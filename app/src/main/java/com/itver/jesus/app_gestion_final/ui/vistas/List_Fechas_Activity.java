package com.itver.jesus.app_gestion_final.ui.vistas;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.itver.jesus.app_gestion_final.R;
import com.itver.jesus.app_gestion_final.database.CalendarioDataSource;
import com.itver.jesus.app_gestion_final.ui.adaptadores.AdapterCursor_Fechas;

/**
 * Clase que enlaza la vista xml.
 * Muestra la actividad en pantalla la lista de fechas en el calendario de un mes determinado.
 */
public class List_Fechas_Activity extends AppCompatActivity {

    private static final String EXTRA_MES = "mes";

    private AdapterCursor_Fechas calendario;
    private String mes;

    /**
     * Inicia una nueva instancia de la actividad
     *
     * @param activity Contexto desde donde se lanzará
     */
    public static void createInstance(Context activity, String mes) {
        Intent intent = getLaunchIntent(activity);
        intent.putExtra(EXTRA_MES, mes);
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
        Intent intent = new Intent(context, List_Fechas_Activity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__fechas_);

        agregarToolbar();
        agregarCosas();
        getSupportActionBar().setTitle(mes);

        RecyclerView recycler = (RecyclerView) findViewById(R.id.lista_fechas);
        recycler.setHasFixedSize(true);

        RecyclerView.LayoutManager lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        calendario = new AdapterCursor_Fechas(getApplicationContext());
        CalendarioDataSource bd = new CalendarioDataSource(getApplicationContext());

        calendario.swapCursor(bd.getCursorAllDatesForMonth(mes));
        recycler.setAdapter(calendario);
    }

    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_fechas);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void agregarCosas() {
        Intent i = getIntent();
        mes = i.getStringExtra(EXTRA_MES);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
