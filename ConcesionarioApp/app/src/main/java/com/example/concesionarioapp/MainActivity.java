package com.example.concesionarioapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton btnConsultar = findViewById(R.id.btnConsultar);
        btnConsultar.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ConsultarActivity.class);
            startActivity(intent);
        });

        MaterialButton btnInsertar = findViewById(R.id.btnInsertar);
        btnInsertar.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, InsertarActivity.class);
            startActivity(intent);
        });

        MaterialButton btnBorrar = findViewById(R.id.btnBorrar);
        btnBorrar.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, BorrarActivity.class);
            startActivity(intent);
        });

        MaterialButton btnActualizar = findViewById(R.id.btnActualizar);
        btnActualizar.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ActualizarActivity.class);
            startActivity(intent);
        });
    }
}



