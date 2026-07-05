package com.example.concesionarioapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class BorrarActivity extends AppCompatActivity {
    private CochesDAO cochesDAO;
    private Spinner spinnerMarcas;
    private ListView listViewCoches;
    private Button btnVolverMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrar);

        cochesDAO = new CochesDAO(this);
        spinnerMarcas = findViewById(R.id.spinnerMarcas);
        listViewCoches = findViewById(R.id.listViewCochesBorrar);
        btnVolverMenu = findViewById(R.id.btnVolverMenu);

        List<String> marcas = cochesDAO.obtenerMarcas();
        if (marcas.isEmpty()) {
            Toast.makeText(this, "No hay marcas disponibles.", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, marcas);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMarcas.setAdapter(spinnerAdapter);

        spinnerMarcas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String marcaSeleccionada = marcas.get(position);
                List<String[]> cochesPorMarca = cochesDAO.obtenerCochesPorMarca(marcaSeleccionada);

                if (cochesPorMarca.isEmpty()) {
                    Toast.makeText(BorrarActivity.this, "No hay coches para esta marca.", Toast.LENGTH_SHORT).show();
                    listViewCoches.setAdapter(null);
                    return;
                }

                List<String> modelos = new ArrayList<>();
                for (String[] coche : cochesPorMarca) {
                    modelos.add(coche[2]);
                }

                ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(BorrarActivity.this, android.R.layout.simple_list_item_1, modelos);
                listViewCoches.setAdapter(listViewAdapter);

                listViewCoches.setOnItemClickListener((adapterView, itemView, i, l) -> {
                    String modeloSeleccionado = modelos.get(i);
                    new AlertDialog.Builder(BorrarActivity.this)
                            .setTitle("Confirmar eliminación")
                            .setMessage("¿Estás seguro de que quieres borrar el coche: " + modeloSeleccionado + "?")
                            .setPositiveButton("Sí", (dialog, which) -> {
                                cochesDAO.borrarCochePorModelo(modeloSeleccionado);
                                Toast.makeText(BorrarActivity.this, "Coche borrado: " + modeloSeleccionado, Toast.LENGTH_SHORT).show();
                                modelos.remove(i);
                                listViewAdapter.notifyDataSetChanged();
                            })
                            .setNegativeButton("No", null)
                            .show();
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnVolverMenu.setOnClickListener(v -> finish());
    }
}


