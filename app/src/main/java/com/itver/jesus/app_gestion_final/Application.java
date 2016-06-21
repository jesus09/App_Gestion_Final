package com.itver.jesus.app_gestion_final;

import com.itver.jesus.app_gestion_final.parse.CalendarioClass;
import com.itver.jesus.app_gestion_final.parse.DepartamentoClass;
import com.itver.jesus.app_gestion_final.parse.NoticiaClass;
import com.itver.jesus.app_gestion_final.parse.Usuario;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Clase Application para iniciar el servicio de conexion con Parse.
 * Se consulta la siguiente clase antes de iniciar las actividades.
 * Created by Jesus on 16/10/2015.
 */
public class Application extends android.app.Application {

    private final String AP_ID = "86Vhs5IB5g8QRVCOomUdjEDb3aSMDKlk4tKgmdR6";
    private final String CLIENT_KEY = "vJmDbcIhqsQQxzcdIOt17hXUxhbOSupcAhCfNE3Mu";

    public Application() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Registro de SubClases
        ParseObject.registerSubclass(Usuario.class);
        ParseObject.registerSubclass(DepartamentoClass.class);
        ParseObject.registerSubclass(NoticiaClass.class);
        ParseObject.registerSubclass(CalendarioClass.class);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, AP_ID, CLIENT_KEY);
    }
}
