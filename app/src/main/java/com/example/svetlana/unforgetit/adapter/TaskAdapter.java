package com.example.svetlana.unforgetit.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.svetlana.unforgetit.fragment.TaskFragment;
import com.example.svetlana.unforgetit.model.Item;
import com.example.svetlana.unforgetit.model.ModelSeparator;
import com.example.svetlana.unforgetit.model.ModelTask;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public abstract class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Item> items;
    TaskFragment taskFragment;

    public boolean containsSeparatorToday;
    public boolean containsSeparatorTomorrow;
    public boolean containsSeparatorFuture;
    public boolean containsSeparatorOverdue;

    @Override
    public int getItemCount() {
        return items.size();
    }

    public TaskFragment getTaskFragment() {
        return taskFragment;
    }

    public TaskAdapter(TaskFragment taskFragment) {
        this.taskFragment = taskFragment;
        items = new ArrayList<>();
    }

    public Item getItem(int position) {
        return items.get(position);
    }

    public void addItem(Item item) {
        items.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addItem(int location, Item item) {
        items.add(location, item);
        notifyItemChanged(location);
    }

    public void updateTask(ModelTask editedTask) {
        for (int i = 0; i < getItemCount(); i++) {
            if (getItem(i).isTask()) {
                ModelTask task = (ModelTask) items.get(i);
                if (editedTask.getTimeStamp() == task.getTimeStamp()) {
                    removeItem(i);
                    taskFragment.addTask(editedTask, false);
                }
            }
        }
    }

    public void removeItem(int location) {
        if (location >= 0 && location <= getItemCount() - 1) {
            items.remove(location);
            notifyItemRemoved(location);

            if (location >= 1 && location <= getItemCount()
                    && !getItem(location).isTask() && !getItem(location - 1).isTask()) {
                ModelSeparator separator = (ModelSeparator) getItem(location - 1);
                offSeparator(separator.getType());
                items.remove(location - 1);
                notifyItemRemoved(location - 1);
            } else if (getItemCount() >= 1 && !getItem(getItemCount() - 1).isTask()) {
                int locationTemp = getItemCount() - 1;

                ModelSeparator separator = (ModelSeparator) getItem(locationTemp);
                offSeparator(separator.getType());
                items.remove(locationTemp);
                notifyItemRemoved(locationTemp);
            }
        }
    }

    private void offSeparator(int type) {
        switch (type) {
            case (ModelSeparator.TYPE_TODAY):
                containsSeparatorToday = true;
                break;
            case (ModelSeparator.TYPE_TOMORROW):
                containsSeparatorTomorrow = true;
                break;
            case (ModelSeparator.TYPE_FUTURE):
                containsSeparatorFuture = true;
                break;
            case (ModelSeparator.TYPE_OVERDUE):
                containsSeparatorOverdue = true;
                break;
        }
    }

    public void removeAllItems() {
        if (items.size() != 0) {
            items = new ArrayList<>();
            notifyDataSetChanged();
            containsSeparatorToday = false;
            containsSeparatorTomorrow = false;
            containsSeparatorFuture = false;
            containsSeparatorOverdue = false;
        }
    }

    protected class TaskViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected TextView date;
        protected CircleImageView priority;

        public TaskViewHolder(View itemView, TextView title, TextView date, CircleImageView priority) {
            super(itemView);
            this.title = title;
            this.date = date;
            this.priority = priority;
        }
    }

    protected class SeparatorViewHolder extends RecyclerView.ViewHolder {
        protected TextView separatorType;

        public SeparatorViewHolder(View itemView, TextView separatorType) {
            super(itemView);
            this.separatorType = separatorType;
        }
    }
}
