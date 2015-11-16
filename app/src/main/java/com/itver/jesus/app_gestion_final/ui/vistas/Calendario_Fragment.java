package com.itver.jesus.app_gestion_final.ui.vistas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.itver.jesus.app_gestion_final.R;
import com.itver.jesus.app_gestion_final.database.CalendarioDataSource;
import com.itver.jesus.app_gestion_final.ui.adaptadores.AdapterCursor_Meses;
import com.itver.jesus.app_gestion_final.ui.controladores.Controlador_Mes_Fechas;

import java.util.ArrayList;

public class Calendario_Fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;

    private GestureDetector mGestureDetector;
    private AdapterCursor_Meses calendario;
//    private AdapterCursor_Calendario_Fechas calendario;

    public static Calendario_Fragment newInstance(String param1) {
        Calendario_Fragment fragment = new Calendario_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public Calendario_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment_calendario, container, false);

        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.lista_calendario);
        recycler.setHasFixedSize(true);

        RecyclerView.LayoutManager lManager = new LinearLayoutManager(getActivity()); // Filas
        recycler.setLayoutManager(lManager);

        mGestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });


        calendario = new AdapterCursor_Meses(getContext());
        CalendarioDataSource bd = new CalendarioDataSource(getContext());

        calendario.swapCursor(bd.getCursorAllMeses());
        recycler.setAdapter(calendario);

        ArrayList<String> meses = bd.getCursorAll_Name_Meses();

        Controlador_Mes_Fechas controlador = new Controlador_Mes_Fechas(this, meses);
        recycler.addOnItemTouchListener(controlador);

        return view;
    }

    public GestureDetector getmGestureDetector() {
        return mGestureDetector;
    }
}
