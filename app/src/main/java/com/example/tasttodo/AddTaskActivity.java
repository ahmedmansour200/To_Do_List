package com.example.tasttodo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tasttodo.model.Model;
import com.example.tasttodo.sql.DataBaseAdapter;

import java.sql.DatabaseMetaData;

public class AddTaskActivity extends AppCompatActivity {
     private EditText etTitle, etBode;
     private Button addNode;

    boolean isUpdate = false;
    DataBaseAdapter myDb = new DataBaseAdapter(AddTaskActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        etTitle = findViewById(R.id.add_title);
        etBode = findViewById(R.id.add_bode);
        addNode = findViewById(R.id.add_task);

        addNode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                myDb.insertMode(new Model(etTitle.getText().toString(),etBode.getText().toString()));

                // Set the result code to indicate a successful operation
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}