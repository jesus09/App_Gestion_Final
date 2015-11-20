package com.itver.jesus.app_gestion_final.ui.modelos;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Clase modelo de preferencias para la abstracción de elementos SharedPreferences.
 * Created by Jesus on 01/11/2015.
 */
public class Preferencias {

    public static final String[] CATEGORIAS_EVENTOS = {"1", "4", "5", "6", "7", "8", "10", "11"};

    public static final String[] CATEGORIAS_EXTERNAS = {"0", "2", "3", "9"};

    private SharedPreferences sharedPreferences;
    private Context context;

    public Preferencias(Context context) {
        this.context = context;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setCuenta() {
        setUsername("");
        setClaveUser("");
        setClaveAnterior("");
    }

    public void setUsername(String usuario) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", usuario);
        editor.commit();
    }

    public void setClaveUser(String claveUser) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("clave_username", claveUser);
        editor.commit();
        setClaveAnterior(claveUser);
    }

    public void setClaveAnterior(String claveAnterior) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("clave_anterior", claveAnterior);
        editor.commit();
    }

    public boolean getMiniaturas() {
        return sharedPreferences.getBoolean("miniaturas", true);
    }

    public String getUserName() {
        return sharedPreferences.getString("username", "");
    }

    public int getCantidadFilasList() {
        return Integer.parseInt(sharedPreferences.getString("numNoticias", "8"));
    }

    public String getClaveUser() {
        return sharedPreferences.getString("clave_username", "");
    }

    public String getClaveAnterior() {
        return sharedPreferences.getString("clave_anterior", "");
    }

    public String[] getDepartamentosForUser() {
        Set<String> listaDepartamentos = sharedPreferences.getStringSet("listaDepartamentos", new HashSet<String>());

        String[] departamentos = new String[listaDepartamentos.size()];
        departamentos = listaDepartamentos.toArray(departamentos);

        return departamentos;
    }

    public String[] getCategoriasForEventsForUser() {
        Set<String> listaDepartamentos = sharedPreferences.getStringSet("listaCategorias", new HashSet<String>());
        ArrayList<String> categorias = new ArrayList<>();

        String[] values = {
                "1", "4", "5", "6", "7", "8", "10", "11"
        };

        Iterator it = listaDepartamentos.iterator();

        for (String value : values) {
            if (listaDepartamentos.contains(value)) {
                categorias.add(value);
            }
        }

        String[] departamentos = new String[categorias.size()];
        departamentos = categorias.toArray(departamentos);

        return departamentos;
    }

    public String[] getCategoriasForExternsForUser() {
        Set<String> listaDepartamentos = sharedPreferences.getStringSet("listaCategorias", new HashSet<String>());
        ArrayList<String> categorias = new ArrayList<>();
        categorias.add("0");

        String[] values = {
                "2", "3", "9"
        };

        Iterator it = listaDepartamentos.iterator();

        for (String value : values) {
            if (listaDepartamentos.contains(value)) {
                categorias.add(value);
            }
        }

        String[] departamentos = new String[categorias.size()];
        departamentos = categorias.toArray(departamentos);

        return departamentos;
    }
}