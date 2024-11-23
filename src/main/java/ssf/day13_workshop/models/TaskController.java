package ssf.day13_workshop.models;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

import static ssf.day13_workshop.models.Task.*;

@Controller
@RequestMapping
public class TaskController {

    public static final String TASK_LIST = "taskList";
    // The logger name is the class name
    private final Logger logger = Logger.getLogger(TaskController.class.getName());

    @PostMapping("/tasks")
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

        logger.info("Task %s".formatted(task));

        model.addAttribute("task", task);
        model.addAttribute(TASK_LIST, taskList);

        return "update";
    }

    @GetMapping("/tasks/noHttp")
    public String getTask(Model model,
        @ModelAttribute String hiddenList,
        @ModelAttribute Task task) {

        logger.info("Entering get task");
        logger.info("Task %s".formatted(task));

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        // Format: name:priority:deadline
        hiddenList = hiddenList + "%s:%s:%s ".formatted(task.getName(), task.getPriority(), df.format(task.getDeadline()));
        model.addAttribute("hiddenList", hiddenList);

        return "_update";
    }

    @PostMapping("/tasks/noHttp")
    public String postTaskNoHttp(Model model,
        @Valid @ModelAttribute Task task,
        @RequestParam String list,
        BindingResult bindings) {

        String[] tasks = list.trim().split(" ");
        logger.info("No. of tasks: %d".formatted(tasks.length));
        logger.info("Entered post mapping");
        
        List<Task> taskList = deserializer(list);
        taskList.add(task);
        list = serializer(taskList);

        if(bindings.hasErrors()) {
            logger.warning("Errors detected");
            System.out.println(bindings.toString());
            return "_index";
        }

        System.out.printf("Bindings: %b\n", bindings.hasErrors());

        logger.info("Task %s".formatted(task));

        model.addAttribute("task", task);
        model.addAttribute(TASK_LIST, taskList);
        model.addAttribute("hiddenList", list);

        return "_update";
    }
}
