package com.itver.jesus.app_gestion_final.ui.controladores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.itver.jesus.app_gestion_final.ui.vistas.List_Fechas_Activity;
import com.itver.jesus.app_gestion_final.ui.vistas.Calendario_Fragment;

import java.util.ArrayList;

/**
 * Clase Controladora para mostrar en la vista la lista de fechas seleccionadas por mes.
 * Created by Jesus on 24/10/2015.
 */
public class Controlador_Mes_Fechas implements RecyclerView.OnItemTouchListener {

    private Context lista;
    private GestureDetector mGestureDetector;
    private ArrayList<String> meses;

    public Controlador_Mes_Fechas(Calendario_Fragment fragment, ArrayList<String> noticias) {
        lista = fragment.getActivity().getApplicationContext();
        mGestureDetector = fragment.getmGestureDetector();
        meses = noticias;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

        View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

        if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
            int position = recyclerView.getChildPosition(child);
            mostrarFechas(position);
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    private void mostrarFechas(int position) {
        List_Fechas_Activity.createInstance(lista, meses.get(position));
    }
}