package com.example.appmabbicaracommunity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class KalimatUmumIndoActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{
    private EditText search_field;
    private ImageButton search_btn;
    private RecyclerView recycle_list;
    private TextToSpeech ttsinggris,ttsindo;
    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalimat_umum_indo);
        mUserDatabase = FirebaseDatabase.getInstance().getReference("KalimatUmumIndo");
        recycle_list = findViewById(R.id.recycle_list_indo);
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
        Query firebaseSearchQuery = mUserDatabase.orderByChild("indonesia").startAt(searchText).endAt(searchText + "\uf8ff");
        FirebaseRecyclerAdapter<Users, KalimatUmumIndoActivity.UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, KalimatUmumIndoActivity.UsersViewHolder>
                (

                        Users.class,
                        R.layout.list_layout,
                        KalimatUmumIndoActivity.UsersViewHolder.class,
                        firebaseSearchQuery
                ) {

            @Override
            protected void populateViewHolder(KalimatUmumIndoActivity.UsersViewHolder viewHolder, Users model, int position) {
                viewHolder.setDetails(getApplication().getApplicationContext(),model.getInggris(),model.getIndonesia(),ttsinggris,ttsindo);

            }
        };
        recycle_list.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {
            int resultinggris = ttsinggris.setLanguage(Locale.ROOT);
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
        public ImageButton audiotexttengah, audiotextatas, audiotextbawah, sharetext;
        public UsersViewHolder (View itemView){
            super(itemView);

            mView = itemView;
        }



        public void setDetails(Context ctx, final String userInggris, String userIndonesia, final TextToSpeech ttsinggris, final TextToSpeech ttsindo) {
            audiotextatas = mView.findViewById(R.id.audiotextatas);
            audiotexttengah = mView.findViewById(R.id.audiotexttengah);
            audiotextbawah = mView.findViewById(R.id.audiotextbawah);

            audiotextbawah.setVisibility(View.GONE);
            audiotextatas.setVisibility(View.GONE);

            TextView user_inggris = (TextView) mView.findViewById(R.id.textView2);;
            TextView user_indonesia = (TextView) mView.findViewById(R.id.textView3);
            TextView user_3 = (TextView) mView.findViewById(R.id.textView4);

            user_3.setVisibility(View.GONE);

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

            audiotexttengah.setOnClickListener(new View.OnClickListener() {
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