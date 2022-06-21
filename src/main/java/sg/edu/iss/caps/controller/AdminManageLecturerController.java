package sg.edu.iss.caps.controller;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.caps.model.Lecturer;
import sg.edu.iss.caps.model.User;
import sg.edu.iss.caps.service.LecturerService;
import sg.edu.iss.caps.service.UserSessionService;
import sg.edu.iss.caps.util.MenuNavBarUtil;

@Controller
@RequestMapping("/manage/lecturer") 
public class AdminManageLecturerController {
    
    @Autowired
    LecturerService lecturerService;
    
    @GetMapping("/create")
    public String loadLecturerForm(HttpSession session, Model model) {
    	User user = UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
    	
        Lecturer l = new Lecturer();
        model.addAttribute("lecturer",l);
        model.addAttribute("action","create");
        return "lecturer-form";
    }
    
    @PostMapping("/create")
    public String saveLecturerForm(@ModelAttribute("lecturer") @Valid Lecturer l, BindingResult bindingResult, HttpSession session, Model model) {
    	User user = UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);

    	model.addAttribute("action","create");
    	if(lecturerService.checkIfEmailExist(l)) {
    		bindingResult.rejectValue("email","duplicate","Email already registered");
    	}
    	if(lecturerService.checkIfUsernameExist(l)) {
    		bindingResult.rejectValue("username","duplicate","Username already registered");
    	}
    	if(bindingResult.hasErrors()) {
			return "lecturer-form";
		}
    	lecturerService.saveLecturer(l);
        return "redirect:/manage/lecturer/list";
    }
    
    @GetMapping("/edit/{lecturerId}")
    public String loadEditLecturerForm(Model model, @PathVariable("lecturerId") Integer lecturerId, HttpSession session) {
    	User user = UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
    	
    	model.addAttribute("lecturer", lecturerService.findLecturerById(lecturerId));
    	 model.addAttribute("action","edit");
        return "lecturer-form";
    }
    
    @PostMapping("/edit/{lecturerId}")
    public String saveEditLecturerForm(@ModelAttribute("lecturer") @Valid Lecturer l, BindingResult bindingResult,HttpSession session, Model model) {
    	User user = UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
    	
    	model.addAttribute("action","edit");
		if(bindingResult.hasErrors()) {
			return "lecturer-form";
		}
		lecturerService.saveEditLecturer(l);
        model.addAttribute("action","edit");
        return "redirect:/manage/lecturer/list";
    }

    @RequestMapping("/list")
    public String listLecturers(HttpSession session, Model model) {
    	User user = UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
        
        model.addAttribute("lecturerList", lecturerService.findAllActiveLecturers());
        return "list-lecturers";
    }

    @GetMapping("/delete/{lecturerId}")
    public String deleteLecturer(Model model, @PathVariable("lecturerId") Integer lecturerId, HttpSession session) {
    	User user = UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
    	
    	Lecturer l2 = lecturerService.findLecturerById(lecturerId);
    	lecturerService.deleteLecturer(l2);
        return "redirect:/manage/lecturer/list";
    }
    
//	@GetMapping("/{lecturerId}")
//	public String displayDetails(@PathVariable("lecturerId") String lecturerId, Model model) {
//		List<Integer> displayLecturer = new ArrayList<Integer>();
//		displayLecturer.add(Integer.parseInt(lecturerId));
//		List<Lecturer> lecturerList = lecturerRepo.findAllById(displayLecturer);
//		System.out.println(lecturerList.get(0));
//		model.addAttribute("lecturerList", lecturerList);
//		return "list-lecturers";
//	}
//	
//	@PostMapping("/save")
//    public String saveLecturerForm(@ModelAttribute("lecturer") @Valid Lecturer l, BindingResult bindingResult, Model model) {
//    	
//    	if (l.getLecturerId() != null) {
//    		if(bindingResult.hasErrors()) {
//    			//return "forward:/manage/lecturer/create";
//    			return "lecturer-form";
//    		}
//    		Lecturer l2 = lecturerRepo.findById(l.getLecturerId() ).get();
//    		l2.setFirstName(l.getFirstName());
//    		l2.setLastName(l.getLastName());
//    		l2.setUsername( l.getUsername());
//    		l2.setEmail(l.getEmail());
//    		lecturerRepo.save(l2);
//    	} else {
//    		if(bindingResult.hasErrors()) {
//
//    			//return "forward:/manage/lecturer/create";
//    			return "lecturer-form";
//    		}
//    		l.setRole(Role.LECTURER);
//    		l.setUserStatus(UserStatus.ACTIVE);
//    		String defaultPwd = "123456";
//    		l.setPasswordHash(HashUtil.getHash(l.getUsername(),defaultPwd));
//    		lecturerRepo.save(l);
//    	}
////        return "forward:/lecturer-management/list";
//        return "forward:/manage/lecturer/list";
//
//    }
    // clarify purpose of editLecturerPage
//    @GetMapping("/edit-page")
//    public String editLecturerPage(Model model) {
//        return "edit-lecturer";
//    }

    // clarify difference between this and editLecturerPage
//    @GetMapping("/edit/{lecturerId}")
//    public String editLecturer(Model model, @PathVariable("lecturerId") Integer lecturerId) {
//        model.addAttribute("lecturer", lecturerRepo.findById(lecturerId).get());
//        return "lecturer-form";
//    }
}
