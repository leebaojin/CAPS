package sg.edu.iss.caps.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.caps.model.Course;
import sg.edu.iss.caps.model.CourseStudent;
import sg.edu.iss.caps.model.Student;
import sg.edu.iss.caps.model.User;
import sg.edu.iss.caps.service.StudentCourseServiceImpl;
import sg.edu.iss.caps.service.StudentService;
import sg.edu.iss.caps.service.UserSessionService;
import sg.edu.iss.caps.util.MenuNavBarUtil;
import sg.edu.iss.caps.util.UserSessionUtil;

@Controller
@RequestMapping("/student")
public class StudentProfileController {

	@Autowired
	StudentCourseServiceImpl scsImpl;
	
	@Autowired
	StudentService studentService;
	
	@Autowired
    UserSessionService userSessionService;

	@GetMapping("/courses")
	public String listCourses(Model model) {
		User user = userSessionService.findUserSession();
		if (user == null || !(user instanceof Student)) {
			return "redirect:/home";
		}

		Student student = (Student) user;

		MenuNavBarUtil.generateNavBar(student, model);

		// Find enrolled courses
		List<Course> enrolledCourses = scsImpl.findEnrolledCourse(student);
		model.addAttribute("enrolledCourses", enrolledCourses);
		return "view-courses";
	}

	@GetMapping("/grades")
	public String listGrades(Model model) {
		User user = userSessionService.findUserSession();
		if (user == null || !(user instanceof Student)) {
			return "redirect:/home";
		}

		Student student = (Student) user;
		MenuNavBarUtil.generateNavBar(student, model);
		
		List<CourseStudent> courseGrades = scsImpl.findStudentGrades(student);
		
		int numerator = 0;
		int denomenator = 0;
		
		for(CourseStudent courseGrade : courseGrades) {
			Course c = courseGrade.getCourse();
			numerator += courseGrade.getScore() * Integer.parseInt(c.getCourseCredits());
			denomenator += Integer.parseInt(c.getCourseCredits());
		}
		
		double overallGrade = numerator / denomenator;

		model.addAttribute("overallGrade", overallGrade);
		model.addAttribute("courseGrades", courseGrades);	

		return "view-grades";
	}

	@GetMapping("/profile")
	public String viewStudentProfile(Model model) {
		User user = userSessionService.findUserSession();
		if (user == null || !(user instanceof Student)) {
			return "redirect:/home";
		}

		Student student = (Student) user;
		MenuNavBarUtil.generateNavBar(student, model);
		
		model.addAttribute("student", student);
		return "student-profile";
	}
	
	@RequestMapping("/profile-edit")
	public String editStudentProfile(Model model) {
		User user = userSessionService.findUserSession();
		if (user == null || !(user instanceof Student)) {
			return "redirect:/home";
		}

		Student student = (Student) user;
		MenuNavBarUtil.generateNavBar(student, model);
		
		model.addAttribute("student", student);
		return "profile-edit";
	}
	
	@GetMapping("/profile-update")
	public String saveStudentProfile(@ModelAttribute @Valid Student student, Model model, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return "forward:/student/profile-edit";
		Student s1 = userSessionService.findStudentSession();
		if(s1 == null || s1.getStudentId() != student.getStudentId()) {
			return "redirect:/home";
		}
		
		Student s = studentService.changeStudentProfile(student);
		userSessionService.setUserSession(s);
		
		return "forward:/student/profile";
	}
}
