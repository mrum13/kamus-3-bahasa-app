package com.example.appmabbicaracommunity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class QuisIndoActivity extends AppCompatActivity {

    TextView kuis;
    RadioGroup rg;
    RadioButton PilihanA, PilihanB, PilihanC, PilihanD;
    int nomor = 0;
    public static int hasil, benar, salah;

    //pertanyaan
    String[] pertanyaan_kuis = new String[]{
            "Ada berapakah huruf abjad yang dipakai dalam ejaan bahasa Indonesia...",
            "Sebutkan huruf yang melambangkan vokal dalam bahasa Indonesia",
            "Di dalam bahasa Indonesia terdapat empat huruf diftong yaitu",
            "Sebutkan macam-macam imbuhan",
            "Apa fungsi dari pemakaian huruf tebal"
    };

    //pilihan jawaban a, b, c, d
    String[] pilihan_jawaban = new String[]{
            "16", "20", "26", "30",
            "A, i, u, e, o", "A, b, c, d, e", "V, d, x, g, z", "J, k, m, q, r",
            "Me, meny, meng, mem", "Ai, au, ei, oi", "Di, ke, ter, se", "Di-ke, me-kan, di-i, se-nya",
            "–isme, -man, -wan, atau –wi", "At, -al, -if, -is", "Ai, au, ei, oi", "Prefiks, infiks, sufiks, konfiks",
            "Sebagai tanda baca", "Untuk menegaskan bagian karangan seperti buku, bab, atau subbab", "Dipakai untuk memaknai sebuah karangan", "Dipakai untuk memulainya percakapan atau dialog"
    };

    //jawaban benar
    String[] jawaban_benar = new String[]{
            "26",
            "A, i, u, e, o",
            "Ai, au, ei, oi",
            "Prefiks, infiks, sufiks, konfiks",
            "Untuk menegaskan bagian karangan seperti buku, bab, atau subbab"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quis_indo);
        kuis = (TextView) findViewById(R.id.kuis);
        rg = (RadioGroup) findViewById(R.id.pilihan);
        PilihanA = (RadioButton) findViewById(R.id.pilihanA);
        PilihanB = (RadioButton) findViewById(R.id.pilihanB);
        PilihanC = (RadioButton) findViewById(R.id.pilihanC);
        PilihanD = (RadioButton) findViewById(R.id.pilihanD);

        kuis.setText(pertanyaan_kuis[nomor]);
        PilihanA.setText(pilihan_jawaban[0]);
        PilihanB.setText(pilihan_jawaban[1]);
        PilihanC.setText(pilihan_jawaban[2]);
        PilihanD.setText(pilihan_jawaban[3]);

        rg.check(0);
        benar = 0;
        salah = 0;
    }

    public void next(View view) {
        if (PilihanA.isChecked() || PilihanB.isChecked() || PilihanC.isChecked() || PilihanD.isChecked()) {

            RadioButton jawaban_user = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
            String ambil_jawaban_user = jawaban_user.getText().toString();
            rg.check(0);
            if (ambil_jawaban_user.equalsIgnoreCase(jawaban_benar[nomor])) benar++;
            else salah++;
            nomor++;
            if (nomor < pertanyaan_kuis.length) {
                kuis.setText(pertanyaan_kuis[nomor]);
                PilihanA.setText(pilihan_jawaban[(nomor * 4) + 0]);
                PilihanB.setText(pilihan_jawaban[(nomor * 4) + 1]);
                PilihanC.setText(pilihan_jawaban[(nomor * 4) + 2]);
                PilihanD.setText(pilihan_jawaban[(nomor * 4) + 3]);

            } else {
                hasil = benar * 20;
                Intent selesai = new Intent(getApplicationContext(), ResultIndoActivity.class);
                startActivity(selesai);
            }
        }
        else {
            Toast.makeText(this,"Kamu Jawab Dulu", Toast.LENGTH_LONG).show();
        }
    }
}