package sg.edu.iss.caps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.caps.model.Course;
import sg.edu.iss.caps.model.Student;
import sg.edu.iss.caps.repo.CourseRepository;
import sg.edu.iss.caps.repo.StudentRepository;

@Controller
@RequestMapping("/student")
public class StudentProfileController {

    @Autowired
    CourseRepository courseRepo;
    StudentRepository studentRepo;

    @GetMapping("/courses")
    public String listCourses(Model model){
        List<Course> courseList = courseRepo.findAll();
        return "courses";
    }

    @GetMapping("/grades")
    public String listGrades(Model model, @ModelAttribute("student") Student s) {
        return "grades";
    }

    @GetMapping("/{id}")
    public String viewStudentProfile(Model model, @PathVariable("id") Integer id) {
        Student s = studentRepo.findById(id).get();
        model.addAttribute("student", s);
        return "profile";
    }
}
