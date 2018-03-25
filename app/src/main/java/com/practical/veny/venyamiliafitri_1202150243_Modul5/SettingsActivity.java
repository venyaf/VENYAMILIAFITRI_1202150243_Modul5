package com.practical.veny.venyamiliafitri_1202150243_Modul5;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {
    //Deklarasi nama variable
    int color;
    TextView warna;
    AlertDialog.Builder builder;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //deklarasi alert dialog untuk dibuild
        builder = new AlertDialog.Builder(this);

        //Mendapatkan SharedPreference dan menentukan editor untuk SharedPreference
        SharedPreferences pref = getApplicationContext().getSharedPreferences("pref", 0);
        edit = pref.edit();
        color = pref.getInt("background", R.color.default_background);
        warna = findViewById(R.id.warna);
        //set tulisan text dari getter color
        warna.setText(getWarna(color));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
        }
        return true;
    }

    //method untuk kembali ke aktivitas sebelumnya
    @Override
    public void onBackPressed() {
        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
        finish();
    }

    //Method untuk menampilkan dialog pilihan warna untuk memilih warna
    public void pilihanwarna(View view) {
        //set judul dari alert dialog yang dibuat
        builder.setTitle("Shape Color");
        //menampilkan view dari file xml yang ditampilkan di alert dialog
        View v = getLayoutInflater().inflate(R.layout.pilihan_warna, null);
        //setView yang telah dideklarasikan ke builder alert dialog
        builder.setView(v);

        //Menentukan radiobutton yang dipilih
        final RadioGroup rg = v.findViewById(R.id.rg);
        //check pada radio button yang sudah dikelompokkan ke radio group
        rg.check(getIntColor(color));

        //setPositiveButton ketika telah ditekan ok maka terdapat switch case yang akan dijalankan
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int check = rg.getCheckedRadioButtonId();
                switch (check) {
                    case R.id.red:
                        color = R.color.red_background;
                        break;
                    case R.id.blue:
                        color = R.color.blue_background;
                        break;
                    case R.id.green:
                        color = R.color.green_background;
                        break;
                    case R.id.dasar:
                        color = R.color.default_background;
                        break;
                }

                //Mengatur SharedPreference
                warna.setText(getWarna(color));
                //put warna background
                edit.putInt("background", color);
                //commit
                edit.commit();
            }
        });

        //Method ketika menekan Cancel
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }
        );

        //Menampilkan dialog dari pilihan warna
        builder.create().show();
    }

    //Method untuk mendapatkan String warna
    public String getWarna(int i) {
        if (i == R.color.red_background) {
            return "Red";
        } else if (i == R.color.blue_background) {
            return "Blue";
        } else if (i == R.color.green_background) {
            return "Green";
        } else {
            return "Default";
        }
    }

    //Method untuk mendapatkan id warna
    public int getIntColor(int i) {
        if (i == R.color.red_background) {
            return R.id.red;
        } else if (i == R.color.blue_background) {
            return R.id.blue;
        } else if (i == R.color.green_background) {
            return R.id.green;
        } else {
            return R.id.dasar;
        }
    }
}
