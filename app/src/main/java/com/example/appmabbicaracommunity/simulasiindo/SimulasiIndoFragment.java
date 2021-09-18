package com.example.appmabbicaracommunity.simulasiindo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.appmabbicaracommunity.HewanInggrisActivity;
import com.example.appmabbicaracommunity.KalimatUmumIndoActivity;
import com.example.appmabbicaracommunity.KalimatUmumInggrisActivity;
import com.example.appmabbicaracommunity.NumberInggrisActivity;
import com.example.appmabbicaracommunity.PanduanVideoIndoActivity;
import com.example.appmabbicaracommunity.PanduanVideoInggrisActivity;
import com.example.appmabbicaracommunity.QuisIndoActivity;
import com.example.appmabbicaracommunity.QuisInggrisActivity;
import com.example.appmabbicaracommunity.R;

public class SimulasiIndoFragment extends Fragment {

    private Button buttonquis, buttonangka, buttonkalimatumum, buttonhewan, buttonpanduanvideoindo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_simulasi_indo, container, false);

        buttonquis = root.findViewById(R.id.btn_quis_indo);
        buttonangka = root.findViewById(R.id.btn_angka_indo);
        buttonkalimatumum = root.findViewById(R.id.btn_kalimatumum1_indo);
        buttonpanduanvideoindo = root.findViewById(R.id.btn_panduanvideoindo);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.menu_Simulasi_Indo);

        buttonquis.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), QuisIndoActivity.class);
                startActivity(i);

            }
        });
//        buttonangka.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getActivity(), NumberInggrisActivity.class);
//                startActivity(i);
//
//            }
//        });
        buttonkalimatumum.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), KalimatUmumIndoActivity.class);
                startActivity(i);

            }
        });
//        buttonhewan.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getActivity(), HewanInggrisActivity.class);
//                startActivity(i);
//
//            }
//        });
        buttonpanduanvideoindo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), PanduanVideoIndoActivity.class);
                startActivity(i);

            }
        });

        return root;
    }
}