package sg.edu.iss.caps.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.caps.model.User;
import sg.edu.iss.caps.util.MenuNavBarUtil;
import sg.edu.iss.caps.util.UserSessionUtil;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	@RequestMapping("")
	public String loadHomePage(HttpSession session, Model model) {
		User user = UserSessionUtil.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
		return "index";
	}
	
	@RequestMapping("/contact")
	public String loadContactPage(HttpSession session, Model model) {
		User user = UserSessionUtil.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
		return "contact";
	}

}
