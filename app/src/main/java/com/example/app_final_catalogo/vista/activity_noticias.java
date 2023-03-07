package com.example.app_final_catalogo.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.app_final_catalogo.R;

public class activity_noticias extends AppCompatActivity {

    WebView wv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);

        wv1 = findViewById(R.id.wv1);
        String noticias = getIntent().getExtras().getString("Noticias");
        wv1.loadUrl(noticias);
        wv1.setWebViewClient(new WebViewClient());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wv1.canGoBack()) {
            wv1.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}