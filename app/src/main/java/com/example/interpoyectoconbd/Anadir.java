package com.example.interpoyectoconbd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Anadir extends AppCompatActivity {

    private EditText aniadirnombreAve, aniadirlugar, aniadirfecha;
    private String ave, lugar, fecha;
    private Button regresar, guardar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir);

        aniadirnombreAve = findViewById(R.id.aniadirnombreAve);
        aniadirlugar = findViewById(R.id.aniadirlugar);
        aniadirfecha = findViewById(R.id.aniadirfecha);

        regresar = findViewById(R.id.regresar);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Anadir.this, Menu.class);
                startActivity(intent);
            }
        });

        guardar = findViewById(R.id.guardar);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener datos de los EditText

                ave = aniadirnombreAve.getText().toString().trim();
                lugar = aniadirlugar.getText().toString().trim();
                fecha = aniadirfecha.getText().toString().trim();

                // Validar que no estén vacíos
                if (ave.isEmpty() || lugar.isEmpty() || fecha.isEmpty()) {
                    Toast.makeText(Anadir.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    Toast.makeText(Anadir.this, "Registro añadido correctamente", Toast.LENGTH_SHORT).show();
                    aniadirnombreAve.setText("");
                    aniadirlugar.setText("");
                    aniadirfecha.setText("");
                }
            }

        });
    }

}