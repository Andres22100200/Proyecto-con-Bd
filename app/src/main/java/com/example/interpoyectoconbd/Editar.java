package com.example.interpoyectoconbd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Editar extends AppCompatActivity {

    private EditText editarnombreAve, editarlugar, editarfecha;
    private String ave, lugar, fecha;
    private Spinner spinner;
    private Button editarguardar, editarregresar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        editarnombreAve = findViewById(R.id.editarnombreAve);
        editarlugar = findViewById(R.id.editarlugar);
        editarfecha = findViewById(R.id.editarfecha);
        spinner = findViewById(R.id.spinner);
        editarguardar = findViewById(R.id.editarguardar);
        editarregresar = findViewById(R.id.regresar);

        // Botón "Regresar"
        editarregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Editar.this, Menu.class);
                startActivity(intent);
            }
        });

        // Configurar el Spinner
        String[] opciones = {"Ave 1", "Ave 2", "Ave 3", "Ave 4", "Ave 5", "Ave 6", "Ave 7", "Ave 8", "Ave 9", "Ave 10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                // Puedes usar el valor seleccionado aquí
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nada seleccionado
            }
        });

        // Botón "Guardar"
        editarguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener datos de los EditText
                ave = editarnombreAve.getText().toString().trim();
                lugar = editarlugar.getText().toString().trim();
                fecha = editarfecha.getText().toString().trim();

                if (ave.isEmpty() || lugar.isEmpty() || fecha.isEmpty()) {
                    Toast.makeText(Editar.this, "No hay datos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Editar.this, "Datos guardados", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}