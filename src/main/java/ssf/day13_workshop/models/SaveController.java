package ssf.day13_workshop.models;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/exit")
public class SaveController {

    @PostMapping
    public String postExit(HttpSession sess, Model model) {

        List<Task> taskList = (List<Task>)sess.getAttribute(TaskController.TASK_LIST);

        System.out.printf(">>> Task list: %s\n", taskList);

        sess.invalidate();

        model.addAttribute("task", new Task());

        return "index";
    }

    
}
