package com.example.appweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class DanhGia extends AppCompatActivity {
    RadioButton radioButton;
    RadioGroup radioGroup;
    Button danhgia;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhgia);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        radioGroup = findViewById(R.id.radioGroup);
        danhgia = findViewById(R.id.danhgia);
        DanhGia();
        nav = (NavigationView) findViewById(R.id.navmenu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_home:

                        Intent it = new Intent(DanhGia.this, MainActivity.class);
                        startActivity(it);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_thongtin:
                        Intent tt = new Intent(DanhGia.this, ThongTin.class);
                        startActivity(tt);

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_danhgia:
                        Intent ii = new Intent(DanhGia.this, DanhGia.class);
                        startActivity(ii);

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.menu_logout:
                        logout(DanhGia.this);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }

                return true;
            }
        });

    }

    public void DanhGia() {
        danhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(id);
                radioButton = (RadioButton) findViewById(R.id.motsao);
                radioButton = (RadioButton) findViewById(R.id.haisao);
                radioButton = (RadioButton) findViewById(R.id.basao);
                radioButton = (RadioButton) findViewById(R.id.bonsao);
                radioButton = (RadioButton) findViewById(R.id.namsao);
                Intent it = new Intent(DanhGia.this, MainActivity.class);
                startActivity(it);
                Toast.makeText(DanhGia.this, "Cám ơn bạn đã đánh giá chất lượng ứng dụng",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static void logout(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Bạn có muốn thoát khỏi ứng dụng không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}