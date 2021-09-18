package com.example.appmabbicaracommunity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private final boolean isOpen = false;
    private CardView indjpneng,jpnind,jpneng,engjpn,engind,indeng,budayaindo,budayainggris,budayajepang,simulasiinggris,simulasijepang,simulasiindo;
    private String katakunci;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        indjpneng = findViewById(R.id.indtojpntoeng);
        jpnind = findViewById(R.id.jpntoind);
        jpneng = findViewById(R.id.jpntoeng);
        engjpn = findViewById(R.id.engtojpn);
        engind = findViewById(R.id.engtoind);
        indeng = findViewById(R.id.indtoeng);

        budayaindo = findViewById(R.id.budaya_ind);
        budayajepang = findViewById(R.id.budaya_jepang);
        budayainggris = findViewById(R.id.budaya_inggris);

        simulasiinggris = findViewById(R.id.simulasi_inggris);
        simulasijepang = findViewById(R.id.simulasi_jepang);
        simulasiindo = findViewById(R.id.simulasi_indo);

        indjpneng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                katakunci = "indtojpn";
                intent();
            }
        });

        jpnind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                katakunci = "jpntoind";
                intent();
            }
        });

        jpneng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                katakunci = "jpntoeng";
                intent();
            }
        });

        engjpn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                katakunci = "engtojpn";
                intent();
            }
        });

        engind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                katakunci = "engtoind";
                intent();
            }
        });

        indeng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                katakunci = "indtoeng";
                intent();
            }
        });

        budayaindo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                katakunci = "budayaindo";
                intent();
            }
        });

        budayajepang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                katakunci = "budayajepang";
                intent();
            }
        });

        budayainggris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                katakunci = "budayainggris";
                intent();
            }
        });

        simulasijepang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                katakunci = "simulasijepang";
                intent();
            }
        });

        simulasiinggris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                katakunci = "simulasiinggris";
                intent();
            }
        });

        simulasiindo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                katakunci = "simulasiindo";
                intent();
            }
        });

//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_indonesiajepang, R.id.nav_jepangindonesia, R.id.nav_inggrisjepang, R.id.nav_jepanginggris, R.id.nav_indonesiainggris, R.id.nav_indonesiainggris, R.id.nav_inggrisindonesia, R.id.nav_inggris, R.id.nav_indonesia, R.id.nav_jepang, R.id.nav_simulasijepang, R.id.nav_simulasiinggris)
//                .setDrawerLayout(drawer)
//                .build();NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
////        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
////        NavigationUI.setupWithNavController(navigationView, navController);
//
    }

    private void intent() {
        Intent keContentMain = new Intent(MainActivity.this, ContentMain.class);
        keContentMain.putExtra("key",katakunci);
        startActivity(keContentMain);
    }

    //tab setting
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //button setting
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id. action_settings){
            Intent intent = new Intent(MainActivity.this,PanduanActivity.class);
            startActivity(intent);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}