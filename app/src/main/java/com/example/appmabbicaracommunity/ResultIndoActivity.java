package com.example.appmabbicaracommunity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultIndoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_indo);

        TextView hasil = (TextView)findViewById(R.id.hasil);
        TextView nilai = (TextView)findViewById(R.id.nilai);

        hasil.setText("Jawaban Benar :"+ QuisIndoActivity.benar+"\nJawaban Salah :"+ QuisIndoActivity.salah);
        nilai.setText(""+ QuisIndoActivity.hasil);
    }

    public void ulangi(View view){
        finish();
        Intent a = new Intent(getApplicationContext(), QuisIndoActivity.class);
        startActivity(a);
    }
}