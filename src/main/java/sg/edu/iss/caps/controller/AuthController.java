package sg.edu.iss.caps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loadLoginPage(Model model) {
        return "login";
    }

    @PostMapping(path = "/authenticate")
    public String login (Model model) {
        return "forward:/home";
    }

    @GetMapping("/logout")
    public String logout (Model model) {
        return "forward:/home";
    }

    @GetMapping("/change-password")
    public String changePassword (Model model) {
        // need to standardize naming conventions here
        return "change-password";
    }

    @GetMapping("/forget-password")
    public String forgetPassword (Model model) {
        // update after completing pages
        return "forward:/request-password";
    }

    @GetMapping("/request-password")
    public String requestPasswordByEmail (Model model) {
        return "request-password";
    }

}
