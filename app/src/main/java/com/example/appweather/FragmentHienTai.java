package com.example.appweather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FragmentHienTai extends Fragment {

    ArrayList arrayList;
    View view;
    //EditText edtTimKiem;
    Button btnTimKiem;
    TextView txtName, txtCounty, txtNhietDo, txtNgay, txtTrangThai, txtDoAm, txtMay, txtGio;
    ImageView imgIcon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        arrayList = new ArrayList();
        arrayList.add("Ha Noi");arrayList.add("Hai Phong");arrayList.add("Da Nang");arrayList.add("Can Tho");arrayList.add("Bac Ninh");
        arrayList.add("Hai Duong");arrayList.add("Lao Cai");arrayList.add("Yen Bai");arrayList.add("Bac Giang");
        arrayList.add("Hung Yen");arrayList.add("Ha Nam");arrayList.add("Nam Dinh");arrayList.add("Thanh Hoa");
        arrayList.add("Ninh Binh");arrayList.add("Vinh Long");arrayList.add("Tien Giang");arrayList.add("Hau Giang");
        arrayList.add("Quy Nhon");arrayList.add("Tuyen Quang");arrayList.add("Gia Lai");arrayList.add("Dac Nong");

        view = inflater.inflate(R.layout.fragment_hientai, container, false);

        //edtTimKiem = (EditText) view.findViewById(R.id.edtSearch);
        btnTimKiem = (Button) view.findViewById(R.id.btnSearch);
        txtName = (TextView) view.findViewById(R.id.txtName);
        txtCounty = (TextView) view.findViewById(R.id.txtCountry);
        txtNhietDo = (TextView) view.findViewById(R.id.txtNhietDo);
        txtTrangThai = (TextView) view.findViewById(R.id.txtTrangThai);
        txtDoAm = (TextView) view.findViewById(R.id.txtDoAm);
        txtMay = (TextView) view.findViewById(R.id.txtMay);
        txtGio = (TextView) view.findViewById(R.id.txtGio);
        txtNgay = (TextView) view.findViewById(R.id.txtNgay);
        imgIcon = (ImageView) view.findViewById(R.id.imageIcon);
        final AutoCompleteTextView editText = view.findViewById(R.id.thanhpho);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, arrayList);
        editText.setAdapter(adapter);
        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = editText.getText().toString();
                GetCurrentWeatherData(city);
            }

        });
        return view;

    }

    public void GetCurrentWeatherData(String city) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&mode=json&units=metric&appid=6ede76477a8107a26616a37dc4d9ccdc";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");
                            txtName.setText("Tên thành phố :" + name);

                            long l = Long.valueOf(day);
                            Date date = new Date(l * 1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
                            String Day = simpleDateFormat.format(date);

                            txtNgay.setText(Day);
                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            String trangthai = jsonObjectWeather.getString("main");
                            String icon = jsonObjectWeather.getString("icon");

                            Picasso.with(getContext()).load("http://openweathermap.org/img/w/" + icon + ".png").into(imgIcon);
                            txtTrangThai.setText(trangthai);

                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String nhietdo = jsonObjectMain.getString("temp");
                            String doam = jsonObjectMain.getString("humidity");

                            Double a = Double.valueOf(nhietdo);
                            String NhietDo = String.valueOf(a.intValue());

                            txtNhietDo.setText(NhietDo + "°C");
                            txtDoAm.setText(doam + "%");

                            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                            String gio = jsonObjectWind.getString("speed");
                            txtGio.setText(gio + "m/s");

                            JSONObject jsonObjecClouds = jsonObject.getJSONObject("clouds");
                            String may = jsonObjecClouds.getString("all");
                            txtMay.setText(may + "%");

                            JSONObject jsonObjecSys = jsonObject.getJSONObject("sys");
                            String country = jsonObjecSys.getString("country");
                            txtCounty.setText("Tên quốc gia :" + country);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }

}
