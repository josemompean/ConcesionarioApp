package com.example.concesionarioapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ConsultarActivity extends AppCompatActivity {
    private CochesDAO cochesDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);

        // Vinculación de vistas
        ListView listView = findViewById(R.id.listViewCoches);
        TextView emptyMessage = findViewById(R.id.tvEmptyMessage);
        Button btnVolverMenu = findViewById(R.id.btnVolverMenu); // Vincular btnVolverMenu al XML

        cochesDAO = new CochesDAO(this);

        // Obtener lista de coches
        List<String[]> cochesCompletos = cochesDAO.consultarCoches();
        if (cochesCompletos.isEmpty()) {
            // Mostrar mensaje de lista vacía
            listView.setVisibility(View.GONE);
            emptyMessage.setVisibility(View.VISIBLE);
            return;
        }

        // Crear lista para mostrar en el ListView
        List<String> cochesLista = new ArrayList<>();
        for (String[] coche : cochesCompletos) {
            cochesLista.add(coche[1] + " " + coche[2]); // Marca y modelo
        }

        // Configurar adaptador para el ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cochesLista);
        listView.setAdapter(adapter);

        // Configurar evento al seleccionar un elemento
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String[] cocheSeleccionado = cochesCompletos.get(position);
            String detalles = "Marca: " + cocheSeleccionado[1] + "\n" +
                    "Modelo: " + cocheSeleccionado[2] + "\n" +
                    "Caballos: " + cocheSeleccionado[3] + "\n" +
                    "Precio: " + cocheSeleccionado[4] + "\n" +
                    "Color: " + cocheSeleccionado[5];

            Toast.makeText(this, detalles, Toast.LENGTH_LONG).show();
        });

        // Evento para volver al menú principal
        btnVolverMenu.setOnClickListener(v -> finish());
    }
}







