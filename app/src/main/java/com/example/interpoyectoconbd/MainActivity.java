package com.example.interpoyectoconbd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import okhttp3.*;
import org.json.JSONObject;
import org.json.JSONException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText nombre, contraseña;
    private String usuario, pass;
    private Button enviar;
    private TextView nuevo;
    private final String URL_API = "http://192.168.137.218/Proyecto.php"; // Cambia esta URL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre = findViewById(R.id.nombreusuario);
        contraseña = findViewById(R.id.contraseña);
        enviar = findViewById(R.id.enviar);
        nuevo = findViewById(R.id.nuevo);

        enviar.setOnClickListener(v -> envio());
        nuevo.setOnClickListener(v -> editar());
    }

    private void editar() {
        Intent intent = new Intent(MainActivity.this, RegistroUsuarios.class);
        startActivity(intent);
    }

    private void envio() {
        usuario = nombre.getText().toString().trim();
        pass = contraseña.getText().toString().trim();

        if (usuario.isEmpty() || pass.isEmpty()) {
            Toast.makeText(MainActivity.this, "Por favor ingrese todos sus datos", Toast.LENGTH_SHORT).show();
            return;
        }

        // esto realiza la solicitud al servidor
        OkHttpClient client = new OkHttpClient();

        // Crear el cuerpo JSON
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nombre", usuario);
            jsonObject.put("contrasenia", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Crear RequestBody
        RequestBody body = RequestBody.create(
                jsonObject.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        // Crear la solicitud
        Request request = new Request.Builder()
                .url(URL_API)
                .post(body)
                .build();

        // Ejecutar la solicitud
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() ->
                        Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseString = response.body().string();

                    try {
                        JSONObject responseJson = new JSONObject(responseString);
                        boolean success = responseJson.getBoolean("status");
                        String message = responseJson.getString("message");

                        runOnUiThread(() -> {
                            if (success) {
                                // Si las credenciales son correctas, abrir la siguiente actividad
                                Intent intent = new Intent(MainActivity.this, Menu.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(MainActivity.this, "Error en la conexión al servidor", Toast.LENGTH_SHORT).show()
                    );
                }
            }
        });
    }
}