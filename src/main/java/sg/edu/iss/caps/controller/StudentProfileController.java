package sg.edu.iss.caps.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.caps.model.Course;
import sg.edu.iss.caps.model.Student;
import sg.edu.iss.caps.model.User;
import sg.edu.iss.caps.repo.CourseRepository;
import sg.edu.iss.caps.repo.StudentRepository;
import sg.edu.iss.caps.service.UserSessionService;
import sg.edu.iss.caps.util.MenuNavBarUtil;

@Controller
@RequestMapping("/student")
public class StudentProfileController {

    @Autowired
    CourseRepository courseRepo;
    StudentRepository studentRepo;

    @GetMapping("/courses")
    public String listCourses(HttpSession session, Model model){
    	User user = UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
    	
        List<Course> courseList = courseRepo.findAll();
        return "courses";
    }

    @GetMapping("/grades")
    public String listGrades(Model model, @ModelAttribute("student") Student s,HttpSession session) {
    	User user = UserSessionService.findUser(session);
    	
    	MenuNavBarUtil.generateNavBar(user, model);
        return "grades";
    }

    @GetMapping("/{id}")
    public String viewStudentProfile(Model model, @PathVariable("id") Integer id,HttpSession session) {
    	User user = UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
    	
        Student s = studentRepo.findById(id).get();
        model.addAttribute("student", s);
        return "profile";
    }
}
