package com.virtusrox.donelist.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.virtusrox.donelist.HttpsTrustManager;
import com.virtusrox.donelist.R;
import com.virtusrox.donelist.fragment.HomeFragment;
import com.virtusrox.donelist.model.ActivityModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    static String URL = "https://simendut.com/mitramas/api/",
            URL_READ_ACTIVITY = URL + "readActivity.php";

    StringRequest stringRequest;

    RequestQueue requestQueue;

    SharedPreferences savedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        savedData = getSharedPreferences("Saved Data", MODE_PRIVATE);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
    }

    public void getAktivitas(final RecyclerView.Adapter adapter,
                             final RecyclerView recyclerView, final List<ActivityModel> listActivity) {
        String id = String.valueOf(savedData.getInt("id", 0));

        HttpsTrustManager.allowAllSSL();

        stringRequest = new StringRequest(Request.Method.POST, URL_READ_ACTIVITY, response -> {
            if (response.length() > 0) {
                listActivity.clear();
                adapter.notifyItemRangeRemoved(0, listActivity.size());

                try {
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject activity = array.getJSONObject(i);

                        ActivityModel currentItem = new ActivityModel();

                        currentItem.setActivity(activity.getString("activity"));

                        listActivity.add(currentItem);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                recyclerView.setAdapter(adapter);
            }

            if (response.contains("Failed")) {
                Toast.makeText(MainActivity.this, "Data Kosong", Toast.LENGTH_LONG).show();
            }
        }, error -> Toast.makeText(MainActivity.this, "Data Error : " + error.toString(), Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }
        };

        requestQueue = Volley.newRequestQueue(MainActivity.this);

        requestQueue.add(stringRequest);
    }
}