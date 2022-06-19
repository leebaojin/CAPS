package sg.edu.iss.caps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import sg.edu.iss.caps.model.Lecturer;
import sg.edu.iss.caps.model.Role;
import sg.edu.iss.caps.model.UserStatus;
import sg.edu.iss.caps.repo.LecturerRepository;
import sg.edu.iss.caps.util.HashUtil;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

@Controller
@RequestMapping("/manage/lecturer") 
public class AdminManageLecturerController {

    @Autowired
    LecturerRepository lecturerRepo;

    @GetMapping("/create")
    public String loadLecturerForm(Model model) {
        Lecturer l = new Lecturer();
        model.addAttribute("lecturer",l);
        model.addAttribute("action","create");
        return "lecturer-form";
    }
    
    @PostMapping("/create")
    public String loadLecturerForm(@ModelAttribute("lecturer") @Valid Lecturer l, BindingResult bindingResult, Model model) {
    	model.addAttribute("action","create");
    	if(bindingResult.hasErrors()) {
			return "lecturer-form";
		}
		l.setRole(Role.LECTURER);
		l.setUserStatus(UserStatus.ACTIVE);
		String defaultPwd = "123456";
		l.setPasswordHash(HashUtil.getHash(l.getUsername(),defaultPwd));
		lecturerRepo.save(l);
        
        return "redirect:/manage/lecturer/list";
    }
    
    @GetMapping("/edit/{lecturerId}")
    public String loadEditLecturerForm(Model model, @PathVariable("lecturerId") Integer lecturerId) {
    	model.addAttribute("lecturer", lecturerRepo.findById(lecturerId).get());
    	 model.addAttribute("action","edit");
        return "lecturer-form";
    }
    
    @PostMapping("/edit/{lecturerId}")
    public String loadEditLecturerForm(@ModelAttribute("lecturer") @Valid Lecturer l, BindingResult bindingResult, Model model) {
    	 model.addAttribute("action","edit");

		if(bindingResult.hasErrors()) {
			return "lecturer-form";
		}
		Lecturer l2 = lecturerRepo.findById(l.getLecturerId() ).get();
		l2.setFirstName(l.getFirstName());
		l2.setLastName(l.getLastName());
		l2.setUsername( l.getUsername());
		l2.setEmail(l.getEmail());
		lecturerRepo.save(l2);
		
        model.addAttribute("action","edit");
        return "redirect:/manage/lecturer/list";
    }

    @RequestMapping("/list")
    public String listLecturers(Model model) {
        List<Lecturer> lecturerList = lecturerRepo.findAllActiveLecturers();
        model.addAttribute("lecturerList", lecturerList);
        return "list-lecturers";
    }

    @GetMapping("/delete/{lecturerId}")
    public String deleteLecturer(Model model, @PathVariable("lecturerId") Integer lecturerId) {
    	Lecturer l2 = lecturerRepo.findById(lecturerId ).get();
    	if (l2.getLecturerId() != null) {
    		l2.setUserStatus(UserStatus.INACTIVE);
    		lecturerRepo.save(l2);
    	}
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
