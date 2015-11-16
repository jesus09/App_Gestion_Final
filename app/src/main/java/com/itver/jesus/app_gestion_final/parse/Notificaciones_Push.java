package com.itver.jesus.app_gestion_final.parse;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.itver.jesus.app_gestion_final.R;
import com.itver.jesus.app_gestion_final.database.NewsDataSource;
import com.itver.jesus.app_gestion_final.ui.modelos.Noticia;
import com.itver.jesus.app_gestion_final.ui.vistas.MainActivity;
import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Clase encargada de recibir y procesar las notificaciones Push que envia el servidor.
 * Created by Jesus on 06/11/2015.
 */
public class Notificaciones_Push extends ParsePushBroadcastReceiver {

    private final String TAG = "gestion";
    private final static String TITULO_NOTIF = "titulo";
    private final static String MENSAJE_NOTIF = "mensaje";
    private final static String ID_NOTICIA = "idNoticia";
    private final static String ID_IMAGEN = "imagenNoticia";
    private final static String ID_TITULO = "tituloNoticia";
    private final static String ID_AUTOR = "autorNoticia";
    private final static String ID_FECHA = "fechaNoticia";
    private final static String ID_CONTENIDO = "contenidoNoticia";
    private final static String ID_DEPARTAMENTO = "departamentoNoticia";
    private final static String ID_CATEGORIA = "categoriaNoticia";

    public Notificaciones_Push() {
        super();
    }

    @Override
    protected Notification getNotification(Context context, Intent intent) {

        JSONObject pushData = null;
        try {
            pushData = new JSONObject(intent.getExtras().getString("com.parse.Data"));
        } catch (JSONException e) {
            Log.e(TAG, "Push message json exception: " + e.getMessage());
        }

        boolean notif = pushData.has(TITULO_NOTIF) && pushData.has(MENSAJE_NOTIF)
                && pushData.has(ID_NOTICIA) && pushData.has(ID_TITULO) && pushData.has(ID_CONTENIDO)
                && pushData.has(ID_FECHA) && pushData.has(ID_AUTOR);

        if (notif) {
            String titulo = pushData.optString("titulo");
            String mensaje = pushData.optString("mensaje");
            String idNoticia = pushData.optString(ID_NOTICIA);
            int imagen = pushData.optInt(ID_IMAGEN);
            String tituloNoticia = pushData.optString(ID_TITULO);
            String autorNoticia = pushData.optString(ID_AUTOR);
            String fechaNoticia = pushData.optString(ID_FECHA);
            String contenidoNoticia = pushData.optString(ID_CONTENIDO);
            int departamentoNoticia = pushData.optInt(ID_DEPARTAMENTO);
            int categoriaNoticia = pushData.optInt(ID_CATEGORIA);

            Noticia noticia = new Noticia(idNoticia, imagen, tituloNoticia, autorNoticia,
                    fechaNoticia, contenidoNoticia, departamentoNoticia, categoriaNoticia);

            NewsDataSource bd = new NewsDataSource(context);
            bd.insertarNoticia(noticia);

            Intent resultIntent = new Intent(context, MainActivity.class);
            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            context,
                            0,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );

            Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            Notification notification = new NotificationCompat.Builder(
                    context)
                    .setAutoCancel(true)
                    .setSmallIcon(R.mipmap.logo_notif)
                    .setContentTitle(titulo)
                    .setContentText(mensaje)
                    .setContentIntent(resultPendingIntent).setSound(defaultSound)
                    .build();

            notification.defaults |= Notification.DEFAULT_VIBRATE;

            return notification;
        } else {
            return null;
        }
    }
}

