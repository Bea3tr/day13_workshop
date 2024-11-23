package ssf.day13_workshop.models;

import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path={"/", "index.html"})
public class IndexController {

    private final Logger logger = Logger.getLogger(IndexController.class.getName());

    @GetMapping
    public String getIndex(Model model) {

        logger.info("New session started");

        model.addAttribute("task", new Task());
        model.addAttribute("hiddenList", "");

        return "_index";
    }
    
}
