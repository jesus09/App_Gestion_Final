package com.itver.jesus.app_gestion_final.ui.ajustes;

import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.itver.jesus.app_gestion_final.R;
import com.itver.jesus.app_gestion_final.ui.modelos.Preferencias;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.parse.SignUpCallback;

import java.util.List;

/**
 * Clase para la vista de Ajustes Activity.
 * Created by Jesus on 11/10/2015.
 */
public class Ajustes_Activity extends PreferenceActivity {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.encabezados_ajustes, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        // Comprobar que el fragmento esté relacionado con la actividad
        return Ajustes_Fragment.class.getName().equals(fragmentName);
    }

    @Override
    public boolean onIsMultiPane() {
        // Determinar que siempre sera multipanel
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        return ((float) metrics.densityDpi / (float) metrics.widthPixels) < 0.30;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class Ajustes_Fragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            String settings = getArguments().getString("settings");
            switch (settings) {
                case "generales":
                    addPreferencesFromResource(R.xml.ajustes);
                    break;
                case "departamentos":
                    addPreferencesFromResource(R.xml.ajustes_departamento);
                    break;
                case "categorias":
                    addPreferencesFromResource(R.xml.ajustes_categorias);
                    break;
                case "notificaciones":
                    addPreferencesFromResource(R.xml.ajustes_notificacion);
                    break;
                case "cuenta":
                    addPreferencesFromResource(R.xml.ajustes_cuenta);
                    break;
            }
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Preference preference;
            Preferencias preferencias = new Preferencias(getActivity());
            String mensaje = "";

            switch (key) {
                case "username":
                    preference = findPreference(key);
                    String user = sharedPreferences.getString(key, "");
                    preference.setSummary(user);
                    String clave = preferencias.getClaveUser();
                    mensaje = configurarUsuario(clave, user);
                    Toast.makeText(getActivity(), mensaje, Toast.LENGTH_LONG).show();
                    break;
                case "clave_username":
                    preference = findPreference(key);
                    String oldClave = preferencias.getClaveAnterior();
                    String new_clave = sharedPreferences.getString(key, "");
                    preference.setSummary(new_clave);
                    configurarClave(oldClave, new_clave);
                    mensaje = "Clave modificada con exito";
                    Toast.makeText(getActivity(), mensaje, Toast.LENGTH_LONG).show();
                    break;
            }
        }

        private String configurarUsuario(String clave, String new_username) {
            try {
                String oldUser = ParseUser.getCurrentUser().getUsername();
                ParseUser user = ParseUser.logIn(oldUser, clave);
                user.setUsername(new_username);
                user.saveInBackground();
                return "Usuario modificado con exito";
            } catch (ParseException e) {
                e.printStackTrace();
                return "Error al modificar el usuario";
            }
        }

        private String configurarClave(String oldClave, String new_clave) {
            try {

                String oldUser = ParseUser.getCurrentUser().getUsername();

                ParseUser user = ParseUser.logIn(oldUser, oldClave);
                user.setPassword(new_clave);
                user.saveInBackground();
                return "Clave modificada con exito";
            } catch (ParseException e) {
                e.printStackTrace();
                return "Error en la modificación";
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            // Registrar escucha
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            // Eliminar registro de la escucha
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }

    }
}
