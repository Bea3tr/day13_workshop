package ssf.day13_workshop.models;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.*;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    public static final String TASK_LIST = "taskList";

    @PostMapping
    public String postTask(Model model,
    @Valid @ModelAttribute("task") Task task,
    BindingResult bindings,
    HttpSession sess) {

        if(bindings.hasErrors())
            return "index";

        List<Task> taskList = (List<Task>)sess.getAttribute(TASK_LIST);

        if(taskList == null) {
            taskList = new LinkedList<>();
            sess.setAttribute(TASK_LIST, taskList);
        }

        taskList.add(task);

        System.out.printf("Bindings: %b\n", bindings.hasErrors());
        System.out.printf(">>> Tasks: %s\n", task);

        model.addAttribute("task", task);
        model.addAttribute(TASK_LIST, taskList);

        return "update";
    }
    
}
