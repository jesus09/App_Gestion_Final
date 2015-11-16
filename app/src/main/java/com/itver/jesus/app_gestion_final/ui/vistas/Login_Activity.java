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
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... params) {

            UsuariosDataSource usersTable = new UsuariosDataSource(context);
            usersTable.insertarUsuario(usuario, clave);

            noticias = API.getAllNews();
            fechas = API.getAllDates();

            NewsDataSource newsTable = new NewsDataSource(context);
            VisualizaDataSource visualizaTable = new VisualizaDataSource(context);

            for (int i = 0; i < noticias.size(); i++) {

                String nombreAutor = "";
                nombreAutor += noticias.get(i).getAutor().getNombre() + " ";
                nombreAutor += noticias.get(i).getAutor().getApPat() + " ";
                nombreAutor += noticias.get(i).getAutor().getApMat();

                Date fecha = noticias.get(i).getFecha(); // String fecha.
                String fecha_Noticia = formatearFecha(fecha + "");

                Noticia noticia = new Noticia(
                        noticias.get(i).getObjectId(),// Noticia(String id
                        noticias.get(i).getImagen(),// int imagen,
                        noticias.get(i).getTitulo(),// String titulo,
                        nombreAutor,                // String autor,
                        fecha_Noticia,              // String fecha,
                        noticias.get(i).getContenido(), // String contenido,
                        noticias.get(i).getDepartamentoClass().getId_Departamento(),  // int departamento,
                        noticias.get(i).getCategoria(), // int categoria
                        false //boolean visto)
                );

                String idNoticia = newsTable.insertarNoticia(noticia);

                visualizaTable.insertarAUsuario_Noticia(usuario, idNoticia);
            }

            CalendarioDataSource calendar = new CalendarioDataSource(context);

            for (int i = 0; i < fechas.size(); i++) {
                String fecha_num = fechas.get(i).getFecha_num();
                String mes = fechas.get(i).getMes();
                String descrip = fechas.get(i).getDescripcion();
                int num_mes = fechas.get(i).getMes_num();
                int anio = fechas.get(i).getAnio();
                Calendario calendario = new Calendario((i + 1), fecha_num, mes, descrip, num_mes, anio);
                calendar.insertarFecha(calendario);
            }
            return null;
        }

        private String formatearFecha(String fecha) {
            try {
                SimpleDateFormat sourceDateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy", Locale.US);
                Date date = sourceDateFormat.parse(fecha);
                Locale mex = new Locale("es_ES");
                SimpleDateFormat targetDateFormat = new SimpleDateFormat("EEEE dd 'de' MMMM 'del' yyyy hh:mm aaa", mex);
                return targetDateFormat.format(date).toUpperCase();
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            return "Error";
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
