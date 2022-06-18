package sg.edu.iss.caps.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.iss.caps.model.Account;
import sg.edu.iss.caps.model.ChangePWRequest;
import sg.edu.iss.caps.model.User;
import sg.edu.iss.caps.service.AccountAuthenticationService;
import sg.edu.iss.caps.service.UserSessionService;

@Controller
public class AuthController {
	
	@Autowired
	AccountAuthenticationService accAuthService;
	

    @GetMapping("/login")
    public String loadLoginPage(Model model) {
    	Account account = new Account();
    	model.addAttribute("account",account);
    	model.addAttribute("repeatlogin", false);
        return "login";
    }

    @PostMapping(path = "/authenticate")
    public String login (@ModelAttribute("Account") Account account, HttpSession session, Model model) {
    	//Admin - capslbj, Student - troy, Lecturer - yuenkwan
    	
    	//To check if the user exist
    	User user = accAuthService.authenticateAccount(account);
    	if(user == null) {
    		//Return back to login page if fail to authenticate
        	model.addAttribute("account",account);
        	model.addAttribute("repeatlogin", true);
        	//Clear any old password reset request since login is successful
        	accAuthService.updatePasswordResetRequest(user,null);
    		return "login";
    	}
    	//Set the user into the session data
    	UserSessionService.setUser(session, user);
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout (HttpSession session, Model model) {
    	UserSessionService.removeSession(session);
        return "redirect:/home";
    }
    
    
    
    //Forget password handling
    //Will send an email with a UUID valid for 1 hour
    //Need to create another model to handle this. UUID to be stored 
    //UUID, timeout, student, lecturer, administrator
    //When a user login, it will also check if there is a request present
    //If the user clicks the UUID url, it will divert the user to the password change page
    //Timeout is the timelimit the UUID is valid
    @GetMapping("/login/forget-password")
    public String forgetPassword (Model model) {
        // update after completing pages
        return "loginForgetPassword";
    }
    
    @GetMapping("/login/request-reset")
    public String resquestPasswordReset (@RequestParam("emailval") String emailstr,HttpServletRequest request, Model model) {
        // update after completing pages
    	User user = accAuthService.findUserByEmail(emailstr);
    	String url = request.getRequestURL().toString().replace(request.getRequestURI(),"/");
    	if(user != null) {
    		accAuthService.sendPasswordResetEmail(user,url);
    	}
        return "loginSentReset";
    }

    @GetMapping("/login/passwordreset")
    public String requestPasswordByEmail (@RequestParam("resetId") String uuidStr, Model model) {
    	//Checks if the uuid is valid and redirect to password reset page
    	if(uuidStr == null) {
    		return "redirect:/login";
    	}
    	ChangePWRequest chPwRequest = accAuthService.findPasswordResetRequestById(uuidStr);
    	if(chPwRequest == null) {
    		return "redirect:/login";
    	}
    	model.addAttribute("resetId",uuidStr);
    	model.addAttribute("repeatreset",false);
        return "loginPasswordResetForm";
    }
    
    //To check the ChangePWRequest table
    @GetMapping("/change-password")
    public String changePassword (Model model) {
        // need to standardize naming conventions here
    	String uuidStr = UUID.randomUUID().toString();
    	model.addAttribute("resetId",uuidStr);
    	model.addAttribute("repeatreset",false);
        return "loginPasswordResetForm";
    }
    
    //To check the ChangePWRequest table
    @PostMapping("/login/reset-password")
    public String resetPassword (@ModelAttribute("resetId") String uuidStr,@ModelAttribute("newPW") String newPW,
    		@ModelAttribute("newCmfPW") String newCmfPW,Model model) {
        // To check if both password are the same
    	if(!newPW.equals(newCmfPW)) {
    		model.addAttribute("resetId",uuidStr);
    		model.addAttribute("repeatreset",true);
    		return "loginPasswordResetForm";
    	}
    	accAuthService.changeNewPassword(uuidStr,newPW);
    	
        return "redirect:/login";
    }

}
