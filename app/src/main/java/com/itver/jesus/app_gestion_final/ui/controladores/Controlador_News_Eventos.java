package com.itver.jesus.app_gestion_final.ui.controladores;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.itver.jesus.app_gestion_final.database.VisualizaDataSource;
import com.itver.jesus.app_gestion_final.ui.adaptadores.AdapterCursor_Noticias;
import com.itver.jesus.app_gestion_final.ui.modelos.Model_Fragment;
import com.itver.jesus.app_gestion_final.ui.modelos.Noticia;
import com.itver.jesus.app_gestion_final.ui.modelos.Preferencias;
import com.itver.jesus.app_gestion_final.ui.vistas.Noticia_FullActivity;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Clase controladora Vista Noticias Eventos Fragment.
 * Created by Jesus on 24/10/2015.
 */
public class Controlador_News_Eventos implements RecyclerView.OnItemTouchListener {

    private Context context;
    private Activity activity;
    private AdapterCursor_Noticias adapterCursor_noticias;
    private GestureDetector mGestureDetector;
    private ArrayList<Noticia> items;
    private RecyclerView recyclerView;

    public Controlador_News_Eventos(final Model_Fragment fragment, ArrayList<Noticia> noticias) {
        activity = fragment.getActivity();
        context = activity.getApplicationContext();
        adapterCursor_noticias = fragment.getAdapter();
        recyclerView = fragment.getRecycler();

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());

                if (childView != null) {
                    int position = recyclerView.getChildAdapterPosition(childView);
                    onLongPressed(position);
                }
            }
        });
        items = noticias;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

        View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

        if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
            int position = recyclerView.getChildAdapterPosition(child);
            mostrarNoticia(position);
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

    private void mostrarNoticia(int position) {
        VisualizaDataSource bd = VisualizaDataSource.getInstance(context);
        Preferencias preferencias = new Preferencias(context);

        String user = preferencias.getUserName();
        bd.noticiaVista(user, items.get(position));

        Noticia_FullActivity.createInstance(context, items.get(position));
    }

    public void onLongPressed(int position) {
        createDialogDeleteNews(position).show();
    }

    private AlertDialog createDialogDeleteNews(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle("Eliminar Noticia").setMessage("¿Desea eliminar la noticia seleccionada?")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                VisualizaDataSource bd = VisualizaDataSource.getInstance(context);
                                Preferencias preferencias = new Preferencias(context);
                                String usuario = preferencias.getUserName();
                                bd.eliminarNoticiaDeLista(usuario, items.get(position));

                                items.remove(position);
                                String[] departamentos = preferencias.getDepartamentosForUser();
                                String[] categorias = preferencias.getCategoriasForEventsForUser();
                                String[] noCategorias = Preferencias.CATEGORIAS_EXTERNAS;

                                Log.e("gestion", "PARA ELIMINAR");

                                adapterCursor_noticias.swapCursor(
                                        bd.getCursorWithPreferences(usuario, departamentos, categorias, noCategorias)
                                );
                            }
                        })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
        return builder.create();
    }

    public ArrayList<Noticia> getItems() {
        return items;
    }

    public void setItems(ArrayList<Noticia> items) {
        this.items = items;
    }
}