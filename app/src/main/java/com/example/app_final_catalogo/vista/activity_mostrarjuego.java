package com.example.app_final_catalogo.vista;

import static com.example.app_final_catalogo.modelo.ColeccionDAO.getColeccion;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_final_catalogo.R;
import com.example.app_final_catalogo.modelo.JuegoDTO;

public class activity_mostrarjuego extends AppCompatActivity {

    private TextView tvNombreMostrar, tvPlataformaMostrar, tvGeneroMostrar, tvNotaMostrar2, tvCompletadoMostrar;
    ImageView ivImagen, ivLogoMostrar;
    Bundle datos;
    JuegoDTO juegoMostrado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrarjuego);
        juegoMostrado = new JuegoDTO();
        datos = getIntent().getExtras();
        String datosObtenidos = datos.getString("nombreTraspaso");

        for (String clave : getColeccion().keySet()) {
            if (datosObtenidos.equalsIgnoreCase(clave)){
                juegoMostrado = getColeccion().get(clave);
            }
        }

        // declarar todos los elementos de la vista con cada id
        tvNombreMostrar = findViewById(R.id.tvNombreMostrar);
        tvPlataformaMostrar = findViewById(R.id.tvPlataformaMostrar);
        tvGeneroMostrar = findViewById(R.id.tvGeneroMostrar);
        tvNotaMostrar2 = findViewById(R.id.tvNotaMostrar2);
        tvCompletadoMostrar = findViewById(R.id.tvCompletadoMostrar);
        ivImagen = findViewById(R.id.ivImagen);
        ivLogoMostrar = findViewById(R.id.ivLogoMostrar);

        // poner cada atributo en su campo
        tvNombreMostrar.setText(juegoMostrado.getNombre());
        tvPlataformaMostrar.setText(juegoMostrado.getPlataforma());
        tvGeneroMostrar.setText(juegoMostrado.getGenero());
        tvNotaMostrar2.setText(juegoMostrado.getNota().toString());
        Bitmap bitmap = BitmapFactory.decodeFile(juegoMostrado.getUriImagen());
        ivImagen.setImageBitmap(bitmap);
        if (juegoMostrado.getPlataforma().equalsIgnoreCase("Xbox")){
            ivLogoMostrar.setImageResource(R.mipmap.ic_xbox);
        } else if (juegoMostrado.getPlataforma().toString().equalsIgnoreCase("PC")) {
            ivLogoMostrar.setImageResource(R.mipmap.ic_windows);
        } else if (juegoMostrado.getPlataforma().toString().equalsIgnoreCase("Multiplataforma")) {
            ivLogoMostrar.setImageResource(R.mipmap.ic_multi);
        } else if (juegoMostrado.getPlataforma().toString().equalsIgnoreCase("Nintendo")) {
            ivLogoMostrar.setImageResource(R.mipmap.ic_switch);
        } else if (juegoMostrado.getPlataforma().toString().equalsIgnoreCase("PlayStation")) {
            ivLogoMostrar.setImageResource(R.mipmap.ic_ps);
        }

        if (juegoMostrado.isCompletado()) {
            tvCompletadoMostrar.setText("COMPLETADO");
        //    tvCompletadoMostrar.setBackgroundColor(R.color.completado);
        } else {
            tvCompletadoMostrar.setText("NO COMPLETADO");
        //    tvCompletadoMostrar.setBackgroundColor(R.color.noCompletado);
        }
    }
}