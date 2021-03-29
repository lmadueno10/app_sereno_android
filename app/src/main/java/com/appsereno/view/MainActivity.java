package com.appsereno.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.appsereno.R;
import com.appsereno.view.fragments.MainFragment;
import com.appsereno.view.fragments.MisDatosFragment;
import com.appsereno.view.fragments.PendientesFragment;
import com.appsereno.view.fragments.ReportarIncidenciaFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

/**
 * MainActivity is the class that is bound to the activity_main.xml view
 */
public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    MaterialToolbar materialToolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        materialToolbar=findViewById(R.id.app_main_menu);
        setSupportActionBar(materialToolbar);
        drawerLayout=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.navigationView);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,materialToolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_container,new MainFragment());
        fragmentTransaction.commit();

        fillData();
        fillMenu();
        loadBottomMenu();
    }

    public void logout(MenuItem item) {
        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("auth_token",null);
        editor.putString("refresh_token",null);
        editor.apply();
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    private  void fillData(){
        try {
            SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
            String userInfo = preferences.getString("user_info", "-");
            String userName = preferences.getString("user", "-");
            View headerView = navigationView.getHeaderView(0);
            if (headerView != null) {
                TextView title = headerView.findViewById(R.id.dwh_text_main);
                TextView subTutle = headerView.findViewById(R.id.dwh_text_secondary);
                if (title != null && subTutle != null) {
                    title.setText(userInfo);
                    subTutle.setText(userName);
                }
            }
        }catch (Exception e){
            Log.d("ERROR:",e.getMessage());
        }
    }

    private void fillMenu() {
        try {

            SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
            int userProfile = preferences.getInt("user_profile", 0);
            Menu menu = navigationView.getMenu();
            MenuItem menuItem = menu.getItem(0);
            SubMenu subMenu = menuItem.getSubMenu();

            MenuItem menuItemMD = subMenu.getItem(0);
            menuItemMD.setOnMenuItemClickListener(item -> {
                drawerLayout.closeDrawer(GravityCompat.START);
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new MisDatosFragment());
                fragmentTransaction.commit();
                materialToolbar.setTitle("Mis datos");
                return true;
            });

            MenuItem menuItemMT = subMenu.add("Notificaciones");
            menuItemMT.setIcon(R.drawable.ic_notifications_24);

            if (userProfile != 1) {
                MenuItem menuItemLS = subMenu.add("Listado de serenos");
                menuItemLS.setIcon(R.drawable.ic_supervised_user_circle_24);
            }

            MenuItem menuItemAD = subMenu.add("Acerca del APP");
            menuItemAD.setIcon(R.drawable.ic_info_24);

            MenuItem menuItemTC = subMenu.add("Términos y condiciones");
            menuItemTC.setIcon(R.drawable.ic_book_24);
        } catch (Exception e) {
            Log.d("ERROR", e.getMessage());
        }
    }
    public void loadBottomMenu(){
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.page_pending);
        badge.setVisible(true);
        badge.setNumber(5);
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final int id=item.getItemId();
        final int pageHome=R.id.page_home;
        final int pagePending = R.id.page_pending;
        final int pageReport=R.id.page_report;
        final int pageStreaming=R.id.page_stream;
        switch (id){
            case pageHome:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new MainFragment());
                fragmentTransaction.commit();
                materialToolbar.setTitle("App Sereno");
                return true;
            case pagePending:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new PendientesFragment());
                fragmentTransaction.commit();
                materialToolbar.setTitle("Pendientes");
                return true;
            case pageReport:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new ReportarIncidenciaFragment());
                fragmentTransaction.commit();
                materialToolbar.setTitle("Reportar Incidencia");
                return true;
            case pageStreaming :
                initCamera();
                return false ;
        }
        return false;
    }

    public void initCamera(){
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(takeVideoIntent);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.top_bar_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        final int id= menuItem.getItemId();
        final int topItemStream=R.id.top_item_stream;
        final int topItemSearch=R.id.top_item_search;
        final int topItemLogout=R.id.top_item_logout;
        switch(id){
            case topItemStream:
                initCamera();
                break;
            case topItemSearch:
                Snackbar.make(findViewById(R.id.main_container),"Buscando...",Snackbar.LENGTH_LONG).show();
                break;
            case topItemLogout:
                logout(menuItem);
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

}