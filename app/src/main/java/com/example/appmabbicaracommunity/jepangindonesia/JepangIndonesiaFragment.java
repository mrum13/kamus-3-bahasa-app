package com.example.appmabbicaracommunity.jepangindonesia;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmabbicaracommunity.R;
import com.example.appmabbicaracommunity.R;
import com.example.appmabbicaracommunity.Users;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class JepangIndonesiaFragment extends Fragment implements TextToSpeech.OnInitListener{
    private EditText search_field;
    private ImageButton search_btn;
    private static final int RECOGNIZER_RESULT = 1;
    private static final int RESULT_OK = -1;
    private CircleImageView voice;
    private String searchText;

    private RecyclerView result_list;

    private TextToSpeech ttsindo,ttsjepang;

    private DatabaseReference mUserDatabase;

    private JepangIndonesiaViewModel jepangIndonesiaViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        jepangIndonesiaViewModel =
                ViewModelProviders.of(this).get(JepangIndonesiaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_jepangindonesia, container, false);
        final EditText editText = root.findViewById(R.id.search_field);
        jepangIndonesiaViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                editText.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.menu_JepangIndonesia);

        mUserDatabase = FirebaseDatabase.getInstance().getReference("JepangIndonesia");
        search_field = view.findViewById(R.id.search_field);
        search_btn = view.findViewById(R.id.search_btn);
        result_list = view.findViewById(R.id.result_list);
        ttsindo = new TextToSpeech(getActivity(), (TextToSpeech.OnInitListener) this);
        ttsjepang = new TextToSpeech(getActivity(), (TextToSpeech.OnInitListener) this);
        result_list.setHasFixedSize(true);
        result_list.setLayoutManager(new LinearLayoutManager(getActivity()));

        voice = view.findViewById(R.id.voice_image);

        searchText = search_field.getText().toString();
        firebaseUserSearch(searchText);

        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent speachIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//                speachIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
//                speachIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speach to text");
//                speachIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.JAPANESE);
//                startActivityForResult(speachIntent,RECOGNIZER_RESULT);

                String language =  "ja-JP";
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,language);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language); //penentu bahasa
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, language);
                intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE,language);
                startActivityForResult(intent, RECOGNIZER_RESULT);

            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                searchText = search_field.getText().toString();

                firebaseUserSearch(searchText);

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RECOGNIZER_RESULT && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            search_field.setText(matches.get(0).toString());

            searchText = search_field.getText().toString();
            firebaseUserSearch(searchText);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void firebaseUserSearch(String searchText) {
        Toast.makeText(JepangIndonesiaFragment.this.getActivity(), "started Search", Toast.LENGTH_LONG).show();
        Query firebaseSearchQuery = mUserDatabase.orderByChild("jepang").startAt(searchText).endAt(searchText + "\uf8ff");
        FirebaseRecyclerAdapter<Users, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>
                (

                        Users.class,
                        R.layout.list_layout,
                        UsersViewHolder.class,
                        firebaseSearchQuery
                ) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, Users model, int position) {
                viewHolder.setDetails(getActivity().getApplicationContext(), model.getLatin(), model.getJepang(), model.getIndonesia(),ttsjepang,ttsindo);

            }
        };
        result_list.setAdapter(firebaseRecyclerAdapter);
    }
    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {
            int resultjepang = ttsjepang.setLanguage(Locale.JAPANESE);
            int resultindo = ttsindo.setLanguage(Locale.ROOT);
            if (resultjepang == TextToSpeech.LANG_MISSING_DATA
                    || resultjepang == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(getActivity(), "Language not supported", Toast.LENGTH_SHORT).show();
            } else {

            }

        } else {
            Toast.makeText(getActivity(), "Init failed", Toast.LENGTH_SHORT).show();
        }

    }
    public static class UsersViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public ImageButton audiotextatas,audiotextbawah, sharetext;
        public UsersViewHolder (View itemView){
            super(itemView);

            mView = itemView;
        }



        public void setDetails(Context ctx, final String userLatin, String userJepang, String userIndonesia, final TextToSpeech ttsjepang, final TextToSpeech ttsindo) {
            audiotextbawah = mView.findViewById(R.id.audiotextbawah);
            audiotextatas = mView.findViewById(R.id.audiotextatas);
            TextView user_latin = (TextView) mView.findViewById(R.id.textView2);
            TextView user_jepang = (TextView) mView.findViewById(R.id.textView3);
            TextView user_indonesia = (TextView) mView.findViewById(R.id.textView4);


            user_latin.setText(userLatin);
            user_jepang.setText(userJepang);
            user_indonesia.setText(userIndonesia);
            audiotextatas.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    //Toast.makeText(ctx, userLatin, Toast.LENGTH_LONG).show();
                    speakOutjepang(ttsjepang, userLatin);
                }
            });

            audiotextbawah.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    speakOutindo2(ttsindo, userIndonesia);

                }
            });
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void speakOutindo2(TextToSpeech ttsindo, String userIndonesia) {
            ttsindo.setOnUtteranceProgressListener(new UtteranceProgressListener() {
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

            String text = userIndonesia;
            ttsindo.speak(text, TextToSpeech.QUEUE_FLUSH, params, "Dummy String");
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void speakOutjepang(TextToSpeech tts, String userLatin) {
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

            String text = userLatin;
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, params, "Dummy String");
        }
    }
}