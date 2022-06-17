package sg.edu.iss.caps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import sg.edu.iss.caps.model.Account;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loadLoginPage(Model model) {
    	Account account = new Account();
    	model.addAttribute("account",account);
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
    
    //Forget password handling
    //Will send an email with a UUID valid for 1 hour
    //Need to create another model to handle this. UUID to be stored 
    //UUID, timeout, student, lecturer, administrator
    //When a user login, it will also check if there is a request present
    //If the user clicks the UUID url, it will divert the user to the password change page
    //Timeout is the timelimit the UUID is valid
    @GetMapping("/forget-password")
    public String forgetPassword (Model model) {
        // update after completing pages
        return "forward:/request-password";
    }

    @GetMapping("/request-password")
    public String requestPasswordByEmail (Model model) {
        return "request-password";
    }
    
    public String resetPassword(Model model) {
    	return "";
    }

}
