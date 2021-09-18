package com.example.appmabbicaracommunity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class PanduanVideoIndoActivity extends AppCompatActivity {

    MediaController media_Controller;
    DisplayMetrics dm;
    VideoView videoViewSaya;
    TextView txtJudul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panduan_video_indo);

        // view
        videoViewSaya = (VideoView) findViewById(R.id.videoViewUtamaIndo);
        txtJudul = (TextView) findViewById(R.id.txtJudul);

        // inisialisasi
        media_Controller = new MediaController(this);
        dm = new DisplayMetrics();

        // ambil ukuran layar
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        int width = dm.widthPixels;

        // lebar dan tinggi video view diberi nilai agar menyesuaikan ukuran layout
        videoViewSaya.setMinimumWidth(width);
        videoViewSaya.setMinimumHeight(height);

        // kontroler video
        videoViewSaya.setMediaController(media_Controller);
    }

    private void muatVideo(String judul, String nama_file) {
        // kasih judul
        txtJudul.setText(judul);

        // ambil video
        videoViewSaya.setVideoPath("android.resource://" + getPackageName() + "/raw/" + nama_file);

        // mulai
        videoViewSaya.start();
    }

    public void video1(View view) {
        muatVideo("Pedoman Umum Ejaan Bahasa Indonesia", "videoindo1");
    }

    public void video2(View view) {
        muatVideo("Pedoman Umum Ejaan Bahasa Indonesia 2", "videoindo2");
    }
}