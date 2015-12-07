package com.itver.jesus.app_gestion_final.ui;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.itver.jesus.app_gestion_final.R;
import com.itver.jesus.app_gestion_final.ui.vistas.Login_Activity;
import com.itver.jesus.app_gestion_final.ui.vistas.MainActivity;
import com.parse.Parse;
import com.parse.ParseUser;

/**
 * Clase Despachadora de Actividad.
 * Si el usuario tiene sesion activa se muestra el menu principal.
 * Si no, se le despliega la vista de inicio de sesion.
 * Created by Jesus on 16/10/2015.
 */
public class DispatchActivity extends AppCompatActivity {

    public DispatchActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Cargar valores por defecto
//        PreferenceManager.setDefaultValues(this, R.xml.encabezados_ajustes, false);

        if (ParseUser.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, Login_Activity.class));
        }
    }

}
