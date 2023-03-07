package com.example.app_final_catalogo.modelo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.app_final_catalogo.vista.MainActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Clase que alberga la coleccion completa de todos los juegos
 * y que es, al mismo tiempo, un mapa de multiples JuegoDTO
 */
public class ColeccionDAO extends MainActivity {

    // atributos
    private static Map<String, JuegoDTO> coleccion = null;

    // constructor
    public ColeccionDAO() {

        this.coleccion = new TreeMap<>();
        Log.i("Fichero", "----- Mapa inicializado -----");

    }

    // CREATE - UPDATE
    public void anyadirJuego(JuegoDTO juego) {
        if (!coleccion.containsKey(juego.getNombre())) {
            coleccion.put(juego.getNombre(), juego);
            Log.i("CRUD", "----- Juego añadido -----");
        } else if (coleccion.containsKey(juego.getNombre())) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }

        for (Map.Entry<String, JuegoDTO> juegoDTO : coleccion.entrySet()) {
            Log.i("Juego", "----- JUEGO: " + juegoDTO.toString());
        }
    }

    // DELETE
    public void borrarJuego(JuegoDTO juego) {
        if (coleccion.containsKey(juego.getNombre())) {
            coleccion.remove(juego.getNombre(), juego);
            Log.i("CRUD", "----- Juego eliminado ------");
        } else {
            Log.i("CRUD", "----- El juego no se encuentra en la coleccion -----");
        }
    }

    public void borrarJuego(String nombreJuego) {
        if (coleccion.containsKey(nombreJuego)) {
            coleccion.remove(nombreJuego, coleccion.get(nombreJuego));
            Log.i("CRUD", "----- Juego eliminado ------");
        }
    }

    // EXPORTAR DATOS
    public void exportarDatos() {

        File fichero = new File("/data/data/com.example.app_final_catalogo/datosGuardados.txt");
        FileWriter fw = null;
        BufferedWriter bw = null;

        try {

            fw = new FileWriter(fichero);
            bw = new BufferedWriter(fw);

            for (Map.Entry<String, JuegoDTO> juego : coleccion.entrySet()) {
                bw.write(juego.getValue().toString());
            }

            bw.flush();
            bw.close();
            Log.i("Fichero", "----- Datos exportados -----");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.w("Fichero", "----- No se ha podido guardar el archivo -----");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // IMPORTAR DATOS
    public void importarDatos() {
        String[] datosPorLinea = null;

        File file = new File("/data/data/com.example.app_final_catalogo/datosGuardados.txt");
        FileReader fr = null;
        BufferedReader br = null;
        Scanner scannerDatos = null;

        try {

            if (file.exists()) {
                fr = new FileReader(file);
                br = new BufferedReader(fr);
                scannerDatos = new Scanner(br);

                while (scannerDatos.hasNext()) {
                    datosPorLinea = scannerDatos.nextLine().split("::");
                    String nombre = datosPorLinea[0];
                    String plataforma = datosPorLinea[1];
                    String genero = datosPorLinea[2];
                    Integer nota = Integer.parseInt(datosPorLinea[3]);
                    boolean completado = Boolean.parseBoolean(datosPorLinea[4]);
                    String uriImagen = datosPorLinea[5];
                    JuegoDTO juego = new JuegoDTO(nombre, plataforma, genero, nota, completado, uriImagen);
                    anyadirJuego(juego);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.w("Fichero", "----- No se ha podido cargar el archivo -----");
            Toast.makeText(this, "No se ha podido leer el archivo", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (scannerDatos != null) {
                    scannerDatos.close();
                }
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.i("Fichero", "----- Datos importados -----");
    }

    public static Map<String, JuegoDTO> getColeccion() {
        return coleccion;
    }
}