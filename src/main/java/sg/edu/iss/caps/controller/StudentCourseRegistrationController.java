package sg.edu.iss.caps.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.iss.caps.model.Course;
import sg.edu.iss.caps.model.CourseStudent;
import sg.edu.iss.caps.model.Student;
import sg.edu.iss.caps.model.User;
import sg.edu.iss.caps.service.CourseService;
import sg.edu.iss.caps.service.EmailService;
import sg.edu.iss.caps.service.StudentCourseService;
import sg.edu.iss.caps.util.MenuNavBarUtil;
import sg.edu.iss.caps.util.UserSessionUtil;

@Controller
@RequestMapping("/student/course-registration")
public class StudentCourseRegistrationController {
	//For students to register courses for themselves
    
    @Autowired
    CourseService courseService;
    
    @Autowired
    StudentCourseService courseStudentService;
    
    @Autowired
    EmailService emailService;

    @GetMapping("")
    public String listAvailableCourse(HttpSession session, HttpServletRequest request, 
    		Model model) {
    	
    	User user = UserSessionUtil.findUser(session);
    	if(user == null || !(user instanceof Student)) {
    		return "redirect:/home";
    	}
    	
    	Student student = (Student) user;
    	
    	//Generate navigation bar
    	MenuNavBarUtil.generateNavBar(student, model);
    	
    	//Find available courses
    	List<Course> courseAvailable = courseService.findAvailableCourseForStudent(student);
    	model.addAttribute("courseAvailable",courseAvailable);
    	
    	//For successful course registration
    	if(request.getAttribute("successfulRegistration") != null){
        	model.addAttribute("successfulRegistration",request.getAttribute("successfulRegistration"));
        	model.addAttribute("successfulCourse",request.getAttribute("successfulCourse"));
    	}
    	
        return "studentCourseRegistrationForm";
    }
    
    
    @GetMapping("/find")
    public String listAvailableCourseBySearch(@RequestParam("findCourse") String searchStr,
    		HttpSession session, Model model) {
    	
    	if(searchStr == null || searchStr.equals("")) {
    		//Redirect if search is empty
    		return "redirect:/student/course-registration";
    	}
    	
    	User user = UserSessionUtil.findUser(session);
    	if(user == null || !(user instanceof Student)) {
    		return "redirect:/home";
    	}
    	
    	Student student = (Student) user;
    	
    	MenuNavBarUtil.generateNavBar(student, model);
    	List<Course> courseAvailable = courseService.findSearchCourseForStudent(student, searchStr);
    	model.addAttribute("courseAvailable",courseAvailable);
    	model.addAttribute("searchStr",searchStr);
    	return "studentCourseRegistrationForm";
    }

    @GetMapping("/register/{id}")
    public String addCourseStudent(@PathVariable("id") String courseCode, HttpSession session, 
    		HttpServletRequest request, Model model) {
    	
    	User user = UserSessionUtil.findUser(session);
    	if(user == null || !(user instanceof Student)) {
    		return "redirect:/home";
    	}
    	Student student = (Student) user;
    	
    	//Create the new CourseStudent
    	CourseStudent cs = courseStudentService.CreateNewCourseStudent(student, courseCode);
    	if(cs == null) {
    		return "redirect:/home";
    	}
    	
    	//Send email notification
    	//emailService.SendCourseRegisteredEmail(student.getEmail(), String.join(" ", student.getFirstName(),student.getLastName()), courseCode);
    	
    	//Set the required attribute to the request before forwarding
    	request.setAttribute("successfulRegistration",true);
    	request.setAttribute("successfulCourse",courseCode);
        return "forward:/student/course-registration"; //Forward and pass new data
    }
    
    //Should not be able to unregister as a student. This should be left to admin
    /*
    @GetMapping("/delete/{id}")
    public String deleteCourseStudent(Model model, @PathVariable("id") Integer courseStudentId) {
        CourseStudent cs = courseStudentRepo.findById(courseStudentId).get();
        courseStudentRepo.delete(cs);
        return "forward:/course-register/home";
    }
    */
}
