package com.example.app_final_catalogo.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

import com.example.app_final_catalogo.R;

public class activity_menu extends AppCompatActivity {

    private VideoView vv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        vv1 = findViewById(R.id.vv1);
        vv1.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.trailer1));
        Log.i("Menu", "----- Video cargado -----");
        vv1.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        vv1.start();
    }

    public void irAVistaInsertar(View v) {

        Intent intent = new Intent(this, activity_insertar.class);
        startActivity(intent);

    }

    public void irAVistaListado(View v) {

        Intent intent = new Intent(this, activity_listado.class);
        startActivity(intent);

    }

    public void irAVistaNoticias(View v) {

        Intent intent = new Intent(this, activity_noticias.class);
        intent.putExtra("Noticias", "www.3djuegos.com");
        startActivity(intent);

    }

    public void irAVistaInfo(View v) {

        Intent intent = new Intent(this, activity_info.class);
        startActivity(intent);

    }
}