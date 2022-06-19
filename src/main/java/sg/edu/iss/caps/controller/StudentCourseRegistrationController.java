package sg.edu.iss.caps.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.caps.model.Course;
import sg.edu.iss.caps.model.CourseStatus;
import sg.edu.iss.caps.model.CourseStudent;
import sg.edu.iss.caps.model.Student;
import sg.edu.iss.caps.model.User;
import sg.edu.iss.caps.repo.CourseRepository;
import sg.edu.iss.caps.repo.CourseStudentRepository;
import sg.edu.iss.caps.repo.StudentRepository;
import sg.edu.iss.caps.util.MenuNavBarUtil;

@Controller
@RequestMapping("/student/course-registration")
public class StudentCourseRegistrationController {
	//For students to register courses for themselves

    @Autowired
    CourseStudentRepository courseStudentRepo;
    
    @Autowired
    StudentRepository stuRepo;
    
    @Autowired
    CourseRepository courseRepo;

    @GetMapping("")
    public String listAvailableCourse(HttpSession session, Model model) {
    	Student student = stuRepo.findById(2).get();
    	MenuNavBarUtil.generateNavBar(student, model);
    	if(student == null) {
    		return "redirect:/home";
    	}
    	List<Course> courseNotTaken = courseRepo.findCourseNotTaken(student);
    	//Filter out the full courses
    	List<Course> courseAvailable = courseNotTaken.stream()
    									.filter(c -> (c.getCourseStudents().size() >= c.getCourseCapacity()) &&
    											c.getCourseStatus() == CourseStatus.OPEN)
    									.collect(Collectors.toList());
    	model.addAttribute("courseAvailable",courseAvailable);
        return "studentCourseRegistrationForm";
    }

    @GetMapping("/register/{id}")
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
