package com.example.interpoyectoconbd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import okhttp3.*;
import org.json.JSONObject;
import org.json.JSONException;
import java.io.IOException;

public class Editarpassword extends AppCompatActivity {

    private EditText nombreusuario, contraseniaactual, nuevacontrasenia, confirmarcontrasenia;
    private Button guardar, regresar;
    private CheckBox checkBox;
    private final String URL_API = "http://192.168.137.218/Proyecto.php"; // Cambia esta URL

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editarpassword);

        // Inicialización de vistas
        nombreusuario = findViewById(R.id.usuario);
        contraseniaactual = findViewById(R.id.oldpassword);
        nuevacontrasenia = findViewById(R.id.newpassword);
        confirmarcontrasenia = findViewById(R.id.confiarmarcontrasenia);

        guardar = findViewById(R.id.guardar);
        regresar = findViewById(R.id.regresar);
        checkBox = findViewById(R.id.checkBox);

        // Listener para el botón "Regresar"
        regresar.setOnClickListener(v -> {
            Intent intent = new Intent(Editarpassword.this, Menu.class);
            startActivity(intent);
        });

        // Listener para el botón "Guardar"
        guardar.setOnClickListener(v -> {
            String nombre = nombreusuario.getText().toString().trim();
            String actualcontrasenia = contraseniaactual.getText().toString().trim();
            String nuevaContrasenia = nuevacontrasenia.getText().toString().trim();
            String confirmarContrasenia = confirmarcontrasenia.getText().toString().trim();

            if (nombre.isEmpty() || actualcontrasenia.isEmpty() || nuevaContrasenia.isEmpty() || confirmarContrasenia.isEmpty()) {
                Toast.makeText(Editarpassword.this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
            } else if (!checkBox.isChecked()) {
                Toast.makeText(Editarpassword.this, "Primero debes confirmar tus datos", Toast.LENGTH_SHORT).show();
            } else if (!nuevaContrasenia.equals(confirmarContrasenia)) {
                Toast.makeText(Editarpassword.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            } else {
                cambiarContrasenia(nombre, actualcontrasenia, nuevaContrasenia);
            }
        });
    }

    private void cambiarContrasenia(String usuario, String actualContrasenia, String nuevaContrasenia) {
        // Crear cliente HTTP
        OkHttpClient client = new OkHttpClient();

        // Crear cuerpo JSON
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("usuario", usuario);
            jsonObject.put("actualContrasenia", actualContrasenia);
            jsonObject.put("nuevaContrasenia", nuevaContrasenia);
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
                .url(URL_API) // Cambia esta URL según tu servidor
                .post(body)
                .build();

        // Ejecutar la solicitud
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() ->
                        Toast.makeText(Editarpassword.this, "Error de conexión: " + e.getMessage(), Toast.LENGTH_SHORT).show()
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
                                Toast.makeText(Editarpassword.this, "Contraseña actualizada correctamente", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Editarpassword.this, Menu.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Editarpassword.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(Editarpassword.this, "Error en el servidor: " + response.message(), Toast.LENGTH_SHORT).show()
                    );
                }
            }
        });
    }
}
