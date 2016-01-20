package com.itver.jesus.app_gestion_final.ui.vistas;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itver.jesus.app_gestion_final.R;
import com.itver.jesus.app_gestion_final.database.VisualizaDataSource;
import com.itver.jesus.app_gestion_final.ui.adaptadores.AdapterCursor_Noticias;
import com.itver.jesus.app_gestion_final.ui.controladores.Controlador_News_Externos;
import com.itver.jesus.app_gestion_final.ui.modelos.Model_Fragment;
import com.itver.jesus.app_gestion_final.ui.modelos.Noticia;
import com.itver.jesus.app_gestion_final.ui.modelos.Preferencias;

import java.util.ArrayList;

/**
 * Clase que enlaza la vista xml.
 * Vista que muestra las noticias que se encuentran en la categoria de externas.
 * Se muestran en su pestana correspondiente.
 */
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

        controlador = new Controlador_News_Externos(this, items);
        recycler.addOnItemTouchListener(controlador);

        cargarDatos();

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
        cargarDatos();
    }


    private void cargarDatos() {
        bd = new VisualizaDataSource(getContext());

        String usuario = preferencias.getUserName();
        String[] departamentos = preferencias.getDepartamentosForUser();
        String[] categorias = preferencias.getCategoriasForExternsForUser();
        String[] noCategorias = Preferencias.CATEGORIAS_EVENTOS;

        items = bd.getListWithPreferences(usuario, departamentos, categorias, noCategorias);
        adapter.swapCursor(bd.getCursorWithPreferences(usuario, departamentos, categorias, noCategorias));

        int scrollPosition = 0;

        if (recycler.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) recycler.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
            Log.e("gestion", "Posición : " + scrollPosition);
        }

        recycler.setAdapter(adapter);
        recycler.scrollToPosition(scrollPosition);
        controlador.setItems(items);
    }
}