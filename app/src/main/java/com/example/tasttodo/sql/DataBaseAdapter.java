package com.example.tasttodo.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tasttodo.model.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataBaseAdapter {
    private Context context;
    private SQLiteDatabase db;
    static DataBaseHelper dbHelper;
    private boolean englishFlag = true;
    public DataBaseAdapter(Context _context){
        dbHelper = new DataBaseHelper(_context);
        context = _context;
    }
    public long insertMode(Model model) {
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
//        contentValues.put(DataBaseHelper.TASK_ID, model.getTaskId());
            contentValues.put(DataBaseHelper.TASK_TITLE, model.getTaskTitle());
            contentValues.put(DataBaseHelper.TASK_BODE, model.getTaskBode());
            long id = db.insert(DataBaseHelper.NOTE_TABLE_NAME, null, contentValues);
            return id;
        } catch (Exception e) {
            Toast.makeText(context, "not insert", Toast.LENGTH_SHORT).show();
            return 1 ;
        } finally {
            dbHelper.close();
        }

    }
    public List<Model> getAllMode() {
        List<Model> models = new ArrayList<>();
        Cursor c = null;

        try {
            db = dbHelper.getReadableDatabase();
            String[] columns = {
                    DataBaseHelper.TASK_ID,
                    DataBaseHelper.TASK_TITLE,
                    DataBaseHelper.TASK_BODE
            };

            c = db.query(
                    DataBaseHelper.NOTE_TABLE_NAME,
                    columns,
                    null,
                    null,
                    null,
                    null,
                    DataBaseHelper.TASK_TITLE + " COLLATE nocase"
            );

            while (c.moveToNext()) {
                Model b = new Model();
                b.setTaskId(c.getInt(0));
                b.setTaskTitle(c.getString(1));
                b.setTaskBode(c.getString(2));
                models.add(b);
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return models;
    }

    public void deleteTask(int id ){
        try {
            db = dbHelper.getWritableDatabase();
            db.delete(DataBaseHelper.NOTE_TABLE_NAME, DataBaseHelper.TASK_ID + "=?", new String[]{String.valueOf(id)});
        }catch (Exception e){
            Toast.makeText(context, "not delete", Toast.LENGTH_SHORT).show();
        } finally {
            dbHelper.close();
        }
        }
    public void updateTask(int id , Model task){
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DataBaseHelper.TASK_TITLE, task.getTaskTitle());
            values.put(DataBaseHelper.TASK_BODE, task.getTaskBode());
            db.update(DataBaseHelper.NOTE_TABLE_NAME, values, "ID=?", new String[]{String.valueOf(id)});
        }catch (Exception e){
            Toast.makeText(context, "Erre", Toast.LENGTH_SHORT).show();
        }
        }


    public class DataBaseHelper extends SQLiteOpenHelper {
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "to_do_list2.db";

        private static final String NOTE_TABLE_NAME = "NOTE";
        private static final String TASK_ID = "task_id";
        private static final String TASK_TITLE = "task_title";
        private static final String TASK_BODE = "task_bode";
        private static final String CREATE_NODE_TABLE = "CREATE TABLE " + NOTE_TABLE_NAME + " (" + TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                TASK_TITLE +" TEXT, " + TASK_BODE + " TEXT );";

        public DataBaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            db.execSQL(CREATE_NODE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub

        }
    }
}
