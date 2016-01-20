package com.itver.jesus.app_gestion_final.ui.vistas;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.itver.jesus.app_gestion_final.R;
import com.itver.jesus.app_gestion_final.ui.modelos.Noticia;

import java.util.Locale;

/**
 * Clase que enlaza la vista xml.
 * Muestra la actividad en pantalla la noticia completa que previamente se ha seleccionado.
 */
public class Noticia_FullActivity extends AppCompatActivity {

    private static final String EXTRA_DRAWABLE = "imagen";
    private static final String EXTRA_NAME = "nombre";
    private static final String EXTRA_AUTOR = "autor";
    private static final String EXTRA_DATE = "fecha";
    private static final String EXTRA_CONTENT = "contenido";

    /**
     * Inicia una nueva instancia de la actividad
     *
     * @param activity Contexto desde donde se lanzará
     */
    public static void createInstance(Context activity, Noticia noticia) {
        Intent intent = getLaunchIntent(activity);
        intent.putExtra(EXTRA_NAME, noticia.getTitulo());
        intent.putExtra(EXTRA_DRAWABLE, noticia.getImagen());
        intent.putExtra(EXTRA_AUTOR, noticia.getAutor());
        intent.putExtra(EXTRA_DATE, noticia.getFecha());
        intent.putExtra(EXTRA_CONTENT, noticia.getContenido());
        // Llamada startActivity () desde fuera de un contexto de actividad requiere la bandera
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    /**
     * Construye un Intent a partir del contexto y la actividad
     * de detalle.
     *
     * @param context Contexto donde se inicia
     * @return Intent listo para usar
     */
    public static Intent getLaunchIntent(Context context) {
        Intent intent = new Intent(context, Noticia_FullActivity.class);
        return intent;
    }

    private TextToSpeech sonido;
    private FloatingActionButton boton_sonido;
    private int resultado;

    private TextView tv_titulo;
    private TextView tv_autor;
    private TextView tv_fecha;
    private TextView tv_contenido;

    private StringBuilder contenido;
    private boolean isPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia__full);

        agregarToolbar();

        agregarCosas();
        loadImageParallax(R.drawable.itver);// Cargar Imagen

        sonido = new TextToSpeech(Noticia_FullActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.SUCCESS) {
                    Toast.makeText(getApplicationContext(),
                            "Característica no apoyada sobre en su dispositivo",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        boton_sonido = (FloatingActionButton) findViewById(R.id.button_reproducir);

        boton_sonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPlay) { // Si es falso, apenas lo presionaron
                    boton_sonido.setImageResource(R.mipmap.sound_off);
                    sonido.speak(contenido.toString(), TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    if (sonido != null) {
                        boton_sonido.setImageResource(R.mipmap.play_sound);
                        sonido.stop();
                    }
                }
                isPlay = !isPlay;
            }
        });
    }

    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_noticia);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void agregarCosas() {
        Intent i = getIntent();
        String titulo = i.getStringExtra(EXTRA_NAME);
        int imagen = i.getIntExtra(EXTRA_DRAWABLE, -1);
        String autor = i.getStringExtra(EXTRA_AUTOR);
        String fecha = i.getStringExtra(EXTRA_DATE);
        String contenido_notic = i.getStringExtra(EXTRA_CONTENT);

        tv_titulo = (TextView) findViewById(R.id.news_full_title);
        tv_autor = (TextView) findViewById(R.id.news_full_name_autor);
        tv_fecha = (TextView) findViewById(R.id.news_full_date);
        tv_contenido = (TextView) findViewById(R.id.news_full_content);
        tv_contenido.setMovementMethod(LinkMovementMethod.getInstance());

        contenido = new StringBuilder();
        contenido.append("autor : ").append(autor).append(" : ");
        contenido.append("fecha : ").append(fecha).append(" : ");
        contenido.append("titulo : ").append(titulo).append(" : ");
        contenido.append("contenido : ").append(contenido_notic);

        tv_titulo.setText((CharSequence) titulo);
        tv_autor.setText((CharSequence) autor);
        tv_fecha.setText((CharSequence) fecha);
        tv_contenido.setText((CharSequence) contenido_notic);
    }

    /**
     * Se carga una imagen aleatoria para el detalle
     */

    private void loadImageParallax(int id) {
        ImageView image = (ImageView) findViewById(R.id.image_paralax);
        // Usando Glide para la carga asíncrona
        Glide.with(this).load(id).centerCrop().into(image);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.home:
                destruir();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void destruir() {
        if (sonido != null) {
            sonido.stop();
            sonido.shutdown();
        }
    }
}
