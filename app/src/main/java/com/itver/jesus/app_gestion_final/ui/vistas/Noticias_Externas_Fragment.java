package com.itver.jesus.app_gestion_final.ui.vistas;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itver.jesus.app_gestion_final.R;
import com.itver.jesus.app_gestion_final.database.VisualizaDataSource;
import com.itver.jesus.app_gestion_final.ui.adaptadores.AdapterCursor_Noticias;
import com.itver.jesus.app_gestion_final.ui.controladores.Controlador_News_Eventos;
import com.itver.jesus.app_gestion_final.ui.controladores.Controlador_News_Externos;
import com.itver.jesus.app_gestion_final.ui.modelos.Model_Fragment;
import com.itver.jesus.app_gestion_final.ui.modelos.Noticia;
import com.itver.jesus.app_gestion_final.ui.modelos.Preferencias;
import com.parse.ParseUser;

import java.util.ArrayList;

public class Noticias_Externas_Fragment extends Model_Fragment {

    private static final String ARG_PARAM1 = "param1";
    private Preferencias preferencias;
    private VisualizaDataSource bd;
    private ArrayList<Noticia> items;
    private Controlador_News_Externos controlador;
    private Noticias_Externas_Fragment fragment;

    public static Noticias_Externas_Fragment newInstance(String param1) {
        Noticias_Externas_Fragment fragment = new Noticias_Externas_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public Noticias_Externas_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment__noticias__externas, container, false);

        fragment = this;

        recycler = (RecyclerView) view.findViewById(R.id.lista_news_externa);
        recycler.setHasFixedSize(true);

        RecyclerView.LayoutManager lManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(lManager);

        preferencias = new Preferencias(getContext());
        adapter = new AdapterCursor_Noticias(getContext(), preferencias.getMiniaturas());
        recycler.setAdapter(adapter);

        Log.e("gestion", "onCreateView EXTERNAS !!!!! ");
        new CargaDatos().execute();

        controlador = new Controlador_News_Externos(fragment, items);
        recycler.addOnItemTouchListener(controlador);

        return view;
    }

    public RecyclerView getRecycler() {
        return recycler;
    }

    Cursor cursor;

    @Override
    public void onResume() {
        super.onResume();
        // Actualizar cantidad de items
        int cantidadItems = preferencias.getCantidadFilasList();
//        Log.e("gestion", "onResume EXTERNAS !!!!! ");
//        new CargaDatos().execute();
    }

    private class CargaDatos extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            bd = new VisualizaDataSource(getContext());

            String usuario = preferencias.getUserName();
            String[] departamentos = preferencias.getDepartamentosForUser();
            String[] categorias = preferencias.getCategoriasForExternsForUser();
            String[] noCategorias = Preferencias.CATEGORIAS_EVENTOS;

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