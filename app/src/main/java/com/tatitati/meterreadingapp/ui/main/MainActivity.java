package com.tatitati.meterreadingapp.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.tatitati.meterreadingapp.R;
import com.tatitati.meterreadingapp.ui.main.account.AccountFragment;
import com.tatitati.meterreadingapp.ui.main.meters.MetersFragment;
import com.tatitati.meterreadingapp.ui.main.services.ServiceFragment;
import com.tatitati.meterreadingapp.ui.main.home.HomeFragment;
import com.tatitati.meterreadingapp.ui.welcome.WelcomeActivity;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.Menu;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;

    private MainModel mainModel;
    private FragmentManager fragmentManager;
    private HomeFragment homeFragment;
    private MetersFragment metersFragment;
    private ServiceFragment serviceFragment;
    private AccountFragment accountFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(this);

        mainModel = new MainModel();
        fragmentManager = getSupportFragmentManager();
        metersFragment = new MetersFragment();
        homeFragment = new HomeFragment();
        serviceFragment = new ServiceFragment();
        accountFragment = new AccountFragment();

        HomeFragment fragment = (HomeFragment) fragmentManager.findFragmentByTag("HomeFragment");
        if (fragment == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, homeFragment,"HomeFragment")
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
            if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
                super.onBackPressed();
            } else {
                if (fragment instanceof ServiceFragment){
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, homeFragment,"HomeFragment")
                            .commit();
                    navigationView.setCheckedItem(R.id.nav_home);
                }

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            HomeFragment fragment = (HomeFragment) fragmentManager.findFragmentByTag("HomeFragment");
            if (fragment == null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, homeFragment,"HomeFragment")
                        .commit();
            }
        } else if (id == R.id.nav_meters) {

            MetersFragment fragment = (MetersFragment) fragmentManager.findFragmentByTag("MetersFragment");
            if (fragment == null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, metersFragment,"MetersFragment")
                        .commit();
            }

        } else if (id == R.id.nav_services) {
            ServiceFragment fragment = (ServiceFragment) fragmentManager.findFragmentByTag("ServiceFragment");
            if (fragment == null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, serviceFragment,"ServiceFragment")
                        .commit();
            }

        } else if (id == R.id.nav_notifications) {

        } else if (id == R.id.nav_account) {
            AccountFragment fragment = (AccountFragment) fragmentManager.findFragmentByTag("AccountFragment");
            if (fragment == null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, accountFragment,"AccountFragment")
                        .commit();
            }
        } else if (id == R.id.nav_logout) {
            mainModel.logout();
            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
