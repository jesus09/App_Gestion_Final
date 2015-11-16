package com.itver.jesus.app_gestion_final.ui.modelos;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.itver.jesus.app_gestion_final.database.VisualizaDataSource;
import com.parse.ParseUser;

/**
 * Clase modelo para la creación de un dialogo en la interfaz de usuario.
 * Created by Jesus on 24/10/2015.
 */
public class MensajeDialog {

    public static AlertDialog createSimpleDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle("Sin conexión")
                .setMessage("No se puede cerrar sesión si no se tiene conexión a Internet." +
                        "\nLa aplicación se cerrara sin consumir datos" +
                        "\nPero su sesión seguirá abierta")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                activity.finish();
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

    public static AlertDialog createDialogDeleteNews(final Context context, final Noticia noticia) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Eliminar Noticia")
                .setMessage("¿Desea eliminar la noticia seleccionada?")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                VisualizaDataSource bd = VisualizaDataSource.getInstance(context);
                                bd.eliminarNoticiaDeLista(ParseUser.getCurrentUser().getUsername(), noticia);
//                                Toast.makeText(context, "Presionado eliminar\nEliminar noticia " + noticia.getId(), Toast.LENGTH_SHORT).show();
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
}
