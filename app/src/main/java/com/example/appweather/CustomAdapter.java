package com.example.appweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<ThoiTiet> arrayList;


    public CustomAdapter(Context context, ArrayList<ThoiTiet> arrayList) {
        this.arrayList = arrayList;
        this.context = context;

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.dong_listview, null);
            ThoiTiet thoiTiet = arrayList.get(i);

            TextView txtNgay = view.findViewById(R.id.txtNgay);
            TextView txtTrangThai = view.findViewById(R.id.txtTrangThai);
            TextView txtMaxTemp = view.findViewById(R.id.txtMaxTemp);
            TextView txtMinTemp = view.findViewById(R.id.txtMinTemp);

            ImageView imgtrangthai = (ImageView) view.findViewById(R.id.imgtrangthai);

            txtNgay.setText(thoiTiet.Ngay);
            txtTrangThai.setText(thoiTiet.TrangThai);
            txtMaxTemp.setText(thoiTiet.NhietDoMax + "°C");
            txtMinTemp.setText(thoiTiet.NhietDoMin + "°C");

            Picasso.with(context).load("http://openweathermap.org/img/w/" + thoiTiet.Icon + ".png").into(imgtrangthai);
            return view;
        }
}


