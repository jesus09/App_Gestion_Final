package com.itver.jesus.app_gestion_final.ui.vistas;


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
import com.itver.jesus.app_gestion_final.ui.controladores.Controlador_News_Eventos;
import com.itver.jesus.app_gestion_final.ui.modelos.Model_Fragment;
import com.itver.jesus.app_gestion_final.ui.modelos.Noticia;
import com.itver.jesus.app_gestion_final.ui.modelos.Preferencias;

import java.util.ArrayList;

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

        preferencias = new Preferencias(getContext());
        adapter = new AdapterCursor_Noticias(getContext(), preferencias.getMiniaturas());

        controlador = new Controlador_News_Eventos(fragment, items);
        recycler.addOnItemTouchListener(controlador);

        cargarDatos();

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
        cargarDatos();
    }

    private void cargarDatos() {
        cargarDatosParaUsuario();
    }

    private void cargarDatosParaUsuario() {
        bd = new VisualizaDataSource(getContext());

        String usuario = preferencias.getUserName();
        String[] departamentos = preferencias.getDepartamentosForUser();
        String[] categorias = preferencias.getCategoriasForEventsForUser();
        String[] noCategorias = Preferencias.CATEGORIAS_EXTERNAS;


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