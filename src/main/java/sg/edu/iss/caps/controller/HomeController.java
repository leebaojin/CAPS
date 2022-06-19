package sg.edu.iss.caps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.caps.util.HeaderUtil;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	@RequestMapping("")
	public String loadHomePage(Model model) {
<<<<<<< HEAD
		HeaderUtil.setHeader(model, new String[] {"Home", "Course"}, "John");
=======
		HeaderUtil.setHeader(model, new String[] {"Home", "Login"}, "John");
>>>>>>> branch 'main' of https://github.com/leebaojin/CAPS.git
		return "index";
	}
<<<<<<< HEAD
	
	@RequestMapping("/contact")
	public String loadContactPage(Model model) {
		HeaderUtil.setContacts(model, new String[] {"Contact", "Details"}, "John");
		return "contact";
	}

=======

	
>>>>>>> branch 'main' of https://github.com/leebaojin/CAPS.git
}
