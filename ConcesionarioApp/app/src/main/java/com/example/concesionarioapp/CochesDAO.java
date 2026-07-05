package com.example.concesionarioapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CochesDAO {
    private final SQLiteDatabase db;

    public CochesDAO(Context context) {
        ConcesionarioDBHelper dbHelper = new ConcesionarioDBHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    public long insertarCoche(String marca, String modelo, int numCV, double precio, String color) {
        ContentValues values = new ContentValues();
        values.put("marca", marca);
        values.put("modelo", modelo);
        values.put("numCV", numCV);
        values.put("precio", precio);
        values.put("color", color);
        return db.insert("coches", null, values);
    }

    public List<String[]> consultarCoches() {
        List<String[]> coches = new ArrayList<>();
        Cursor cursor = db.query("coches", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String[] coche = new String[6];
            coche[0] = cursor.getString(cursor.getColumnIndexOrThrow("cod_coche"));
            coche[1] = cursor.getString(cursor.getColumnIndexOrThrow("marca"));
            coche[2] = cursor.getString(cursor.getColumnIndexOrThrow("modelo"));
            coche[3] = cursor.getString(cursor.getColumnIndexOrThrow("numCV"));
            coche[4] = cursor.getString(cursor.getColumnIndexOrThrow("precio"));
            coche[5] = cursor.getString(cursor.getColumnIndexOrThrow("color"));
            coches.add(coche);
        }
        cursor.close();
        return coches;
    }

    public List<String> obtenerMarcas() {
        List<String> marcas = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT DISTINCT marca FROM coches", null);

        while (cursor.moveToNext()) {
            marcas.add(cursor.getString(cursor.getColumnIndexOrThrow("marca")));
        }
        cursor.close();
        return marcas;
    }

    public List<String[]> obtenerCochesPorMarca(String marca) {
        List<String[]> coches = new ArrayList<>();
        Cursor cursor = db.query("coches", null, "marca = ?", new String[]{marca}, null, null, null);

        while (cursor.moveToNext()) {
            String[] coche = new String[6];
            coche[0] = cursor.getString(cursor.getColumnIndexOrThrow("cod_coche"));
            coche[1] = cursor.getString(cursor.getColumnIndexOrThrow("marca"));
            coche[2] = cursor.getString(cursor.getColumnIndexOrThrow("modelo"));
            coche[3] = cursor.getString(cursor.getColumnIndexOrThrow("numCV"));
            coche[4] = cursor.getString(cursor.getColumnIndexOrThrow("precio"));
            coche[5] = cursor.getString(cursor.getColumnIndexOrThrow("color"));
            coches.add(coche);
        }
        cursor.close();
        return coches;
    }

    public int borrarCochePorModelo(String modelo) {
        return db.delete("coches", "modelo = ?", new String[]{modelo});
    }

    public int actualizarCoche(String codCoche, String modelo, int numCV, double precio, String color) {
        ContentValues values = new ContentValues();
        values.put("modelo", modelo);
        values.put("numCV", numCV);
        values.put("precio", precio);
        values.put("color", color);

        return db.update("coches", values, "cod_coche = ?", new String[]{codCoche});
    }
}

