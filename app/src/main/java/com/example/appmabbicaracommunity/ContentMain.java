package com.example.appmabbicaracommunity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.appmabbicaracommunity.indonesiainggris.IndonesiaInggrisFragment;
import com.example.appmabbicaracommunity.indonesiajepang.IndonesiaJepangFragment;
import com.example.appmabbicaracommunity.inggrisindonesia.InggrisIndonesiaFragment;
import com.example.appmabbicaracommunity.inggrisjepang.InggrisJepangFragment;
import com.example.appmabbicaracommunity.jepangindonesia.JepangIndonesiaFragment;
import com.example.appmabbicaracommunity.jepanginggris.JepangInggrisFragment;
import com.example.appmabbicaracommunity.kebudayaanindonesia.KebudayaanIndonesiaFragment;
import com.example.appmabbicaracommunity.kebudayaaninggris.KebudayaanInggrisFragment;
import com.example.appmabbicaracommunity.kebudayaanjepang.KebudayaanJepangFragment;
import com.example.appmabbicaracommunity.simulasiinggris.SimulasiInggrisFragment;
import com.example.appmabbicaracommunity.simulasijepang.SimulasiJepangFragment;
import com.google.android.material.navigation.NavigationView;

public class ContentMain extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    String judul_intent, getKey;
    Fragment indjpn,jpnind,jpneng,engjpn,engind,indeng,budayaindo,budayajepang,budayainggris,simulasijepang,simulasiinggris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        indjpn = new IndonesiaJepangFragment();
        jpnind = new JepangIndonesiaFragment();
        jpneng = new JepangInggrisFragment();
        engjpn = new InggrisJepangFragment();
        engind = new InggrisIndonesiaFragment();
        indeng = new IndonesiaInggrisFragment();

        budayaindo = new KebudayaanIndonesiaFragment();
        budayajepang = new KebudayaanJepangFragment();
        budayainggris = new KebudayaanInggrisFragment();

        simulasiinggris = new SimulasiInggrisFragment();
        simulasijepang = new SimulasiJepangFragment();

        getIncomingIntent();

//        Fragment fg = new DashboardFragment();

    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("key")) {
            judul_intent = getIntent().getStringExtra("key");
//            kategori = getIntent().getStringExtra("toolbar");

            setDetailFragment(judul_intent);
        }
    }

    private void setDetailFragment(String getKey){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (getKey.equals("indtojpn")){
            transaction.replace(R.id.nav_host_fragment, indjpn);
        }
        else if (getKey.equals("jpntoind")){
            transaction.replace(R.id.nav_host_fragment, jpnind);
        }
        else if (getKey.equals("jpntoeng")){
            transaction.replace(R.id.nav_host_fragment, jpneng);
        }
        else if (getKey.equals("engtojpn")){
            transaction.replace(R.id.nav_host_fragment, engjpn);
        }
        else if (getKey.equals("engtoind")){
            transaction.replace(R.id.nav_host_fragment, engind);
        }
        else if (getKey.equals("indtoeng")){
            transaction.replace(R.id.nav_host_fragment, indeng);
        }

        else if (getKey.equals("budayaindo")){
            transaction.replace(R.id.nav_host_fragment, budayaindo);
        }
        else if (getKey.equals("budayajepang")){
            transaction.replace(R.id.nav_host_fragment, budayajepang);
        }
        else if (getKey.equals("budayainggris")){
            transaction.replace(R.id.nav_host_fragment, budayainggris);
        }

        else if (getKey.equals("simulasijepang")){
            transaction.replace(R.id.nav_host_fragment, simulasijepang);
        }
        else if (getKey.equals("simulasiinggris")){
            transaction.replace(R.id.nav_host_fragment, simulasiinggris);
        }
        transaction.commit();
    }
}