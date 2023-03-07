package com.example.app_final_catalogo.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.app_final_catalogo.R;
import com.example.app_final_catalogo.modelo.ColeccionDAO;

public class MainActivity extends AppCompatActivity {

    public static ColeccionDAO modelo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("INICIO","----- APP INICIADA -----");
                Intent intent = new Intent(MainActivity.this, activity_menu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }, 1000);

        modelo = new ColeccionDAO();
        modelo.importarDatos();

    }
}