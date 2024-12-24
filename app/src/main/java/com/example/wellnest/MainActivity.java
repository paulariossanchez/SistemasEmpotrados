package com.example.wellnest;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        // Configurar el callback
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Manejar clics en los elementos del menú
                Log.i("HOlaaaa", "Item selected " + item.getItemId());
                switch (item.getItemId()) {
                    case 2131231037:
                        // Acción para "Registro Diario"
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.nav_host_fragment, new RegistroDiarioFragment())
                                .addToBackStack(null)
                                .commit();
                        break;

                    case 2131231033:
                        // Acción para "Ejercicios Guiados"
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.nav_host_fragment, new EjerciciosGuiadosFragment())
                                .addToBackStack(null)
                                .commit();
                        break;

                    case 2131231034:
                        // Acción para "Estadísticas"
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.nav_host_fragment, new EstadisticasFragment())
                                .addToBackStack(null)
                                .commit();
                        break;

                    default:
                        return false;
                }
                return true;
            }
        });
    }
}
