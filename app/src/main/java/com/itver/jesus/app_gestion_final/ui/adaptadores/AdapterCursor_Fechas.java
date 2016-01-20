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
 * Clase Adaptadora para lista fechas y Base de datos SQLite local.
 * Created by Jesus on 24/10/2015.
 */
public class AdapterCursor_Fechas extends RecyclerView.Adapter<AdapterCursor_Fechas.FechasViewHolder> {

    private Cursor cursor;
    private Context context;

    /**
     * Clase estatica que emplea el patron viewHolder con la lista RecyclerView.
     */
    public static class FechasViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView fecha_num;
        public TextView contenido;

        public FechasViewHolder(View v) {
            super(v);
            fecha_num = (TextView) v.findViewById(R.id.num_fecha);
            fecha_num.setTextSize(23);
            contenido = (TextView) v.findViewById(R.id.contenido_fecha);
            contenido.setTextSize(16);
//            contenido.setGravity(View.TEXT_ALIGNMENT_TEXT_END);

        }
    }

    public AdapterCursor_Fechas(Context context) {
        this.context = context;
    }

    /**
     * Enlaza la vista del item con el adaptador.
     *
     * @param viewGroup
     * @param viewType  int .
     * @return FechasViewHolder clase empleando patron viewHolder.
     */
    @Override
    public FechasViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_calendario, viewGroup, false);
        return new FechasViewHolder(v);
    }

    /**
     * Agrega el contenido a la vista del item.
     *
     * @param viewHolder Vista a aplicar el contenido.
     * @param position   int Posicion en el scroll.
     */
    @Override
    public void onBindViewHolder(FechasViewHolder viewHolder, int position) {
        cursor.moveToPosition(position);

        //FECHA_NUM  -  MES_CALEND    -   CONT_CALEND -    ANIO_CALEND
        int fecha = cursor.getInt(0);
        String contenido = cursor.getString(2);

        String num = String.format("%02d", fecha);

        viewHolder.fecha_num.setText(num);
        viewHolder.contenido.setText(contenido);
    }

    /**
     * Retorna los elementos almacenados en el Adaptador.
     *
     * @return int Cantidad de elementos en el Adaptador.
     */
    @Override
    public int getItemCount() {
        if (cursor != null)
            return cursor.getCount();
        return 0;
    }

    /**
     * Cambia el cursor que muestra por el cursor que recibe.
     *
     * @param newCursor Cursor con datos actualizados.
     */
    public void swapCursor(Cursor newCursor) {
        cursor = newCursor;
        notifyDataSetChanged();
    }

    /**
     * Retorna el Cursor que contiene los datos en el Adaptador.
     *
     * @return Cursor.
     */
    public Cursor getCursor() {
        return cursor;
    }
}