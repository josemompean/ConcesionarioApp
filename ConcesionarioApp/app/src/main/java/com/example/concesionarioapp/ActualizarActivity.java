package com.example.concesionarioapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ActualizarActivity extends AppCompatActivity {
    private CochesDAO cochesDAO;
    private String cocheSeleccionadoID;
    private int selectedPosition = -1; // Variable para rastrear la posición seleccionada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);

        // Vinculación de vistas
        ListView listViewCoches = findViewById(R.id.listViewCochesActualizar);
        EditText etModelo = findViewById(R.id.etModelo);
        EditText etNumCV = findViewById(R.id.etNumCV);
        EditText etPrecio = findViewById(R.id.etPrecio);
        EditText etColor = findViewById(R.id.etColor);
        Button btnActualizar = findViewById(R.id.btnActualizarCoche);
        Button btnVolverMenu = findViewById(R.id.btnVolverMenu);

        cochesDAO = new CochesDAO(this);
        List<String[]> coches = cochesDAO.consultarCoches();

        // Crear lista para mostrar en el ListView
        List<String> cochesLista = new ArrayList<>();
        for (String[] coche : coches) {
            cochesLista.add(coche[1] + " " + coche[2]); // Marca + Modelo
        }

        // Configuración del adaptador del ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cochesLista);
        listViewCoches.setAdapter(adapter);

        // Evento al seleccionar un coche de la lista
        listViewCoches.setOnItemClickListener((parent, view, position, id) -> {
            String[] cocheSeleccionado = coches.get(position);
            cocheSeleccionadoID = cocheSeleccionado[0];
            selectedPosition = position; // Guardar la posición seleccionada
            etModelo.setText(cocheSeleccionado[2]);
            etNumCV.setText(cocheSeleccionado[3]);
            etPrecio.setText(cocheSeleccionado[4]);
            etColor.setText(cocheSeleccionado[5]);
        });

        // Evento para actualizar el coche seleccionado
        btnActualizar.setOnClickListener(v -> {
            if (selectedPosition == -1) {
                Toast.makeText(this, "Por favor, selecciona un coche para actualizar.", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                String modelo = etModelo.getText().toString().trim();
                int numCV = Integer.parseInt(etNumCV.getText().toString().trim());
                double precio = Double.parseDouble(etPrecio.getText().toString().trim());
                String color = etColor.getText().toString().trim();

                if (modelo.isEmpty() || color.isEmpty()) {
                    Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Actualizar coche en la base de datos
                int rowsUpdated = cochesDAO.actualizarCoche(cocheSeleccionadoID, modelo, numCV, precio, color);
                if (rowsUpdated > 0) {
                    Toast.makeText(this, "Coche actualizado con éxito", Toast.LENGTH_SHORT).show();
                    coches.set(selectedPosition, new String[]{cocheSeleccionadoID, coches.get(selectedPosition)[1], modelo, String.valueOf(numCV), String.valueOf(precio), color});
                    cochesLista.set(selectedPosition, coches.get(selectedPosition)[1] + " " + modelo);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(this, "Error al actualizar el coche.", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Introduce valores numéricos válidos para CV y Precio.", Toast.LENGTH_SHORT).show();
            }
        });

        // Evento para volver al menú principal
        btnVolverMenu.setOnClickListener(v -> finish());
    }
}


