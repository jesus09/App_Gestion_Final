package com.itver.jesus.app_gestion_final.ui.vistas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.itver.jesus.app_gestion_final.R;

public class Reporte_Fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String TEXT_PARAM = "texto_input";

    private String mParam1;
    private String texto_campo;
    private EditText reporte;

    public static Reporte_Fragment newInstance(String param1) {
        Reporte_Fragment fragment = new Reporte_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public Reporte_Fragment() {
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
        View view = inflater.inflate(R.layout.fragment_reporte_, container, false);

        reporte = (EditText) view.findViewById(R.id.input_reporte);

        if(savedInstanceState != null){
            texto_campo = savedInstanceState.getString(TEXT_PARAM);
            reporte.setText(texto_campo);
        }
        return view;
    }


    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        texto_campo = reporte.getText().toString();
        if (!texto_campo.isEmpty()) {
            outState.putString(TEXT_PARAM, texto_campo);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            texto_campo = savedInstanceState.getString(TEXT_PARAM);
            reporte.setText(texto_campo);
        }
    }

}
