package com.virtusrox.donelist.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.virtusrox.donelist.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    static String URL = "https://simendut.com/mitramas/api/",
            URL_REGISTER = URL + "addUser.php";

    StringRequest stringRequest;

    RequestQueue requestQueue;

    Button btnDaftar;

    EditText inputUsername,
            inputEmail,
            inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnDaftar = findViewById(R.id.btn_daftar);

        inputUsername = findViewById(R.id.input_username);
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);

        btnDaftar.setOnClickListener(v -> register());
    }

    public void register() {
        String username = inputUsername.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER, response -> {
            if (response.contains("Success")) {
                Intent intent = new Intent(RegisterActivity.this, LandingActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, "Registrasi Gagal", Toast.LENGTH_LONG).show();
            }
        }, error -> Toast.makeText(RegisterActivity.this, "Proses Registrasi Error : " + error.toString(), Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        requestQueue = Volley.newRequestQueue(RegisterActivity.this);

        requestQueue.add(stringRequest);
    }
}