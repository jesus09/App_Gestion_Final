package com.itver.jesus.app_gestion_final.ui.vistas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itver.jesus.app_gestion_final.R;
import com.itver.jesus.app_gestion_final.database.CalendarioDataSource;
import com.itver.jesus.app_gestion_final.database.NewsDataSource;
import com.itver.jesus.app_gestion_final.database.UsuariosDataSource;
import com.itver.jesus.app_gestion_final.database.VisualizaDataSource;
import com.itver.jesus.app_gestion_final.parse.API;
import com.itver.jesus.app_gestion_final.parse.CalendarioClass;
import com.itver.jesus.app_gestion_final.parse.NoticiaClass;
import com.itver.jesus.app_gestion_final.ui.modelos.API_Conexion;
import com.itver.jesus.app_gestion_final.ui.modelos.Calendario;
import com.itver.jesus.app_gestion_final.ui.modelos.Noticia;
import com.itver.jesus.app_gestion_final.ui.modelos.Preferencias;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Login_Activity extends AppCompatActivity {

    private ProgressDialog progreso;
    private Context context;
    private List<NoticiaClass> noticias;
    private List<CalendarioClass> fechas;

    private String usuario;
    private String clave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        context = this;
        Button logear = (Button) findViewById(R.id.button_login_ingresar);
        logear.setOnClickListener(new Escuchador_Login());
    }

    class Escuchador_Login implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            EditText inputUser = (EditText) findViewById(R.id.input_user);
            EditText inputPass = (EditText) findViewById(R.id.input_pass);

            usuario = inputUser.getText().toString().trim();
            clave = inputPass.getText().toString();

            // Comprueba si hay conexión a Internet.
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                iniciarLoginServidor();
            } else {
                iniciarLoginLocal();
            }
        }
    }

    private void iniciarLoginLocal() {
        progreso = ProgressDialog.show(context, "Conectando...", "Conectando localmente...", true, false);
        if (conectarLocal()) {
            progreso.dismiss();
            guardarUsuario();
            Intent menuPrincipal = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(menuPrincipal);
            finish();
            Toast.makeText(context, "Sesion iniciada localmente\nNo se recibirán Noticias", Toast.LENGTH_LONG).show();
        } else {
            progreso.dismiss();
            Toast.makeText(getBaseContext(), "Sesión local fallida\nSesión no encontrada o datos incorrectos", Toast.LENGTH_LONG).show();
        }
    }

    private void iniciarLoginServidor() {

        progreso = ProgressDialog.show(context, "Conectando...", "Conectando con el servidor...", true, false);

        ParseUser.logInInBackground(usuario, clave, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    guardarUsuario();
                    new ConexionTask().execute();
                } else {
                    progreso.dismiss();
                    Toast.makeText(getBaseContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void guardarUsuario() {
        Preferencias preferencias = new Preferencias(getApplicationContext());
        preferencias.setUsername(usuario);
        preferencias.setClaveUser(clave);
    }

    private boolean conectarLocal() {
        UsuariosDataSource bd = new UsuariosDataSource(context);
        return bd.getUsuario(usuario, clave);
    }

    private class ConexionTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            API_Conexion api_conexion = new API_Conexion(context);

            api_conexion.insertarUsuarioEnBD(usuario, clave);

            api_conexion.insertarNoticiasEnBD(usuario);

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            progreso.dismiss();
            Intent menuPrincipal = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(menuPrincipal);
            finish();
        }
    }
}
