package com.itver.jesus.app_gestion_final.ui.vistas;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.itver.jesus.app_gestion_final.R;
import com.itver.jesus.app_gestion_final.database.NewsDataSource;
import com.itver.jesus.app_gestion_final.database.VisualizaDataSource;
import com.itver.jesus.app_gestion_final.ui.adaptadores.AdapterCursor_Noticias;
import com.itver.jesus.app_gestion_final.ui.controladores.Controlador_News_Eventos;
import com.itver.jesus.app_gestion_final.ui.modelos.Model_Fragment;
import com.itver.jesus.app_gestion_final.ui.modelos.Noticia;
import com.itver.jesus.app_gestion_final.ui.modelos.Preferencias;

import java.util.ArrayList;
import java.util.Arrays;

public class Noticias_Eventos_Fragment extends Model_Fragment {

    public static final String ARG_SECTION_NUMBER = "section_number";
    private Noticias_Eventos_Fragment fragment;
    private Controlador_News_Eventos controlador;
    private Preferencias preferencias;
    private VisualizaDataSource bd;
    private ArrayList<Noticia> items;

    public static Noticias_Eventos_Fragment newInstance(String param1) {
        Noticias_Eventos_Fragment fragment = new Noticias_Eventos_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_NUMBER, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public Noticias_Eventos_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment__noticias, container, false);

        fragment = this;

        recycler = (RecyclerView) view.findViewById(R.id.lista_news_itver);
        recycler.setHasFixedSize(true);

        RecyclerView.LayoutManager lManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(lManager);

        Log.e("gestion", "onCreateView EVENTOS!!!!! ");

        preferencias = new Preferencias(getContext());
        adapter = new AdapterCursor_Noticias(getContext(), preferencias.getMiniaturas());
        recycler.setAdapter(adapter);

        new CargaDatos().execute();

        controlador = new Controlador_News_Eventos(fragment, items);
        recycler.addOnItemTouchListener(controlador);

        return view;
    }

    public RecyclerView getRecycler() {
        return recycler;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Actualizar cantidad de items
        int cantidadItems = preferencias.getCantidadFilasList();
        Log.e("gestion", "onResume !!!!! ");
        new CargaDatos().execute();
    }

    private class CargaDatos extends AsyncTask<Void, Void, Void> {

        private Cursor cursor;

        @Override
        protected Void doInBackground(Void... params) {

            bd = new VisualizaDataSource(getContext());

            String usuario = preferencias.getUserName();
            String[] departamentos = preferencias.getDepartamentosForUser();
            String[] categorias = preferencias.getCategoriasForEventsForUser();
            String[] noCategorias = Preferencias.CATEGORIAS_EXTERNAS;

            cursor = bd.getCursorWithPreferences(usuario, departamentos, categorias, noCategorias);
            items = bd.getListWithPreferences(usuario, departamentos, categorias, noCategorias);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            adapter.swapCursor(cursor);
            controlador.setItems(items);
        }
    }
}
