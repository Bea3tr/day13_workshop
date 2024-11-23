package ssf.day13_workshop.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.*;

public class Task {

    @NotEmpty(message="Name cannot be empty")
    @Size(min=5, max=32, message="Name must be between 5 and 32 characters")
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

    public static String serializer(List<Task> tasks) {
        String result = "";
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        // Format: name:priority:deadline
        for(Task task : tasks) {
            String name = task.getName();
            String priority = task.getPriority();
            String deadline = df.format(task.getDeadline());
            result = "%s:%s:%s ".formatted(name, priority, deadline);
        }
        return result;
    }

    public static List<Task> deserializer(String tasks) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        // tasks split by space; attributes split by ":"
        List<Task> taskList = new LinkedList<>();
        if(tasks.length() > 1) {
            String[] taskArr = tasks.trim().split(" ");
            for (String task : taskArr) {
                String[] attr = task.split("\\:");
                Task nTask = new Task();
                nTask.setName(attr[0]);
                nTask.setPriority(attr[1]);
                try {
                    nTask.setDeadline(df.parse(attr[2]));
                } catch (ParseException ex) {
                    System.err.println("Error with date format");
                    ex.printStackTrace();
                }
                taskList.add(nTask);
            }
        }
        return taskList;
    }

    
}
