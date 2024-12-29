package com.example.wellnest;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.CalendarView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wellnest.database.AppDatabase;
import com.example.wellnest.database.Emocion;
import com.example.wellnest.database.RegistroDiario;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EstadisticasFragment extends Fragment {

    private static final String TAG = "EstadisticasFragment";
    private BarChart barChart;
    private LinearLayout emotionsContainer;
    private CalendarView calendarView;
    private final Executor executor = Executors.newSingleThreadExecutor();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_estadisticas, container, false);

        initializeViews(view);
        setupChart();
        setupCalendar();
        loadEmotions();

        return view;
    }

    private void initializeViews(View view) {
        barChart = view.findViewById(R.id.bar_chart);
        emotionsContainer = view.findViewById(R.id.emotions_container);
        calendarView = view.findViewById(R.id.calendar_view);
    }

    private void setupChart() {
        barChart.getDescription().setEnabled(true);
        barChart.getDescription().setText("Conteo de emociones");
        barChart.setDrawGridBackground(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);

        barChart.getAxisLeft().setDrawGridLines(true);
        barChart.getAxisRight().setEnabled(false);

        cargarEstadisticas();
    }

    private void cargarEstadisticas() {
        executor.execute(() -> {
            try {
                AppDatabase db = AppDatabase.getInstance(requireContext());
                List<RegistroDiario> registros = db.registroDiarioDao().getTodosLosRegistros();

                if (registros == null || registros.isEmpty()) {
                    Log.d(TAG, "No hay registros en la base de datos.");
                    return;
                }

                Map<Emocion, Integer> conteoEmociones = contarEmociones(registros);
                List<BarEntry> entries = crearEntradasGrafico(conteoEmociones);
                actualizarGrafico(entries);

            } catch (Exception e) {
                Log.e(TAG, "Error al cargar estadísticas: ", e);
            }
        });
    }

    private Map<Emocion, Integer> contarEmociones(List<RegistroDiario> registros) {
        Map<Emocion, Integer> conteoEmociones = new HashMap<>();
        for (Emocion emocion : Emocion.values()) {
            conteoEmociones.put(emocion, 0);
        }

        for (RegistroDiario registro : registros) {
            if (registro.emocion != null) {
                conteoEmociones.merge(registro.emocion, 1, Integer::sum);
            }
        }
        return conteoEmociones;
    }

    private List<BarEntry> crearEntradasGrafico(Map<Emocion, Integer> conteoEmociones) {
        List<BarEntry> entries = new ArrayList<>();
        int index = 0;
        for (Map.Entry<Emocion, Integer> entry : conteoEmociones.entrySet()) {
            entries.add(new BarEntry(index++, entry.getValue()));
        }
        return entries;
    }

    private void actualizarGrafico(List<BarEntry> entries) {
        if (!isAdded()) return;

        requireActivity().runOnUiThread(() -> {
            BarDataSet dataSet = new BarDataSet(entries, "Emociones");
            dataSet.setColors(getColoresEmociones());
            BarData barData = new BarData(dataSet);
            barChart.setData(barData);
            barChart.invalidate();
        });
    }

    private int[] getColoresEmociones() {
        return new int[]{
                Color.rgb(76, 175, 80),   // Verde para Feliz
                Color.rgb(244, 67, 54),   // Rojo para Triste
                Color.rgb(255, 152, 0),   // Naranja para Estresado
                Color.rgb(33, 150, 243),  // Azul para Relajado
                Color.rgb(156, 39, 176)   // Púrpura para otros estados
        };
    }

    private void setupCalendar() {
        executor.execute(() -> {
            try {
                AppDatabase db = AppDatabase.getInstance(requireContext());
                List<RegistroDiario> registros = db.registroDiarioDao().getTodosLosRegistros();
                Map<Long, Emocion> emocionesPorDia = mapearEmocionesPorDia(registros);

                requireActivity().runOnUiThread(() ->
                        configurarCalendarView(emocionesPorDia));
            } catch (Exception e) {
                Log.e(TAG, "Error al configurar el calendario: ", e);
            }
        });
    }

    private Map<Long, Emocion> mapearEmocionesPorDia(List<RegistroDiario> registros) {
        Map<Long, Emocion> emocionesPorDia = new HashMap<>();
        for (RegistroDiario registro : registros) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(registro.fecha);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            emocionesPorDia.put(calendar.getTimeInMillis(), registro.emocion);
        }
        return emocionesPorDia;
    }

    private void configurarCalendarView(Map<Long, Emocion> emocionesPorDia) {
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(year, month, dayOfMonth, 0, 0, 0);
            long selectedMillis = selectedDate.getTimeInMillis();

            Emocion emocion = emocionesPorDia.get(selectedMillis);
            String mensaje = (emocion != null) ?
                    "Emoción registrada: " + emocion.name() :
                    "No hay emoción registrada para este día";
            Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
        });
    }

    private void loadEmotions() {
        Map<Emocion, String> emocionEmojis = new HashMap<>();
        emocionEmojis.put(Emocion.Feliz, "😊");
        emocionEmojis.put(Emocion.Triste, "😢");
        emocionEmojis.put(Emocion.Estresado, "😰");
        emocionEmojis.put(Emocion.Agradecido, "🤩");
        emocionEmojis.put(Emocion.Relajado, "🤗");
        emocionEmojis.put(Emocion.Nervioso, "😬");
        emocionEmojis.put(Emocion.Neutral, "😐");
        emocionEmojis.put(Emocion.Perezoso, "🙄");
        emocionEmojis.put(Emocion.Ansioso, "😲");
        emocionEmojis.put(Emocion.Irritable, "😤");
        emocionEmojis.put(Emocion.Emocionado, "😃");

        emotionsContainer.removeAllViews();
        for (Emocion emocion : Emocion.values()) {
            TextView emojiText = new TextView(requireContext());
            emojiText.setText(emocionEmojis.getOrDefault(emocion, "😐"));
            emojiText.setTextSize(24);
            emojiText.setOnClickListener(v ->
                    Toast.makeText(getContext(), emocion.name(), Toast.LENGTH_SHORT).show());
            emotionsContainer.addView(emojiText);
        }
    }
}
