package sg.edu.iss.caps.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.caps.model.CourseStudent;
import sg.edu.iss.caps.model.User;
import sg.edu.iss.caps.repo.CourseRepository;
import sg.edu.iss.caps.repo.CourseStudentRepository;
import sg.edu.iss.caps.service.UserSessionService;
import sg.edu.iss.caps.util.MenuNavBarUtil;

@Controller
@RequestMapping("/course-student")
public class LecturerUpdateCourseController {

    @Autowired
    CourseStudentRepository courseStudentRepo;
    CourseRepository courseRepo;

    @RequestMapping("/list")
    public String listCourseStudents(HttpSession session, Model model) {
    	User user = UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
    	
        List<CourseStudent> courseStudentList = courseStudentRepo.findAll();
        model.addAttribute("courseStudentList", courseStudentList);
        return "list-coursestudents";
    }
    
    @GetMapping("/add-grade/{courseStudentId}")
    public String addGrade(HttpSession session, Model model, @PathVariable("courseStudentId") Integer courseStudentId) {
    	User user = UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
    	
    	model.addAttribute("courseStudent", courseStudentRepo.findById(courseStudentId).get());
        return "courseStudent-form";
    }
    
    @PostMapping("/save")
    public String saveCourseStudentForm(@ModelAttribute("courseStudent")  CourseStudent cs, HttpSession session) {
    	User user = UserSessionService.findUser(session);
    	
    	CourseStudent cs2 = courseStudentRepo.findById(cs.getCourseStudentId()).get();
    		cs2.setCourseStudentId(cs.getCourseStudentId());
    		cs2.setCourse(cs.getCourse());
    		cs2.setStudent(cs.getStudent());
    		courseStudentRepo.save(cs2);
        return "forward:/course-student/list";
    }
    
//public static void  calculateGrade(int score) {
//	
//	Grade g;
//	
//	if(score >= 90 || score <= 100) {
//		g = Grade.A;
//	} else if (score >= 80 || score <= 89){
//		g = Grade.B;
//	} else if (score >= 70 || score <= 79) {
//		g = Grade.C;
//	} else if (score >= 60 || score <= 69) {
//		g = Grade.D;
//	} else if (score >= 0 || score <= 59) {
//		g = Grade.F;
//	}

    
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
