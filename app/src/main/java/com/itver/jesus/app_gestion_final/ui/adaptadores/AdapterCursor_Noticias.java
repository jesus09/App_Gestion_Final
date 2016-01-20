package com.itver.jesus.app_gestion_final.ui.adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itver.jesus.app_gestion_final.R;

/**
 * Clase Modelo para implementar el Adaptador con cursor para
 * cargar datos localmente de la base de datos SQLite y poblar el RecyclerView.
 * Created by Jesus on 18/10/2015.
 */
public class AdapterCursor_Noticias extends RecyclerView.Adapter<AdapterCursor_Noticias.NoticiaViewHolder> {

    private Cursor cursor;
    private Context context;
    private boolean activoMiniaturas;

    /**
     * Clase estatica que emplea el patron viewHolder con la lista RecyclerView.
     */
    public static class NoticiaViewHolder extends RecyclerView.ViewHolder {

        // Campos respectivos de un item
        public ImageView imagen;
        public TextView titulo;
        public TextView fecha_hora;
        public TextView autor;
        public TextView contenido;

        public NoticiaViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imagen);
            titulo = (TextView) v.findViewById(R.id.titulo);
            fecha_hora = (TextView) v.findViewById(R.id.fecha_hora);
            autor = (TextView) v.findViewById(R.id.autor);
            contenido = (TextView) v.findViewById(R.id.contenido);
        }
    }

    public AdapterCursor_Noticias(Context context, boolean activarMiniaturas) {
        this.context = context;
        activoMiniaturas = activarMiniaturas;
    }

    /**
     * Enlaza la vista del item con el adaptador.
     *
     * @param viewGroup Lista con el conjunto de item.
     * @param viewType  int viewType.
     * @return FechasViewHolder clase empleando patron viewHolder.
     */
    @Override
    public NoticiaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_noticia, viewGroup, false);
        return new NoticiaViewHolder(v);
    }

    /**
     * Agrega el contenido a la vista del item.
     *
     * @param viewHolder Vista a aplicar el contenido.
     * @param position   int Posicion en el scroll.
     */
    @Override
    public void onBindViewHolder(NoticiaViewHolder viewHolder, int position) {
        cursor.moveToPosition(position);

        //ID -  Imagen - Titulo - Autor - Fecha - Contenido - Departamento - Categoria - Visto
        int imagen = cursor.getInt(1);
        String titulo = cursor.getString(2);
        String autor = cursor.getString(3);
        String fecha = cursor.getString(4);
        String contenido = cursor.getString(5);
        int visto = cursor.getInt(8);

        if (activoMiniaturas) {
            viewHolder.imagen.setVisibility(View.VISIBLE);
            viewHolder.imagen.setImageResource(imagen);
        } else {
            viewHolder.imagen.setVisibility(View.GONE);
        }

        viewHolder.titulo.setText(titulo);
        viewHolder.autor.setText(autor);
        viewHolder.fecha_hora.setText(fecha);
        viewHolder.contenido.setText(contenido);

        if (visto == 0) {
            viewHolder.itemView.setActivated(true);
        }
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
     * Cambia el estado de la vista de la imagen en cada item.
     *
     * @param activoMiniaturas boolean True, activas - False, ocultas.
     */
    public void setActivoMiniaturas(boolean activoMiniaturas) {
        if (activoMiniaturas != this.activoMiniaturas) {
            this.activoMiniaturas = activoMiniaturas;
            notifyDataSetChanged();
        }
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
