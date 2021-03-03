package es.urjc.dadproject.youwatch;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class homeController
{
    private static final Logger log = LoggerFactory.getLogger(homeController.class);

    @GetMapping("/")
    public String home(Model model)
    {
        log.debug("Home Cargado");
        model.addAttribute("name","World");
        model.addAttribute("session",false);
        return "home";
    }
}
