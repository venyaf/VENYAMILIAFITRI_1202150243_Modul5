package com.practical.veny.venyamiliafitri_1202150243_Modul5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTodoActivity extends AppCompatActivity {

    //Inisialisasi variable
    private EditText a, b,c;
    private Button d;

    //Inisialisasi DataHelper sqlite
    DataHelper sqlite = new DataHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        //Inisialisasi id dari xml di layout activity_add_todo
        a = (EditText) findViewById(R.id.todo);
        b = (EditText) findViewById(R.id.description);
        c = (EditText) findViewById(R.id.priority);
        d = (Button) findViewById(R.id.tambah);

        //Ketika button diklik maka akan menjalankan method save
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }


    private void save() {
        //Kondisi ketika data tidak terisi maka akan muncul toast
        if (String.valueOf(a.getText()).equals(null) || String.valueOf(b.getText()).equals("") ||
                String.valueOf(c.getText()).equals(null) || String.valueOf(c.getText()).equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Isikan dengan lengkap ...", Toast.LENGTH_SHORT).show();
        //Insert data ke sqlite untuk nanti diteruskan ke MainActivity
        } else {
            sqlite.insert(a.getText().toString().trim(), b.getText().toString().trim(),c.getText().toString().trim());
            blank();
            Intent a = new Intent(AddTodoActivity.this, MainActivity.class);
            startActivity(a);
        }
    }

    //SetText menjadi null
    private void blank() {
        a.requestFocus();
        a.setText(null);
        b.setText(null);
        c.setText(null);
    }
}
