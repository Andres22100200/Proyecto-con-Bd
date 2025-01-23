package com.example.interpoyectoconbd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.*;
import org.json.JSONObject;
import org.json.JSONException;
import java.io.IOException;

public class RegistroUsuarios extends AppCompatActivity {

    private EditText nombreusuario, contraseñaUsuario;
    private Button guardar, regresar;
    private final String URL_API = "http://192.168.137.218/Proyecto.php"; // Cambia por la URL de tu servidor

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        nombreusuario = findViewById(R.id.nombreusuario);
        contraseñaUsuario = findViewById(R.id.contraseñaUsuario);
        guardar = findViewById(R.id.guardar);
        regresar = findViewById(R.id.regresar);

        regresar.setOnClickListener(v -> {
            Intent intent = new Intent(RegistroUsuarios.this, MainActivity.class);
            startActivity(intent);
        });

        guardar.setOnClickListener(v -> {
            String nombre = nombreusuario.getText().toString().trim();
            String contraseña = contraseñaUsuario.getText().toString().trim();

            if (nombre.isEmpty() || contraseña.isEmpty()) {
                Toast.makeText(RegistroUsuarios.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            } else {
                registrarUsuario(nombre, contraseña);
            }
        });
    }

    private void registrarUsuario(String nombre, String contraseña) {
        // Crear cliente HTTP
        OkHttpClient client = new OkHttpClient();

        // Crear cuerpo JSON
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("usuario", nombre);
            jsonObject.put("contraseña", contraseña);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Crear RequestBody
        RequestBody body = RequestBody.create(
                jsonObject.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        // Crear solicitud HTTP
        Request request = new Request.Builder()
                .url(URL_API) // Cambia esta URL según tu servidor
                .post(body)
                .build();

        // Ejecutar la solicitud
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() ->
                        Toast.makeText(RegistroUsuarios.this, "Error de conexión: " + e.getMessage(), Toast.LENGTH_SHORT).show()
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
                                Toast.makeText(RegistroUsuarios.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                                nombreusuario.setText("");
                                contraseñaUsuario.setText("");
                            } else {
                                Toast.makeText(RegistroUsuarios.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(RegistroUsuarios.this, "Error en el servidor: " + response.message(), Toast.LENGTH_SHORT).show()
                    );
                }
            }
        });
    }
}