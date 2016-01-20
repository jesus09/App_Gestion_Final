package com.itver.jesus.app_gestion_final.ui.vistas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.itver.jesus.app_gestion_final.R;

/***
 * Clase que enlaza la vista xml.
 * Clase Fragmento que muestra en la pantalla la actividad de Invitado con el mensaje de dialogo.
 */
public class InvitadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitado);
    }
}
