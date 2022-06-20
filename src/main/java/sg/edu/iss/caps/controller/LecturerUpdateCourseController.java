package sg.edu.iss.caps.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import sg.edu.iss.caps.model.Course;
import sg.edu.iss.caps.model.CourseStudent;
import sg.edu.iss.caps.model.CourseStudentStatus;
import sg.edu.iss.caps.model.Grade;
import sg.edu.iss.caps.model.Lecturer;
import sg.edu.iss.caps.model.Role;
import sg.edu.iss.caps.model.User;
import sg.edu.iss.caps.model.UserStatus;
import sg.edu.iss.caps.repo.CourseRepository;
import sg.edu.iss.caps.repo.CourseStudentRepository;
import sg.edu.iss.caps.repo.LecturerRepository;
import sg.edu.iss.caps.service.UserSessionService;
import sg.edu.iss.caps.util.GradeUtil;
import sg.edu.iss.caps.util.HashUtil;
import sg.edu.iss.caps.util.MenuNavBarUtil;

@Controller
@RequestMapping("/lecturer")
public class LecturerUpdateCourseController {

    @Autowired
    CourseStudentRepository courseStudentRepo;
    @Autowired
    CourseRepository courseRepo;
    @Autowired
    LecturerRepository lecturerRepo;
    

    @RequestMapping("/list-courses")
    public String listTeachCourses(HttpSession session, Model model, Lecturer l) {
    	Lecturer user = (Lecturer) UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);

    	Lecturer l2 = lecturerRepo.findById(user.getLecturerId()).get();
    	List<Course>teachCoursesList = l2.getTeachCourses();
        model.addAttribute("teachCoursesList", teachCoursesList);
        return "list-lecturer-courses";
    }
    
    @RequestMapping("/list-course-students/{courseCode}")
    public String listCourseStudents(HttpSession session, Model model, @PathVariable("courseCode") String courseCode) {
    	User user = UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
    	
        List<CourseStudent> courseStudentList = courseStudentRepo.findCSByCourse(courseCode);
        model.addAttribute("courseStudentList", courseStudentList);
        Course c = courseRepo.findCourseCode(courseCode);
        model.addAttribute("c", c);
        return "list-courses-students";
    }
    
    @GetMapping("/add-score/{courseStudentId}")
    public String loadAddScoreForm(HttpSession session, Model model, @PathVariable("courseStudentId") Integer courseStudentId) {
    	User user = UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);

    	model.addAttribute("courseStudent", courseStudentRepo.findById(courseStudentId).get());
        return "courseStudent-form";
    }
    
    @PostMapping("/add-score/{courseStudentId}")
    public String saveAddScoreForm(HttpSession session, Model model, @ModelAttribute("courseStudent") CourseStudent cs, int score) {
    	User user = UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
    	
    	CourseStudent cs2 = courseStudentRepo.findById(cs.getCourseStudentId()).get();
    		cs2.setScore(score);
    		try {
    			cs2.setGrade(GradeUtil.calculateGrade(score));
    			if (cs2.getGrade() == Grade.F) {
					cs2.setCourseStudentStatus(CourseStudentStatus.FAILED);
				} else{
					cs2.setCourseStudentStatus(CourseStudentStatus.COMPLETED);
				};
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		courseStudentRepo.save(cs2);
		
        return "redirect:/lecturer/list-course-students/" + cs2.getCourse().getCourseCode();
    }
    
    
    
//    @PostMapping("/save")
//    public String saveCourseStudentForm(@ModelAttribute("courseStudent")  CourseStudent cs, HttpSession session) {
//    	User user = UserSessionService.findUser(session);
//    	
//    	CourseStudent cs2 = courseStudentRepo.findById(cs.getCourseStudentId()).get();
//    		cs2.setCourseStudentId(cs.getCourseStudentId());
//    		cs2.setCourse(cs.getCourse());
//    		cs2.setStudent(cs.getStudent());
//    		courseStudentRepo.save(cs2);
//        return "forward:/course-student/list";
//    }
        
//    @GetMapping("/{id}/list")
//    public String listCourseStudent(Model model, @PathVariable("id") Integer courseStudentId) {
//        CourseStudent courseStudent = courseStudentRepo.findById(courseStudentId).get();
//        model.addAttribute("courseStudent", courseStudent);
//        return "list-coursestudents";
//    }
//
//    @GetMapping("/{id}/view-grade")
//    public String viewGrade(Model model, @PathVariable("id") Integer courseStudentId) {
//        CourseStudent courseStudent = courseStudentRepo.findById(courseStudentId).get();
//        model.addAttribute("courseStudent", courseStudent);
//        return "coursestudent";
//    }


}
