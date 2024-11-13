package ssf.day13_workshop.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.*;

public class Task {

    @NotEmpty(message="Name cannot be empty")
    @Size(min=5, max=32, message="Name must be between 2 and 32 characters")
    private String name = "";

    private String priority = "";

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message="Are you a time traveler?")
    private Date deadline;

    public String getName() {return name;}
    public void setName(String name) {this.name = name.toUpperCase();}

    public String getPriority() {return priority;}
    public void setPriority(String priority) {this.priority = priority.toUpperCase();}
    
    public Date getDeadline() {return deadline;}
    public void setDeadline(Date deadline) {this.deadline = deadline;}

    @Override
    public String toString() {
        return "Task [name=" + name + ", priority=" + priority + ", deadline=" + deadline + "]";
    }
}
