package com.example.app_final_catalogo.vista;

import static com.example.app_final_catalogo.vista.MainActivity.modelo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_final_catalogo.R;
import com.example.app_final_catalogo.modelo.ColeccionDAO;
import com.example.app_final_catalogo.modelo.JuegoDTO;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;

public class activity_insertar extends AppCompatActivity {

    private Spinner spGenero, spNota;
    private TextView tvNombre, tvPlataforma, tvGenero, tvNota;
    private EditText etNombre;
    private ImageView img;
    private CheckBox cbCompletado;
    private RadioGroup rg;
    private RadioButton rbPlaysation, rbXbox, rbNintendo, rbPc, rbOtras, rbMultiplataforma;
    private String[] listaGenero = {"", "Aventuras", "Acción", "Deportes", "Conducción", "Puzzle",
            "Simulación", "RPG", "Aventura gráfica", "Lucha", "Estrategia"};
    private Integer[] listaNota = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private Button btFoto;
    private Camera camera;
    private int cameraId = 0;
    private Bitmap imgBitmap;
    private static String nombreFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);

        etNombre = findViewById(R.id.etNombre);
        tvNombre = findViewById(R.id.tvNombreJuego);
        tvPlataforma = findViewById(R.id.tvPlataforma);
        rg = findViewById(R.id.rg);
        tvGenero = findViewById(R.id.tvGenero);
        tvNota = findViewById(R.id.tvNota);
        spGenero = findViewById(R.id.spGenero);
        ArrayAdapter<String> adaptador1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaGenero);
        spGenero.setAdapter(adaptador1);
        spNota = findViewById(R.id.spNota);
        ArrayAdapter<Integer> adaptador2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaNota);
        spNota.setAdapter(adaptador2);
        rbPlaysation = findViewById(R.id.rbPlaystation);
        rbNintendo = findViewById(R.id.rbNintendo);
        rbXbox = findViewById(R.id.rbXbox);
        rbPc = findViewById(R.id.rbPc);
        rbMultiplataforma = findViewById(R.id.rbMultiplataforma);
        cbCompletado = findViewById(R.id.cbCompletado);
        btFoto = findViewById(R.id.btFoto);
        img = (ImageView) findViewById(R.id.img);
    }

    public void guardarDatos(View v) {

        String nombre = etNombre.getText().toString();
        if (nombre.isEmpty()) {
            nombre = " ";
        }
        String plataforma = seleccionPlataforma().toString();
        String genero = spGenero.getSelectedItem().toString();
        Integer nota = Integer.parseInt(spNota.getSelectedItem().toString());
        boolean completado = cbCompletado.isChecked();
        String uriImagen = "/data/data/com.example.app_final_catalogo/files/" + nombre + ".jpg";
        setNombreFoto(nombre);
        guardarFoto();
        JuegoDTO juego = new JuegoDTO(nombre, plataforma, genero, nota, completado, uriImagen);
        boolean nombreRepetido = false;

        for (Map.Entry<String, JuegoDTO> juegoDTO : ColeccionDAO.getColeccion().entrySet()) {
            if (nombre.equalsIgnoreCase(juegoDTO.getKey())) {
                nombreRepetido = true;
            }
        }

        // Comprobaciones de campos
        if (nombre == " ") {
            Toast.makeText(this, "Introduce un nombre", Toast.LENGTH_SHORT).show();
        } else if (nombreRepetido == true) {
            Toast.makeText(this, "Ya existe un juego con ese nombre", Toast.LENGTH_SHORT).show();
            nombreRepetido = false;
        } else if (plataforma == " ") {
            Toast.makeText(this, "Selecciona una plataforma", Toast.LENGTH_SHORT).show();
        } else if (genero == "") {
            Toast.makeText(this, "Selecciona un genero", Toast.LENGTH_SHORT).show();
        } else {

            modelo.anyadirJuego(juego);
            limpiarCampos(v);
            Toast.makeText(this, "Juego añadido", Toast.LENGTH_SHORT).show();
            Log.i("Insertar", "----- Juego añadido -----");
            modelo.exportarDatos();
        }
    }

    public String seleccionPlataforma() {

        String plataformaSeleccionada = " ";

        if (rbNintendo.isChecked()) {
            plataformaSeleccionada = "Nintendo";
        } else if (rbPlaysation.isChecked()) {
            plataformaSeleccionada = "PlayStation";
        } else if (rbXbox.isChecked()) {
            plataformaSeleccionada = "XBox";
        } else if (rbPc.isChecked()) {
            plataformaSeleccionada = "PC";
        } else if (rbPlaysation.isChecked()) {
            plataformaSeleccionada = "PlayStation";
        } else if (rbMultiplataforma.isChecked()) {
            plataformaSeleccionada = "Multiplataforma";
        }
        return plataformaSeleccionada;
    }

    public void limpiarCampos(View v) {

        etNombre.setText("");
        rbPlaysation.setChecked(false);
        rbMultiplataforma.setChecked(false);
        rbPc.setChecked(false);
        rbXbox.setChecked(false);
        rbNintendo.setChecked(false);
        spGenero.setSelection(0);
        spNota.setSelection(0);
        cbCompletado.setChecked(false);
        Log.i("Insertar", "----- Campos restaurados -----");
        img.setImageBitmap(null);

    }

    // Tomar foto y mostrarla en la miniatura
    public void tomarFoto(View v) {
        camaraLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
    }

    ActivityResultLauncher<Intent> camaraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        if (result.getResultCode() == RESULT_OK) {
                            Bundle extras = result.getData().getExtras();
                            imgBitmap = (Bitmap) extras.get("data");
                            img.setImageBitmap(imgBitmap);
                        }
                    }
                }
            });

    public void irAtras(View v) {
        finish();
        Log.i("Insertar", "----- Pantalla insertar cerrada -----");
    }

    public String getNombreFoto() {
        return this.nombreFoto;
    }

    public void setNombreFoto(String nombreFoto) {
        this.nombreFoto = nombreFoto;
    }

    public void guardarFoto() {
        try {
            FileOutputStream fos = openFileOutput( getNombreFoto() + ".jpg", Context.MODE_PRIVATE);
            imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void cargarDatosSeleccion() {
        etNombre.setText("HELLO");
        Log.i("--- TRASPASO ---", activity_listado.getJuegoATraspasar().getNombre().toString());
        if(activity_listado.getJuegoATraspasar().getPlataforma().toString() == "Nintendo"){
            rbNintendo.setChecked(true);
        } else if (activity_listado.getJuegoATraspasar().getPlataforma().toString() == "PlayStation") {
            rbPlaysation.setChecked(true);
        } else if (activity_listado.getJuegoATraspasar().getPlataforma().toString() == "Xbox") {
            rbXbox.setChecked(true);
        } else if (activity_listado.getJuegoATraspasar().getPlataforma().toString() == "PC") {
            rbPc.setChecked(true);
        } else if (activity_listado.getJuegoATraspasar().getPlataforma().toString() == "Multiplataforma") {
            rbMultiplataforma.setChecked(true);
        }

    }
}