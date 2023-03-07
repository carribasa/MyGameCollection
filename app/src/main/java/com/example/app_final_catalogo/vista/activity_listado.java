package com.example.app_final_catalogo.vista;


import static com.example.app_final_catalogo.modelo.ColeccionDAO.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_final_catalogo.R;
import com.example.app_final_catalogo.modelo.*;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Map;

public class activity_listado extends AppCompatActivity {

    public static ArrayList<JuegoDTO> datosLista;
    RecyclerView rv1;
    public static JuegoDTO juegoATraspasar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        datosLista = new ArrayList<JuegoDTO>();
        // Añadimos los datos de cada juego al listado
        for (String clave : getColeccion().keySet()) {
            JuegoDTO juego = getColeccion().get(clave);
            datosLista.add(juego);
            Log.i("Listado", "----- Datos de juego añadidos a datosLista -----");
            Log.i("--- DATOS ---", juego.toString());
        }

//        listadoNombres.add("Elden Ring");
//        listadoNombres.add("A plague tale: Requiem");
//        listadoNombres.add("Devil May Cry 5");
//        listadoNombres.add("Ratchet & Clank: Rift apart");
//        listadoNombres.add("TWD: The first season");
//        listadoNombres.add("Spiderman: Miles Morales");
//        caratulas = new ArrayList<Integer>();
//        caratulas.add(R.drawable.eldenringcaratula);
//        caratulas.add(R.drawable.aplaguetalecaratula);
//        caratulas.add(R.drawable.devilmaycrycaratula);
//        caratulas.add(R.drawable.ratchetcaratula);
//        caratulas.add(R.drawable.twdcaratula);
//        caratulas.add(R.drawable.spidermancaratula);

        rv1 = findViewById(R.id.rv1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv1.setLayoutManager(linearLayoutManager);
        rv1.setAdapter(new AdaptadorListado());
    }

    private class AdaptadorListado extends RecyclerView.Adapter<AdaptadorListado.AdaptadorListadoHolder> {

        @NonNull
        @Override
        public AdaptadorListadoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdaptadorListadoHolder(getLayoutInflater().inflate(R.layout.listado_juegos, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull AdaptadorListadoHolder holder, int position) {
            holder.imprimir(position);
            int posicion = position;
            JuegoDTO juego = null;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogoGestion(posicion).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return datosLista.size();
        }

        private class AdaptadorListadoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            ImageView ivCaratula;
            TextView tvNombreListado, tvGeneroListado, tvNotaListado, tvPlataformaListado;

            public AdaptadorListadoHolder(@NonNull View itemView) {
                super(itemView);
                ivCaratula = itemView.findViewById(R.id.ivCaratula);
                tvNombreListado = itemView.findViewById(R.id.tvNombreListado);
                tvPlataformaListado = itemView.findViewById(R.id.tvPlataformaListado);
                tvGeneroListado = itemView.findViewById(R.id.tvGeneroListado);
                tvNotaListado = itemView.findViewById(R.id.tvNotaListado);
            }

            public void imprimir(int position) {

                Bitmap bitmap = BitmapFactory.decodeFile(datosLista.get(position).getUriImagen().toString());
                Log.i("--- COLECCION ---", datosLista.get(position).getUriImagen().toString());
                ivCaratula.setImageBitmap(bitmap);
                tvNombreListado.setText(datosLista.get(position).getNombre().toString());
                tvPlataformaListado.setText(datosLista.get(position).getPlataforma().toString());
                tvGeneroListado.setText(datosLista.get(position).getGenero().toString());
                tvNotaListado.setText(datosLista.get(position).getNota().toString());

            }

            //-------HACER QUE AL PULSAR VAYA A LA ACTIVITY VER ARTICULO
            @Override
            public void onClick(View view) {

            }
        }
    }

    public AlertDialog dialogoGestion(int posicion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity_listado.this);

        builder.setTitle(datosLista.get(posicion).getNombre().toString())
                .setMessage("Elige una opcion")
                .setNeutralButton("Ver",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intentMostrar = new Intent(activity_listado.this, activity_mostrarjuego.class);
                                intentMostrar.putExtra("nombreTraspaso", datosLista.get(posicion).getNombre());
                                startActivity(intentMostrar);
                            }
                        })
                .setNegativeButton("Borrar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                modelo.borrarJuego(datosLista.get(posicion).getNombre().toString());
                                recreate();
                            }
                        });
        return builder.create();
    }

    public void irAtras(View v) {
        modelo.exportarDatos();
        finish();
        Log.i("Listado", "----- Pantalla listado cerrada -----");
    }

    public static JuegoDTO getJuegoATraspasar() {
        return juegoATraspasar;
    }
}