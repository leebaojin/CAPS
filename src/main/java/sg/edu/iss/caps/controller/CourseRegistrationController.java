package sg.edu.iss.caps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.caps.model.CourseStudent;
import sg.edu.iss.caps.repo.CourseStudentRepository;

@Controller
@RequestMapping("/course-register")
public class CourseRegistrationController {

    @Autowired
    CourseStudentRepository courseStudentRepo;

    @GetMapping("")
    public String loadRegisterCoursePage(Model model) {
        return "/home";
    }

    @GetMapping("/add")
    public String addCourseStudent(Model model) {
        CourseStudent cs = new CourseStudent();
        model.addAttribute("coursestudent", cs);
        return "add-coursestudent";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourseStudent(Model model, @PathVariable("id") Integer courseStudentId) {
        CourseStudent cs = courseStudentRepo.findById(courseStudentId).get();
        courseStudentRepo.delete(cs);
        return "forward:/course-register/home";
    }
}
