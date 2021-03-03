package es.urjc.dadproject.youwatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class userController
{
    private static final Logger log = LoggerFactory.getLogger(homeController.class);
    @GetMapping("/user")
    public String user(Model model)
    {
        log.debug("Home Cargado");
        model.addAttribute("name","patata");
        model.addAttribute("email","patata");
        model.addAttribute("date","patata");
        model.addAttribute("session",false);

        return "user";
    }

    @GetMapping("/user/login")
    public String userLog(Model model)
    {
        log.debug("Home Cargado");
        model.addAttribute("name","patata");
        model.addAttribute("email","patata");
        model.addAttribute("date","patata");
        model.addAttribute("session",false);

        return "user";
    }

}
