package sg.edu.iss.caps.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.caps.model.Course;
import sg.edu.iss.caps.model.Student;
import sg.edu.iss.caps.model.User;
import sg.edu.iss.caps.service.StudentCourseServiceImpl;
import sg.edu.iss.caps.service.StudentServiceImpl;
import sg.edu.iss.caps.service.UserSessionService;
import sg.edu.iss.caps.util.MenuNavBarUtil;

@Controller
@RequestMapping("/manage/studentcourse")
public class AdminManageStudentCourseController {
	@Autowired
    StudentServiceImpl stuService;
	
	@Autowired
	StudentCourseServiceImpl scsImpl;
	
	@Autowired
    UserSessionService userSessionService;
	
	@GetMapping("/view")
    public String studentUnenrollView(HttpSession session, Model model) {
		
    	User user = userSessionService.findUserSession();
    	MenuNavBarUtil.generateNavBar(user, model);
    	
    	List<Student> studentActiveList = stuService.findAllActiveStudents();
        model.addAttribute("studentList", studentActiveList);    	
        return "student-list-view-unenroll";
    }
	
	@GetMapping("/course-list/{id}")
    public String studentEnrolledCourses(Model model, @PathVariable("id") Integer id, HttpSession session) {
		User user = userSessionService.findUserSession();
    	MenuNavBarUtil.generateNavBar(user, model);
    	
        Student student = stuService.findStudentById(id);
        List<Course> enrolledCourses = scsImpl.findEnrolledCourse(student);
        model.addAttribute("studentId", id);
		model.addAttribute("enrolledCourses", enrolledCourses);
        return "course-list-view-unenroll";
    }
	
	@GetMapping("/unenroll/{id}/{coursecode}")
    public String unenrollCourse(Model model, @PathVariable("id") Integer id,@PathVariable("coursecode") String courseCode, HttpSession session) {
		User user = userSessionService.findUserSession();
    	MenuNavBarUtil.generateNavBar(user, model);
    	
    	scsImpl.unenrollStudentFromCourse(id, courseCode);
        return "redirect:/manage/studentcourse/course-list/{id}";
    }
}
