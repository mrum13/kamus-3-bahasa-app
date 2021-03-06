package com.example.appmabbicaracommunity.indonesiajepang;

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
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class IndonesiaJepangFragment extends Fragment implements TextToSpeech.OnInitListener {
    private static final int RECOGNIZER_RESULT = 1;
    private static final int RESULT_OK = -1;
    private EditText search_field;
    private ImageButton search_btn;
    private CircleImageView voice;

    private RecyclerView result_list;

    private DatabaseReference mUserDatabase;
    private TextToSpeech ttsjepang,ttsindo,ttsinggris;

    String searchText;

    private IndonesiaJepangViewModel indonesiaJepangViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        indonesiaJepangViewModel =
                ViewModelProviders.of(this).get(IndonesiaJepangViewModel.class);
        View root = inflater.inflate(R.layout.fragment_indonesiajepang, container, false);
        final EditText editText = root.findViewById(R.id.search_field);
        search_field = root.findViewById(R.id.search_field);
        indonesiaJepangViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                editText.setText(s);
            }
        });

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ttsjepang = new TextToSpeech(getActivity(), this);
        ttsindo = new TextToSpeech(getActivity(), this);
        ttsinggris = new TextToSpeech(getActivity(),this);

//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.menu_IndonesiaJepang);

        mUserDatabase = FirebaseDatabase.getInstance().getReference("indonesiainggrisjepang");
        search_field = view.findViewById(R.id.search_field);
        search_btn = view.findViewById(R.id.search_btn);
        result_list = view.findViewById(R.id.result_list);
        result_list.setHasFixedSize(true);
        result_list.setLayoutManager(new LinearLayoutManager(getActivity()));

        voice = view.findViewById(R.id.voice_image);

        searchText = search_field.getText().toString();
        firebaseUserSearch(searchText);

        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent speachIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                speachIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                speachIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speach to text");
                speachIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.JAPANESE);
                startActivityForResult(speachIntent,RECOGNIZER_RESULT);
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
        Toast.makeText(IndonesiaJepangFragment.this.getActivity(), "started Search", Toast.LENGTH_LONG).show();
        Query firebaseSearchQuery = mUserDatabase.orderByChild("indonesia").startAt(searchText).endAt(searchText + "\uf8ff");
        FirebaseRecyclerAdapter<Users, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>
                (
                        Users.class,
                        R.layout.list_layout,
                        UsersViewHolder.class,
                        firebaseSearchQuery
                ) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, Users model, int position) {
                viewHolder.setDetails(getActivity().getApplicationContext(), model.getIndonesia(), model.getLatin(), model.getJepang(),model.getInggris(), ttsindo,ttsjepang,ttsinggris);

            }
        };
        result_list.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {
            int resultjepang = ttsjepang.setLanguage(Locale.JAPANESE);
            int resultindo = ttsindo.setLanguage(Locale.ROOT);
            int resultinggris = ttsinggris.setLanguage(Locale.ENGLISH);

            if (resultjepang == TextToSpeech.LANG_MISSING_DATA
                    || resultjepang == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(getActivity(), "Language not supported", Toast.LENGTH_SHORT).show();
            } else {

            }

        } else {
            Toast.makeText(getActivity(), "Init failed", Toast.LENGTH_SHORT).show();
        }

    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public ImageButton audiotextjepang,audiotextindo,audiotextinggris;
        public UsersViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }


        public void setDetails(final Context ctx,  final String userIndonesia, final String userLatin, final String userJepang, final String userInggris, final TextToSpeech ttsindo, final TextToSpeech ttsjepang,final TextToSpeech ttsinggris) {

            audiotextjepang = mView.findViewById(R.id.audiotextbawah);
            audiotextindo = mView.findViewById(R.id.audiotextatas);
            audiotextinggris = mView.findViewById(R.id.audiotexttengah);


            final TextView user_indonesia = (TextView) mView.findViewById(R.id.textView2);
//            final TextView user_latin = (TextView) mView.findViewById(R.id.textView3);
            final TextView user_jepang = (TextView) mView.findViewById(R.id.textView4);
            final TextView user_inggirs = (TextView) mView.findViewById(R.id.textView3);


            user_indonesia.setText(userIndonesia);
//            user_latin.setText(userLatin);
            user_jepang.setText(userJepang);
            user_inggirs.setText(userInggris);

            audiotextjepang.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    //Toast.makeText(ctx, userLatin, Toast.LENGTH_LONG).show();
                    speakOutJepang(ttsjepang, userJepang);
                }
            });

            audiotextinggris.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    //Toast.makeText(ctx, userLatin, Toast.LENGTH_LONG).show();
                    speakOutInggris(ttsinggris, userInggris);
                }
            });

            audiotextindo.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    speakOutIndo(ttsindo, userIndonesia);
                }
            });
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void speakOutIndo(TextToSpeech tts, String userIndonesia) {
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

            String text = userIndonesia;
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, params, "Dummy String");
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void speakOutInggris(TextToSpeech tts, String userInggris) {
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

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void speakOutJepang(TextToSpeech tts, String userLatin) {
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
