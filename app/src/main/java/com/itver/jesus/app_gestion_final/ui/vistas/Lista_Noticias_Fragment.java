package com.itver.jesus.app_gestion_final.ui.vistas;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itver.jesus.app_gestion_final.R;

import java.util.ArrayList;
import java.util.List;

public class Lista_Noticias_Fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;

    private static final String TAB_SELECTED = "tabs_selected";
    private static int tab_param;

    private AppBarLayout appBar;
    private TabLayout pestanas;
    private ViewPager viewPager;

    public static Lista_Noticias_Fragment newInstance(String param1) {
        Lista_Noticias_Fragment fragment = new Lista_Noticias_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public Lista_Noticias_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            tab_param = getArguments().getInt(TAB_SELECTED);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabs__noticias_, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.pager);

        if (savedInstanceState == null) {
            insertarTabs(container);
            poblarViewPager(viewPager);
            pestanas.setupWithViewPager(viewPager);
        } else {
            viewPager.setCurrentItem(tab_param);
        }

        return view;
    }

    private void insertarTabs(ViewGroup container) {
        View padre = (View) container.getParent();
        appBar = (AppBarLayout) padre.findViewById(R.id.appbar);
        pestanas = new TabLayout(getActivity());
        pestanas.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"));
        appBar.addView(pestanas);
    }

    private void poblarViewPager(ViewPager viewPager) {

        AdaptadorSecciones adapter = new AdaptadorSecciones(getFragmentManager());

        adapter.addFragment(Noticias_Eventos_Fragment.newInstance("Eventos"), getString(R.string.titulo_tab_new_eventos));
        adapter.addFragment(Noticias_Externas_Fragment.newInstance("Externas"), getString(R.string.titulo_tab_new_externos));

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (appBar != null) {
            appBar.removeView(pestanas);
        }
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("gestion", "!!!!onSaveInstanceState :  " + viewPager.getCurrentItem());
        outState.putInt(TAB_SELECTED, viewPager.getCurrentItem());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            tab_param = savedInstanceState.getInt(TAB_SELECTED);
            Log.e("gestion", "!!!onActivityCreated :  " + tab_param);
//            viewPager.setCurrentItem(tab_param);
        }
    }

    public class AdaptadorSecciones extends FragmentStatePagerAdapter {
        private final List<Fragment> fragmentos = new ArrayList<>();
        private final List<String> titulosFragmentos = new ArrayList<>();

        public AdaptadorSecciones(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragmentos.get(position);
        }

        @Override
        public int getCount() {
            return fragmentos.size();
        }

        public void addFragment(android.support.v4.app.Fragment fragment, String title) {
            fragmentos.add(fragment);
            titulosFragmentos.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titulosFragmentos.get(position);
        }
    }
}
