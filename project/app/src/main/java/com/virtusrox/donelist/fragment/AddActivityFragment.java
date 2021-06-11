package com.virtusrox.donelist.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.virtusrox.donelist.R;
import com.virtusrox.donelist.activity.LandingActivity;
import com.virtusrox.donelist.activity.RegisterActivity;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class AddActivityFragment extends Fragment {

    static String URL = "https://simendut.com/mitramas/api/",
            URL_ACTIVITY = URL + "addActivity.php";

    StringRequest stringRequest;

    RequestQueue requestQueue;

    Button btnTambah;

    EditText inputAktivitas;

    SharedPreferences savedData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        savedData = getActivity().getSharedPreferences("Saved Data", Context.MODE_PRIVATE);

        btnTambah = view.findViewById(R.id.btn_tambah);

        inputAktivitas = view.findViewById(R.id.input_aktivitas);

        btnTambah.setOnClickListener(v -> {
            aktivitas();
        });
    }

    public void aktivitas() {
        String aktivitas = inputAktivitas.getText().toString().trim();
        String id = String.valueOf(savedData.getInt("id", 0));

        stringRequest = new StringRequest(Request.Method.POST, URL_ACTIVITY, response -> {
            if (response.contains("Success")) {
                getActivity().getSupportFragmentManager().popBackStack();
            } else {
                Toast.makeText(getActivity(), "Tambah Aktivitas Gagal", Toast.LENGTH_LONG).show();
            }
        }, error -> Toast.makeText(getActivity(), "Proses Tambah Aktivitas Error : " + error.toString(), Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("aktivitas", aktivitas);
                return params;
            }
        };

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        requestQueue.add(stringRequest);
    }
}