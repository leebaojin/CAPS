package sg.edu.iss.caps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sg.edu.iss.caps.model.CourseStudent;
import sg.edu.iss.caps.model.Grade;
import sg.edu.iss.caps.repo.CourseRepository;
import sg.edu.iss.caps.repo.CourseStudentRepository;

@Controller
@RequestMapping("/course-student")
public class CourseStudentController {

    @Autowired
    CourseStudentRepository courseStudentRepo;
    CourseRepository courseRepo;

    @GetMapping("/{id}/list")
    public String listCourseStudent(Model model, @PathVariable("id") Integer courseStudentId) {
        CourseStudent courseStudent = courseStudentRepo.findById(courseStudentId).get();
        model.addAttribute("courseStudent", courseStudent);
        return "list-coursestudent";
    }

    @GetMapping("/{id}/view-grade")
    public String viewGrade(Model model, @PathVariable("id") Integer courseStudentId) {
        CourseStudent courseStudent = courseStudentRepo.findById(courseStudentId).get();
        model.addAttribute("courseStudent", courseStudent);
        return "coursestudent";
    }

    @PostMapping("/{id}/add-grade")
    public String addGrade(Model model, @PathVariable("id") Integer courseStudentId, @ModelAttribute("grade") Grade g) {
        CourseStudent courseStudent = courseStudentRepo.findById(courseStudentId).get();
        courseStudent.setGrade(g);
        courseStudentRepo.save(courseStudent);
        return "forward:/{id}/view-grade";
    }
}
