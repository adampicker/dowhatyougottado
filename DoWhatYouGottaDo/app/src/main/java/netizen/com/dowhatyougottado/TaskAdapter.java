package netizen.com.dowhatyougottado;

import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import netizen.com.dowhatyougottado.model.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    public interface OnTaskItemClickListener {
        void onItemClick(Task item);
    }

   public LinkedList<Task> taskList;
    OnTaskItemClickListener listener;



    public class TaskViewHolder extends RecyclerView.ViewHolder {

        public TextView taskContentTextView, taskFounderTextView, createdAtTextView, deadlineTextView;

        public TaskViewHolder(View view) {
            super(view);
            taskContentTextView = view.findViewById(R.id.task_content);
            taskFounderTextView = view.findViewById(R.id.task_founder);
            createdAtTextView = view.findViewById(R.id.createdAtText);
            deadlineTextView = view.findViewById(R.id.deadlineText);
        }

        public void bind(final Task item, final OnTaskItemClickListener listener) {
            Log.e("XD", "clickec + " + item.getFounderName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public TaskAdapter(LinkedList<Task> taskList, OnTaskItemClickListener listener){
        this.taskList = taskList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.task_layout, viewGroup, false);

        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {

        Task task = taskList.get(i);
        taskViewHolder.taskContentTextView.setText(task.getContent());
        taskViewHolder.taskFounderTextView.setText(task.getFounderName());
        taskViewHolder.createdAtTextView.setText(getAgoTime(task));
        taskViewHolder.deadlineTextView.setText(getDeadline(task));

        taskViewHolder.bind(task, listener);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    private String getAgoTime(Task task){

         Long diff = Calendar.getInstance().getTimeInMillis() - task.getCreatedAt();
         int seconds = (int) (diff/1000);
         int minutes = (int) (diff/1000/60);
         int hours = (int) (diff/1000/60/60);
         int days = (int) (diff/1000/60/60/24);
         if (days == 0){
             if(hours == 0) {
                 if (minutes == 0){
                     if (seconds == 0) return "just now.";
                     else return seconds + "s ago.";
                 } else return minutes +"m ago.";
             } else return hours + "h ago.";
         } else return days +"d ago.";
    }

    private String getDeadline(Task task){
        String beforeResponse = "till deadline.";
        String lateResponse = " too late!";
        String RESPONSE;
        Long diff = task.getDeadline() - Calendar.getInstance().getTimeInMillis() ;
        if (diff < 0) RESPONSE = lateResponse;
        else RESPONSE = beforeResponse;
        int seconds = (int) (diff/1000);
        int minutes = (int) (diff/1000/60);
        int hours = (int) (diff/1000/60/60);
        int days = (int) (diff/1000/60/60/24);
        if (days == 0){
            if(hours == 0) {
                if (minutes == 0){
                    return "do it now!";
                } else return minutes +"m " +RESPONSE;
            } else return hours + "h " + RESPONSE;
        } else return days +"d " + RESPONSE;
    }


}
