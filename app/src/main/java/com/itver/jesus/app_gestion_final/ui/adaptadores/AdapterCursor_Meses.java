package com.itver.jesus.app_gestion_final.ui.adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itver.jesus.app_gestion_final.R;

/**
 * Clase adaptadora de Lista de meses y tabla Calendario en SQLite.
 * Created by Jesus on 19/10/2015.
 */
public class AdapterCursor_Meses extends RecyclerView.Adapter<AdapterCursor_Meses.CalendarioViewHolder> {

    private Cursor cursor;
    private Context context;

    public static class CalendarioViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView inicial;
        public TextView mes;
        public TextView anio;

        public CalendarioViewHolder(View v) {
            super(v);
            inicial = (TextView) v.findViewById(R.id.num_fecha);
            mes = (TextView) v.findViewById(R.id.contenido_fecha);
            anio = (TextView) v.findViewById(R.id.calendar_anio);
            anio.setVisibility(View.VISIBLE);
        }
    }

    public AdapterCursor_Meses(Context context) {
        this.context = context;
    }

    @Override
    public CalendarioViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_calendario, viewGroup, false);
        return new CalendarioViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CalendarioViewHolder viewHolder, int position) {
        cursor.moveToPosition(position);

        // ID_NOTICIA  -   MES_CALEND - MES_NUM - ANIO
        int id = cursor.getInt(0);
        String mes = cursor.getString(1);
        int anio = cursor.getInt(3);
        String inicial = mes.charAt(0) + "";

        viewHolder.inicial.setText(inicial);
        viewHolder.mes.setText(mes);
        viewHolder.anio.setText(anio + "");
    }

    @Override
    public int getItemCount() {
        if (cursor != null)
            return cursor.getCount();
        return 0;
    }

    public void swapCursor(Cursor newCursor) {
        cursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return cursor;
    }
}