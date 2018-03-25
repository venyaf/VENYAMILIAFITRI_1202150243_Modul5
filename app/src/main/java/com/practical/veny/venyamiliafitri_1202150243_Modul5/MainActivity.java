package com.practical.veny.venyamiliafitri_1202150243_Modul5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Deklarasi nama variable
    private RecyclerView recyclerView;
    private SQLiteDatabase tampil;
    private DataHelper dataHelper;
    private List<Todo> todoList;
    TodoAdapter userAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    //deklarasi tag dari kolom yang ada pada tabel di database
    public static final String TAG_ID = "id";
    public static final String TAG_TODO = "todo";
    public static final String TAG_DESCRIPTION = "description";
    public static final String TAG_PRIORITY = "priority";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Mengambil kembali SharedPreference
        SharedPreferences pref = this.getApplicationContext().getSharedPreferences("pref", 0);
        //get data int warna untuk backround
        int warna = pref.getInt("background", R.color.default_background);

        //inisialisasi DataHelper untuk getApplicationContext
        dataHelper  =  new DataHelper(getApplicationContext());
        //menampilkan DataHelper untuk dibaca dari database
        tampil      = dataHelper.getReadableDatabase();

        //deklarasi id recyclerview dari xml
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //Arraylist<> dari todoList
        todoList = new ArrayList<>();
        //userAdapter untuk memanggil konstruktor pada TodoAdapter
        userAdapter = new TodoAdapter(this,todoList,warna);
        //LayoutManager pada recyclerview yang dibuat pada MainActivity
        mLayoutManager = new LinearLayoutManager(this);

        //setLayoutManager pada recyclerview yang telah dideklarasikan di mLayoutManager
        recyclerView.setLayoutManager(mLayoutManager);

        //setHasFixedSize dari recyclerview yang dibuat
        recyclerView.setHasFixedSize(true);
        //setAdapter dari TodoAdapter
        recyclerView.setAdapter(userAdapter);

        //mendefinisikan diawal method dataUser() dan setSwipeForRecyclerView()
        dataUser();
        setSwipeForRecyclerView();

        //Mendeklarasikan FloatingActionButton id dari xml
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        //Intent aktivitas dari MainActivity ke AddTodoActivity setelah diklik fab
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(MainActivity.this,AddTodoActivity.class);
                startActivity(a);
            }
        });
    }

    //method setSwipeforRecyclerView
    private void setSwipeForRecyclerView() {
        //ItemTouchHelper yang memberikan SimpleCallback ke kanan atau ke kiri
        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //getAdapterPosition
                final int position = viewHolder.getAdapterPosition();
                //Melakukan perintah delete berdasarkan posisi id dari todoList
                dataHelper.delete(Integer.parseInt(todoList.get(position).getId()));
                todoList.clear();
                //method dataUser() dipanggil untuk dijalankan
                dataUser();
                //notifyDataSetChanged() dari konstruktor yang ada di TodoAdapter
                userAdapter.notifyDataSetChanged();
            }
        });
        touchHelper.attachToRecyclerView(recyclerView);
    }

    //method dataUser
    private void dataUser() {
        //ArrayList dataHelper untuk melakukan get semua data
        ArrayList<HashMap<String, String>> row = dataHelper.getAllData();
        //perulangan untuk get tag dari row yang ada
        for (int i = 0; i < row.size(); i++) {
            String id = row.get(i).get(TAG_ID);
            String todo = row.get(i).get(TAG_TODO);
            String description = row.get(i).get(TAG_DESCRIPTION);
            String priority = row.get(i).get(TAG_PRIORITY);

            //memanggil konstruktor pada model
            Todo data = new Todo();

            //setData pada konstruktor
            data.setId(id);
            data.setTodo(todo);
            data.setDescription(description);
            data.setPriority(priority);

            //menambahkan data pada todoList
            todoList.add(data);
        }
        //notifyDataSetChanged() dari konstruktor yang ada di TodoAdapter
        userAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //Intent dari MainActivity ke SettingsActivity
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        //mengembalikan kunci super pada item di method onOptionsItemSelected
        return super.onOptionsItemSelected(item);
    }
}
