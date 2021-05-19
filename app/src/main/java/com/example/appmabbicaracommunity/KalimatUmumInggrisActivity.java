package com.example.appmabbicaracommunity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Locale;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class KalimatUmumInggrisActivity extends AppCompatActivity implements TextToSpeech.OnInitListener  {
    private EditText search_field;
    private ImageButton search_btn;
    private RecyclerView recycle_list;
    private TextToSpeech ttsinggris,ttsindo;
    private DatabaseReference mUserDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalimat_umum_inggris);
        mUserDatabase = FirebaseDatabase.getInstance().getReference("KalimatUmumInggris");
        recycle_list = findViewById(R.id.recycle_list);
        ttsinggris = new TextToSpeech(getApplicationContext(), (TextToSpeech.OnInitListener) this);
        ttsindo = new TextToSpeech(getApplicationContext(), (TextToSpeech.OnInitListener) this);
        recycle_list.setHasFixedSize(true);
        recycle_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        search_field = findViewById(R.id.search_field);
        search_btn = findViewById(R.id.search_btn);


        search_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String searchText = search_field.getText().toString();

                firebaseUserSearch(searchText);

            }
        });

    }

    private void firebaseUserSearch(String searchText) {
        Query firebaseSearchQuery = mUserDatabase.orderByChild("inggris").startAt(searchText).endAt(searchText + "\uf8ff");
        FirebaseRecyclerAdapter<Users, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>
                (

                        Users.class,
                        R.layout.list_layout,
                        UsersViewHolder.class,
                        firebaseSearchQuery
                ) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, Users model, int position) {
                viewHolder.setDetails(getApplication().getApplicationContext(),model.getInggris(),model.getIndonesia(),ttsinggris,ttsindo);

            }
        };
        recycle_list.setAdapter(firebaseRecyclerAdapter);
    }
    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {
            int resultinggris = ttsinggris.setLanguage(Locale.ENGLISH);
            int resultindo = ttsindo.setLanguage(Locale.ROOT);

            if (resultinggris == TextToSpeech.LANG_MISSING_DATA
                    || resultinggris == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
            } else {

            }

        } else {
            Toast.makeText(getApplicationContext(), "Init failed", Toast.LENGTH_SHORT).show();
        }

    }
    public static class UsersViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public ImageButton audiotextbawah, audiotextatas, sharetext;
        public UsersViewHolder (View itemView){
            super(itemView);

            mView = itemView;
        }



        public void setDetails(Context ctx, final String userInggris, String userIndonesia, final TextToSpeech ttsinggris,final TextToSpeech ttsindo) {
            audiotextatas = mView.findViewById(R.id.audiotextatas);
            audiotextbawah = mView.findViewById(R.id.audiotextbawah);

            TextView user_inggris = (TextView) mView.findViewById(R.id.textView2);;
            TextView user_indonesia = (TextView) mView.findViewById(R.id.textView3);

            user_inggris.setText(userInggris);
            user_indonesia.setText(userIndonesia);

            audiotextatas.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    //Toast.makeText(ctx, userLatin, Toast.LENGTH_LONG).show();
                    speakOut(ttsinggris, userInggris);
                }
            });

            audiotextbawah.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    //Toast.makeText(ctx, userLatin, Toast.LENGTH_LONG).show();
                    speakOut(ttsindo, userIndonesia);
                }
            });
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void speakOut(TextToSpeech tts, String userInggris) {
            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {

                }

                @Override
                public void onDone(String utteranceId) {

                }

                @Override
                public void onError(String utteranceId) {

                }
            });

            Bundle params = new Bundle();
            params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "");

            String text = userInggris;
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, params, "Dummy String");
        }
    }
}
