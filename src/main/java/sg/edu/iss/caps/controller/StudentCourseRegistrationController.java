package sg.edu.iss.caps.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.iss.caps.model.Course;
import sg.edu.iss.caps.model.CourseStatus;
import sg.edu.iss.caps.model.CourseStudent;
import sg.edu.iss.caps.model.Student;
import sg.edu.iss.caps.model.User;
import sg.edu.iss.caps.repo.CourseRepository;
import sg.edu.iss.caps.repo.CourseStudentRepository;
import sg.edu.iss.caps.repo.StudentRepository;
import sg.edu.iss.caps.service.CourseServiceImpl;
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
    
    @Autowired
    CourseServiceImpl courseService;

    @GetMapping("")
    public String listAvailableCourse(HttpSession session, HttpServletRequest request, Model model) {
    	Student student = stuRepo.findById(2).get();
    	
    	if(student == null) {
    		return "redirect:/home";
    	}
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
    public String listAvailableCourseBySearch(@RequestParam("findCourse") String searchStr,HttpSession session, Model model) {
    	if(searchStr == null || searchStr.equals("")) {
    		//Redirect if search is empty
    		return "redirect:/student/course-registration";
    	}
    	
    	Student student = stuRepo.findById(2).get();
    	
    	if(student == null) {
    		return "redirect:/home";
    	}
    	
    	MenuNavBarUtil.generateNavBar(student, model);
    	List<Course> courseAvailable = courseService.findSearchCourseForStudent(student, searchStr);
    	model.addAttribute("courseAvailable",courseAvailable);
    	model.addAttribute("searchStr",searchStr);
    	return "studentCourseRegistrationForm";
    }

    @GetMapping("/register/{id}")
    public String addCourseStudent(@PathVariable("id") String courseCode,HttpServletRequest request, Model model) {
    	
    	Student student = stuRepo.findById(2).get();
    	
    	if(student == null) {
    		return "redirect:/home";
    	}
    	
    	Course course = courseRepo.findById(courseCode).get();
    	for(CourseStudent cs : student.getCourseAttended()) {
    		if(cs.getCourse().equals(course)) {
    			//Cannot register if course already present
    			return "redirect:/student/course-registration";
    		}
    	}
    	
    	
    	CourseStudent cs = new CourseStudent(student,course);
    	student.getCourseAttended().add(cs);
    	stuRepo.saveAndFlush(student);
    	
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
