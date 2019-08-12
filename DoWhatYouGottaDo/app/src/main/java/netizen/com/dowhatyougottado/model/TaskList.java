package netizen.com.dowhatyougottado.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class TaskList implements Serializable {

    private LinkedList<Task> tasks = new LinkedList<>();

    public TaskList(){}

    public LinkedList<Task> getTasks(){
        return this.tasks;
    }

    public void setTasksList(LinkedList<Task> taskList){
        this.tasks = taskList;
    }

    public LinkedList<Task> getPrivateTasks(){
        LinkedList<Task> privateTasks = new LinkedList<>();
        for (Task task : tasks) {
            if(!task.getShared()) privateTasks.add(task);
        }

        return privateTasks;
    }

    public LinkedList<Task> getSharedTasks(){
        LinkedList<Task> privateTasks = new LinkedList<>();
        for (Task task : tasks) {
            if(task.getShared()) privateTasks.add(task);
        }

        return privateTasks;
    }
}
