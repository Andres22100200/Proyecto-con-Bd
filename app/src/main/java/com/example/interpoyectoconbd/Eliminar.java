package com.example.interpoyectoconbd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Eliminar extends AppCompatActivity {

    private Button regresar, eliminar;
    private String ave, lugar, fecha;
    private Spinner spinner;
    private CheckBox checkBox;
    private TextView eliminarnombreAve, elimilugar, eliminarfecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar);

        eliminarnombreAve = findViewById(R.id.eliminarnombreAve);
        elimilugar = findViewById(R.id.eliminarlugar);
        eliminarfecha = findViewById(R.id.eliminarfecha);

        spinner = findViewById(R.id.spinner);
        checkBox = findViewById(R.id.checkBox);
        regresar = findViewById(R.id.regresar);
        eliminar = findViewById(R.id.eliminar);

        // Botón "Regresar"
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Eliminar.this, Menu.class);
                startActivity(intent);
            }
        });

        // Configurar el Spinner
        String[] opciones = {"Ave 1", "Ave 2", "Ave 3", "Ave 4", "Ave 5", "Ave 6", "Ave 7", "Ave 8", "Ave 9", "Ave 10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Spinner Listener
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

        // CheckBox Listener
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                Toast.makeText(Eliminar.this, "Primero debes confirmar tus datos", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón "Eliminar"
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ave = eliminarnombreAve.getText().toString().trim();
                lugar = elimilugar.getText().toString().trim();
                fecha = eliminarfecha.getText().toString().trim();

                if (ave.isEmpty() || lugar.isEmpty() || fecha.isEmpty()) {
                    Toast.makeText(Eliminar.this, "No hay datos para eliminar", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Eliminar.this, "Datos eliminados", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}