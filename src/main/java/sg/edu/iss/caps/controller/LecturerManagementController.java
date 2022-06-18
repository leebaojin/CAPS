package sg.edu.iss.caps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sg.edu.iss.caps.model.Lecturer;
import sg.edu.iss.caps.repo.LecturerRepository;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

@Controller
@RequestMapping("/lecturer-management")
public class LecturerManagementController {

    @Autowired
    LecturerRepository lecturerRepo;

    @GetMapping("/create")
    public String loadLecturerForm(Model model) {
        Lecturer l = new Lecturer();
        model.addAttribute("lecturer",l);
        return "lecturer-form";
    }
    
    @PostMapping("/save")
    public String saveLecturerForm(@ModelAttribute("lecturer") @Valid Lecturer l, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
        	return "lecturer-form";
        }
    	lecturerRepo.save(l);
        return "forward:/lecturer-management/list";
    }

    @RequestMapping("/list")
    public String listLecturers(Model model) {
        List<Lecturer> lecturerList = lecturerRepo.findAll();
        model.addAttribute("lecturerList", lecturerList);
        return "list-lecturers";
    }

	@GetMapping("/{lecturerId}")
	public String displayDetails(@PathVariable("lecturerId") String lecturerId, Model model) {
		List<Integer> displayLecturer = new ArrayList<Integer>();
		displayLecturer.add(Integer.parseInt(lecturerId));
		List<Lecturer> lecturerList = lecturerRepo.findAllById(displayLecturer);
		System.out.println(lecturerList.get(0));
		model.addAttribute("lecturerList", lecturerList);
		return "list-lecturers";
	}

    // clarify purpose of editLecturerPage
    @GetMapping("/edit-page")
    public String editLecturerPage(Model model) {
        return "edit-lecturer";
    }

    // clarify difference between this and editLecturerPage
    @GetMapping("/edit/{id}")
    public String editLecturer(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("lecturer", lecturerRepo.findById(id).get());
        return "edit-lecturer";
    }

    @GetMapping("/delete/{id}")
    public String deleteLecturer(Model model, @PathVariable("id") Integer id) {
        Lecturer l = lecturerRepo.findById(id).get();
        lecturerRepo.delete(l);
        return "forward:/lecturer-management/list";
    }
}
