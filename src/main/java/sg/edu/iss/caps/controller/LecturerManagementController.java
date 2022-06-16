package sg.edu.iss.caps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sg.edu.iss.caps.model.Lecturer;
import sg.edu.iss.caps.model.Student;
import sg.edu.iss.caps.repo.LecturerRepository;

import java.util.List;

@Controller
@RequestMapping("/lecturer-management")
public class LecturerManagementController {

    @Autowired
    LecturerRepository lecturerRepo;

    @GetMapping("/create")
    public String createLecturerPage(Model model) {
        Lecturer l = new Lecturer();
        model.addAttribute("lecturer",l);
        return "create-lecturer";
    }

    @GetMapping("/list")
    public String listLecturers(Model model) {
        List<Lecturer> lecturerList = lecturerRepo.findAll();
        model.addAttribute("studentList", lecturerList);
        return "list-lecturers";
    }

    @PostMapping("/save")
    public String saveLecturer(@ModelAttribute("lecturer") Lecturer l) {
        lecturerRepo.save(l);
        return "forward:/lecturer-management/list";
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
