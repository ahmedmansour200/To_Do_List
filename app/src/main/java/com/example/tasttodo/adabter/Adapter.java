package com.example.tasttodo.adabter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasttodo.AddTaskActivity;
import com.example.tasttodo.MainActivity;
import com.example.tasttodo.R;
import com.example.tasttodo.model.Model;
import com.example.tasttodo.sql.DataBaseAdapter;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private MainActivity activity;
    private DataBaseAdapter myDB;

    private List<Model> modelList;
    public Adapter ( DataBaseAdapter myDB , MainActivity activity)
    {
        this.myDB = myDB;
        this.activity = activity;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model task = modelList.get(position);
        holder.txTitle.setText(task.getTaskTitle());
        holder.txBode.setText(task.getTaskBode());
        holder.imOptions.setOnClickListener(view -> showPopUpMenu(view, position));
    }

    public void showPopUpMenu(View view, int position) {
        final Model task = modelList.get(position);
        PopupMenu popupMenu = new PopupMenu(activity, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menuDelete:
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity, R.style.AppTheme_Dialog);
                    alertDialogBuilder.setTitle(R.string.delete_confirmation).setMessage(R.string.sureToDelete).
                            setPositiveButton(R.string.yes, (dialog, which) -> deleteTaskFromId(task.getTaskId(), position))
                            .setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel()).show();
                    break;

                case R.id.menuUpdate:
                    AddTaskActivity addTaskActivity = new AddTaskActivity();
                    Intent intent = new Intent(activity , addTaskActivity.getClass());
                    activity.startActivity(intent);
                    break;
                           }
            return false;
        });
        popupMenu.show();
    }


    private void deleteTaskFromId(int taskId, int position) {
        myDB.deleteTask(taskId);
        modelList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,modelList.size());
    }
    public void setTasks(List<Model> modelList){
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imOptions ;
        public TextView  txTitle , txBode  ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imOptions = itemView.findViewById(R.id.options);
            txTitle = itemView.findViewById(R.id.title);
            txBode = itemView.findViewById(R.id.bode);
           }
    }
}
