
package com.example.appweather;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class fragment7Ngay extends Fragment {

    View view;
    String tenthanhpho = "";
    TextView txtName;
    ListView lv;
    CustomAdapter customAdapter;
    ArrayList<ThoiTiet> thoiTiets;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_7ngay, container, false);
        txtName = (TextView) view.findViewById(R.id.txtName);
        lv = (ListView) view.findViewById(R.id.lv);

        Intent it = getActivity().getIntent();
        thoiTiets = new ArrayList<ThoiTiet>();
        customAdapter = new CustomAdapter(getContext(), thoiTiets);
        lv.setAdapter(customAdapter);
        Get7DaysData(tenthanhpho);
        it.getStringExtra("city");
        String city = it.getStringExtra("city");
        Log.d("kết quả", "Dữ liệu truyền qua :" + city);
        lv.setAdapter(customAdapter);

        return view;

    }

    private void Get7DaysData(String city) {
        //String url = "http://api.openweathermap.org/data/2.5/forecast?q="+city+"&appid=6ede76477a8107a26616a37dc4d9ccdc";
        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q="+city+"&units=metric&cnt=7&appid=92bd5d185e6523feb75f8110cb5e9641";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                            String name = jsonObjectCity.getString("name");

                            txtName.setText(name);

                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                            for (int i = 0; i < jsonArrayList.length(); i++) {
                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                                String ngay = jsonObjectList.getString("dt");

                                long l = Long.valueOf(ngay);
                                Date date = new Date(l * 1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd ");
                                String Ngay = simpleDateFormat.format(date);

                                JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("temp");
                                String max = jsonObjectTemp.getString("max");
                                String min = jsonObjectTemp.getString("min");


                                Double a = Double.valueOf(max);
                                Double b = Double.valueOf(min);
                                String NhietDoMax = String.valueOf(a.intValue());
                                String NhietDoMin = String.valueOf(b.intValue());

                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                String TrangThai = jsonObjectWeather.getString("descoription");
                                String Icon = jsonObjectWeather.getString("icon");

                                thoiTiets.add(new ThoiTiet(Ngay, TrangThai, Icon, NhietDoMax, NhietDoMin));
                            }
                            customAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
}
