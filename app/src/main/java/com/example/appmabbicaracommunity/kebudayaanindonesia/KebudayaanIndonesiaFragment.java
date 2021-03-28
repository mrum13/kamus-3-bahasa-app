package com.example.appmabbicaracommunity.kebudayaanindonesia;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.appmabbicaracommunity.R;
import com.example.appmabbicaracommunity.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class KebudayaanIndonesiaFragment extends Fragment {
    private WebView webview;
    private KebudayaanIndonesiaViewModel kebudayaanIndonesiaViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        kebudayaanIndonesiaViewModel =
                ViewModelProviders.of(this).get(KebudayaanIndonesiaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_kebudayaanindonesia, container, false);

        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.menu_Indonesia);

        webview = (WebView) view.findViewById(R.id.webview);


        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);

        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);
        // Baris di bawah untuk menambahkan scrollbar di dalam WebView-nya
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(Uri.parse("file:///android_asset/kebudayaanindonesia.html").toString());

    }

}