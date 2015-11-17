package com.itver.jesus.app_gestion_final.ui.vistas;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.itver.jesus.app_gestion_final.R;
import com.itver.jesus.app_gestion_final.ui.ajustes.Ajustes_Activity;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private static final String ITEM_SELECTED = "item_seleccionado";

    private static int param;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.ajustes_departamento, true);
        PreferenceManager.setDefaultValues(this, R.xml.ajustes_categorias, true);

        agregarToolbar();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {
            prepararDrawer(navigationView);
        }

        if (savedInstanceState == null) {
            seleccionarItem(navigationView.getMenu().getItem(0));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ITEM_SELECTED, param);
    }

    @Override
    protected void onRestoreInstanceState(Bundle estadoGuardado) {
        super.onRestoreInstanceState(estadoGuardado);
        int item = estadoGuardado.getInt(ITEM_SELECTED);
        seleccionarItem(navigationView.getMenu().getItem(item));
    }

    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        seleccionarItem(menuItem);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });

    }

    private void seleccionarItem(MenuItem itemDrawer) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        String title = itemDrawer.getTitle().toString();

        switch (itemDrawer.getItemId()) {
            case R.id.nav_noticias:
                fragmentoGenerico = Lista_Noticias_Fragment.newInstance(title);
                param = 0;
                break;
            case R.id.nav_calendario:
                fragmentoGenerico = Calendario_Fragment.newInstance(title);
                param = 1;
                break;
            case R.id.nav_reportar:
                fragmentoGenerico = Reporte_Fragment.newInstance(title);
                param = 2;
                break;
            case R.id.nav_configuracion:
                // Iniciar actividad de configuración
                mostrar_Configuraciones();
                break;
            case R.id.nav_cerrar_sesion:
                cerrarSesion();
                break;
        }

        if (fragmentoGenerico != null) {
            fragmentManager.beginTransaction().replace(R.id.contenedor_principal, fragmentoGenerico).commit();
            setTitle(itemDrawer.getTitle());
        }
    }

    private void cerrarSesion() {
        // Comprueba si hay conexión a Internet.
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            ParseUser.logOut();
        }
        iniciarLogin();
        finish();
    }

    private void iniciarLogin() {
        Intent login = new Intent(this, Login_Activity.class);
        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(login);
    }

    private void mostrar_Configuraciones() {
        Intent ajustes = new Intent(this, Ajustes_Activity.class);
        startActivity(ajustes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                mostrar_Configuraciones();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
