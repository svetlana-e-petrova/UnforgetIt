package com.example.svetlana.unforgetit.adapter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.svetlana.unforgetit.R;
import com.example.svetlana.unforgetit.fragment.TaskFragment;
import com.example.svetlana.unforgetit.model.Item;
import com.example.svetlana.unforgetit.model.ModelTask;
import com.example.svetlana.unforgetit.utils.DateUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoneTaskAdapter extends TaskAdapter {

    public DoneTaskAdapter(TaskFragment taskFragment) {
        super(taskFragment);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_task, parent, false);
        TextView title = (TextView) v.findViewById(R.id.tvTaskTitle);
        TextView date = (TextView) v.findViewById(R.id.tvTaskDate);
        CircleImageView priority = (CircleImageView) v.findViewById(R.id.cvTaskPriority);

        return new TaskViewHolder(v, title, date, priority);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final Item item = items.get(position);

        if (item.isTask()) {
            holder.itemView.setEnabled(true);
            final ModelTask task = (ModelTask) item;
            final TaskViewHolder taskViewHolder = (TaskViewHolder) holder;

            final View itemView = taskViewHolder.itemView;
            Resources resources = itemView.getResources();

            taskViewHolder.title.setText(task.getTitle());
            if (task.getDate() != 0) {
                taskViewHolder.date.setText(DateUtils.getFullDate(task.getDate()));
            } else {
                taskViewHolder.date.setText(null);
            }

            itemView.setVisibility(View.VISIBLE);
            taskViewHolder.priority.setEnabled(true);

            taskViewHolder.title.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.primary_text_disabled_material_light));
            taskViewHolder.date.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.secondary_text_disabled_material_light));
            taskViewHolder.priority.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(), task.getPriorityColor()));
            taskViewHolder.priority.setImageResource(R.drawable.ic_checkbox_blank_circle_white_48dp);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Handler handler = new Handler();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getTaskFragment().removeTaskDialog(taskViewHolder.getLayoutPosition());
                        }
                    }, 1000);

                    return true;
                }
            });

            taskViewHolder.priority.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    taskViewHolder.priority.setEnabled(false);
                    task.setStatus(ModelTask.STATUS_CURRENT);
                    getTaskFragment().activity.dbHelper.getUpdateManager().updateStatus(task.getTimeStamp(), ModelTask.STATUS_CURRENT);

                    taskViewHolder.title.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.primary_text_default_material_light));
                    taskViewHolder.date.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.secondary_text_default_material_light));
                    taskViewHolder.priority.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(), task.getPriorityColor()));

                    ObjectAnimator flipIn = ObjectAnimator.ofFloat(taskViewHolder.priority, "rotationY", 180f, 0f);

                    flipIn.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (task.getStatus() != ModelTask.STATUS_DONE) {
                                taskViewHolder.priority.setImageResource(R.drawable.ic_check_circle_white_48dp);

                                ObjectAnimator translationX = ObjectAnimator.ofFloat(itemView,
                                        "translationX", 0f, -itemView.getWidth());

                                ObjectAnimator translationXBack = ObjectAnimator.ofFloat(itemView,
                                        "translationX", -itemView.getWidth(), 0f);

                                translationX.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        itemView.setVisibility(View.GONE);
                                        getTaskFragment().moveTask(task);
                                        removeItem(taskViewHolder.getLayoutPosition());
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                });

                                AnimatorSet translationSet = new AnimatorSet();
                                translationSet.play(translationX).before(translationXBack);
                                translationSet.start();
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    flipIn.start();

                }
            });

        }
    }
}
