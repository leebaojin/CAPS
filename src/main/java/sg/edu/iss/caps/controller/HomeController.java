package sg.edu.iss.caps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.caps.util.HeaderUtil;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	@RequestMapping("/home")
	public String loadHomePage(Model model) {
		HeaderUtil.setHeader(model, new String[] {"Home", "Login"}, "John");

		return "index";
	}

	@GetMapping("/contact")
	public String loadContactPage(Model model) {
		Contact 1 = new contact();
		model.addAttribute("contact", 1);
		return "contact";
	}
	
	@GetMapping("/student")
	public String loadStudentPage(Model model) {
		Student 1 = new student();
		model.addAttribute("student", 1);
		return "student";
	}
	
	@GetMapping("lecturer")
	public String loadLecturerPage(Model model) {
		Lecturer 1 = new lecturer();
		model.addAttribute("lecturer", 1);
		return "lecturer";
	}
	
	@GetMapping("administrator")
	public String loadAdministratorPage(Model model) {
		Administrator 1 = new administrator();
		model.addAttribute("administrator", 1);
		return "administrator";
	}
	
	@PostMapping("/save")
	public String saveContactInfo(@ModelAttribute("contact") Contact 1) {
		if(1.getContactId() != null) {
			Contact 2 = contactRepo.findById(1.getContactId()).get();
    		2.setFirstName(l.getFirstName());
    		2.setLastName(l.getLastName());
    		2.setUsername( l.getUsername());
    		2.setEmail(l.getEmail());
    		contactRepo.save(2);
		}else {
    		l.setRole(Role.Contact);
    		l.setUserStatus(UserStatus.ACTIVE);
    		String defaultPwd = "12358";
    		l.setPasswordHash(HashUtil.getHash(l.getUsername(),defaultPwd));
    		contactRepo.save(l);
		}
		return "forward:/contact";
	}
	
	@PostMapping("/save")
	public String saveStudentInfo(@ModelAttribute("student") Student 1) {
		if(1.getStudentId() != null) {
			Student 2 = studentRepo.findById(1.getStudentId()).get();
    		2.setFirstName(l.getFirstName());
    		2.setLastName(l.getLastName());
    		2.setUsername( l.getUsername());
    		2.setEmail(l.getEmail());
    		studentRepo.save(2);
		}else {
    		l.setRole(Role.Contact);
    		l.setUserStatus(UserStatus.ACTIVE);
    		String defaultPwd = "14701";
    		l.setPasswordHash(HashUtil.getHash(l.getUsername(),defaultPwd));
    		studentRepo.save(l);
		}
		return "forward:/student";
	}
	
	@PostMapping("/save")
	public String saveLecturerInfo(@ModelAttribute("lecturer") Lecturer 1) {
		if(1.getContactId() != null) {
			Lecturer 2 = lecturerRepo.findById(1.getLecturerId()).get();
    		2.setFirstName(l.getFirstName());
    		2.setLastName(l.getLastName());
    		2.setUsername( l.getUsername());
    		2.setEmail(l.getEmail());
    		lecturerRepo.save(2);
		}else {
    		l.setRole(Role.Contact);
    		l.setUserStatus(UserStatus.ACTIVE);
    		String defaultPwd = "15678";
    		l.setPasswordHash(HashUtil.getHash(l.getUsername(),defaultPwd));
    		lecturerRepo.save(l);
		}
		return "forward:/lecturer";
	}
	
	@PostMapping("/save")
	public String saveAdministratorInfo(@ModelAttribute("administrator") Administrator 1) {
		if(1.getAdministratorId() != null) {
			Administrator 2 = administratorRepo.findById(1.getAdministratorId()).get();
    		2.setFirstName(l.getFirstName());
    		2.setLastName(l.getLastName());
    		2.setUsername( l.getUsername());
    		2.setEmail(l.getEmail());
    		administratorRepo.save(2);
		}else {
    		l.setRole(Role.Contact);
    		l.setUserStatus(UserStatus.ACTIVE);
    		String defaultPwd = "16790";
    		l.setPasswordHash(HashUtil.getHash(l.getUsername(),defaultPwd));
    		administratorRepo.save(l);
		}
		return "forward:/administrator";
	}
}
