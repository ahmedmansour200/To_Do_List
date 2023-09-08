package com.example.tasttodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tasttodo.adabter.Adapter;
import com.example.tasttodo.model.Model;
import com.example.tasttodo.sql.DataBaseAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int ADD_TASK_REQUEST_CODE = 1;
    private RecyclerView allNodes;
    private TextView addNode;

    private DataBaseAdapter myDB;
    private List<Model> mList;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        allNodes = findViewById(R.id.all_nodes);
        addNode = findViewById(R.id.addNote);

        myDB = new DataBaseAdapter(MainActivity.this);
        mList = new ArrayList<>();
        adapter = new Adapter(myDB , MainActivity.this);

        allNodes.setHasFixedSize(true);
        allNodes.setLayoutManager(new LinearLayoutManager(this));
        allNodes.setAdapter(adapter);

        mList = myDB.getAllMode();
        Collections.reverse(mList);
        adapter.setTasks(mList);

        addNode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddTaskActivity.class);
                startActivityForResult(intent, ADD_TASK_REQUEST_CODE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TASK_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // If the result code is RESULT_OK, it means a new note was added
                // Refresh the RecyclerView with updated data
                mList.clear() ;
                mList.addAll(myDB.getAllMode());
                Collections.reverse(mList);
                adapter.notifyDataSetChanged();

            }
        }
    }

}