package com.virtusrox.donelist.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.virtusrox.donelist.R;
import com.virtusrox.donelist.activity.LandingActivity;
import com.virtusrox.donelist.activity.MainActivity;
import com.virtusrox.donelist.adapter.ActivityAdapter;
import com.virtusrox.donelist.model.ActivityModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    ImageButton btnTambah,
            btnKeluar;

    TextView textJudul;

    SharedPreferences savedData;
    SharedPreferences.Editor editor;

    List<ActivityModel> listActivity;

    RecyclerView
            recyclerView;

    RecyclerView.LayoutManager
            layoutManager;

    RecyclerView.Adapter
            adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull @org.jetbrains.annotations.NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        savedData = getActivity().getSharedPreferences("Saved Data", Context.MODE_PRIVATE);
        editor = savedData.edit();

        btnKeluar = view.findViewById(R.id.btn_keluar);
        btnTambah = view.findViewById(R.id.btn_tambah);

        textJudul = view.findViewById(R.id.text_judul);

        recyclerView = view.findViewById(R.id.recycler_view);

        textJudul.setText("Hi " + savedData.getString("username", "User"));

        listActivity = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());

        adapter = new ActivityAdapter(listActivity, getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        ((MainActivity) getActivity()).getAktivitas(adapter, recyclerView, listActivity);

        btnTambah.setOnClickListener(v -> {
            getParentFragmentManager().popBackStackImmediate("Fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getParentFragmentManager().beginTransaction().replace(R.id.container, new AddActivityFragment()).addToBackStack("Fragment").commit();
        });

        btnKeluar.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LandingActivity.class);
            startActivity(intent);

            editor.clear();
            editor.apply();

            getActivity().finish();
        });
    }
}