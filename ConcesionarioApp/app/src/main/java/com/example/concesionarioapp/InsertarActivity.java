package com.example.concesionarioapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InsertarActivity extends AppCompatActivity {
    private CochesDAO cochesDAO;
    private EditText etMarca, etModelo, etNumCV, etPrecio, etColor;
    private Button btnInsertar, btnVolverMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);

        cochesDAO = new CochesDAO(this);

        etMarca = findViewById(R.id.etMarca);
        etModelo = findViewById(R.id.etModelo);
        etNumCV = findViewById(R.id.etNumCV);
        etPrecio = findViewById(R.id.etPrecio);
        etColor = findViewById(R.id.etColor);
        btnInsertar = findViewById(R.id.btnInsertar);
        btnVolverMenu = findViewById(R.id.btnVolverMenu);

        btnInsertar.setOnClickListener(v -> {
            try {
                String marca = etMarca.getText().toString().trim();
                String modelo = etModelo.getText().toString().trim();
                int numCV = Integer.parseInt(etNumCV.getText().toString().trim());
                double precio = Double.parseDouble(etPrecio.getText().toString().trim());
                String color = etColor.getText().toString().trim();

                if (!marca.isEmpty() && !modelo.isEmpty() && !color.isEmpty()) {
                    cochesDAO.insertarCoche(marca, modelo, numCV, precio, color);
                    Toast.makeText(this, "Coche insertado correctamente.", Toast.LENGTH_SHORT).show();
                    etMarca.setText("");
                    etModelo.setText("");
                    etNumCV.setText("");
                    etPrecio.setText("");
                    etColor.setText("");
                } else {
                    Toast.makeText(this, "Completa todos los campos.", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Introduce valores numéricos válidos en CV y Precio.", Toast.LENGTH_SHORT).show();
            }
        });

        btnVolverMenu.setOnClickListener(v -> finish());
    }
}


