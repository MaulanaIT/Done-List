package com.virtusrox.donelist.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.virtusrox.donelist.HttpsTrustManager;
import com.virtusrox.donelist.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    static String URL = "https://simendut.com/mitramas/api/",
            URL_LOGIN = URL + "readUser.php";

    StringRequest stringRequest;

    RequestQueue requestQueue;

    Button btnMasuk;

    EditText inputEmail,
            inputPassword;

    SharedPreferences savedData;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        savedData = getSharedPreferences("Saved Data", Context.MODE_PRIVATE);
        editor = savedData.edit();

        btnMasuk = findViewById(R.id.btn_masuk);

        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);

        btnMasuk.setOnClickListener(v -> login());
    }

    public void login() {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        HttpsTrustManager.allowAllSSL();

        stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, response -> {
            if (response.length() > 0) {
                try {
                    JSONArray array = new JSONArray(response);
                    JSONObject user = array.getJSONObject(0);

                    editor.putInt("isLogin", 1);
                    editor.putInt("id", user.getInt("id"));
                    editor.putString("username", user.getString("username"));
                    editor.commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            if (response.contains("Failed")) {
                Toast.makeText(LoginActivity.this, "Login Gagal", Toast.LENGTH_LONG).show();
            }
        }, error -> Toast.makeText(LoginActivity.this, "Proses Login Error : " + error.toString(), Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        requestQueue = Volley.newRequestQueue(LoginActivity.this);

        requestQueue.add(stringRequest);
    }
}