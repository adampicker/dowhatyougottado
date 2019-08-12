package netizen.com.dowhatyougottado.model;

import java.util.Calendar;
import java.util.Date;

public class Task {

    private Long id;
    private String content;
    private boolean shared;
    private long deadline;
    private long createdAt;
    private boolean done;
    private String founderName;

    public Task(){}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public long getCreatedAt(){
        return this.createdAt;
    }

    public void setCreatedAt(long createdAt){
        this.createdAt = createdAt;
    }

    public boolean getDone(){
        return this.done;
    }

    public void setDone(boolean done){
        this.done = done;
    }

    public String getFounderName() {
        return founderName;
    }

    public void setFounderName(String founderName) {
        this.founderName = founderName;
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
}
