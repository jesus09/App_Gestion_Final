package com.itver.jesus.app_gestion_final.ui.modelos;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.itver.jesus.app_gestion_final.database.NewsDataSource;
import com.itver.jesus.app_gestion_final.database.VisualizaDataSource;
import com.itver.jesus.app_gestion_final.ui.adaptadores.AdapterCursor_Noticias;

/**
 * Clase modelo de Fragmento
 * Created by Jesus on 25/11/2015.
 */
public class Model_Fragment extends Fragment {

    protected RecyclerView recycler;
    protected AdapterCursor_Noticias adapter;

    public RecyclerView getRecycler() {
        return recycler;
    }

    public AdapterCursor_Noticias getAdapter() {
        return adapter;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());

        // Actualizar visibilidad de miniaturas
        boolean miniaturasPref = sharedPref.getBoolean("miniaturas", true);
        adapter.setActivoMiniaturas(miniaturasPref);
    }
}
