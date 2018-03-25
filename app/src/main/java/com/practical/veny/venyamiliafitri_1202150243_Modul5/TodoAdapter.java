package com.practical.veny.venyamiliafitri_1202150243_Modul5;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Veny on 3/24/2018.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    //mendekrasikan nama variable
    private Context context;
    int colorId;
    private List<Todo> todoList;

    //konstuktor yang berisi parameter Context, listTodo, dan id color
    public TodoAdapter (Context con, List<Todo> listTodo, int colorId){
        this.context = con;
        this.todoList = listTodo;
        this.colorId = colorId;
    }

    @Override
    public TodoAdapter.TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TodoAdapter.TodoViewHolder holder, int position) {
        // Mendapatkan elemen dari dataset Anda di posisi ini
        // Mengganti isi tampilan dengan elemen itu
        //get position dari todoList pada model
        Todo todo = todoList.get(position);
        //holder untuk set data pada getter di model
        holder.todo.setText(todo.getTodo());
        holder.description.setText(todo.getDescription());
        holder.priority.setText(todo.getPriority());
        //holder untuk setCardBackgroundColor dengan colorId
        holder.cd.setCardBackgroundColor(context.getResources().getColor(this.colorId));

        //Memberikan tindakan intent ketika salah satu item view diklik
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext().getApplicationContext(), MainActivity.class);
                view.getContext().startActivity(i);
            }
        });

    }

    //getItemCount dengan mengembalikan size dari todoList
    @Override
    public int getItemCount() {
        return todoList.size();
    }

    //class TodoViewholder
    public static class TodoViewHolder extends RecyclerView.ViewHolder {
        //deklarasi nama variable pada itemView
        public TextView todo, description,priority;
        public CardView cd;

        public TodoViewHolder(View itemView) {
            super(itemView);
            todo  = itemView.findViewById(R.id.todo);
            description   = itemView.findViewById(R.id.description);
            priority   = itemView.findViewById(R.id.priority);
            cd = itemView.findViewById(R.id.cardview);
        }
    }
}
