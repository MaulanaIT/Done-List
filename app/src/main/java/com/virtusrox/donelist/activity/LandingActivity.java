package com.virtusrox.donelist.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.virtusrox.donelist.R;

public class LandingActivity extends AppCompatActivity {

    Button btnDaftar,
            btnMasuk;

    SharedPreferences savedData;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        savedData = getSharedPreferences("Saved Data", Context.MODE_PRIVATE);
        editor = savedData.edit();

        if (savedData.getInt("isLogin", 0) > 0) {
            Intent intent = new Intent(LandingActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            editor.clear();
            editor.apply();
        }

        btnDaftar = findViewById(R.id.btn_daftar);
        btnMasuk = findViewById(R.id.btn_masuk);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}