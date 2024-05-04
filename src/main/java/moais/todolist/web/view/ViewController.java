package moais.todolist.web.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String mainForm() {
        return "form/main";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "form/login";
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "form/signup";
    }

    @GetMapping("/list")
    public String listForm() {
        return "form/list";
    }

    @GetMapping("/save")
    public String saveForm() {
        return "form/save";
    }

    @GetMapping("/latest")
    public String latestForm() {
        return "form/latest";
    }

}
