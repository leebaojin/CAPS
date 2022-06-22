package sg.edu.iss.caps.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import sg.edu.iss.caps.model.CourseStudent;
import sg.edu.iss.caps.model.Lecturer;
import sg.edu.iss.caps.model.User;
import sg.edu.iss.caps.service.CourseService;
import sg.edu.iss.caps.service.LecturerCourseService;
import sg.edu.iss.caps.service.LecturerService;
import sg.edu.iss.caps.service.UserSessionService;
import sg.edu.iss.caps.util.MenuNavBarUtil;

@Controller
@RequestMapping("/lecturer")
public class LecturerUpdateCourseController {

    @Autowired
    LecturerCourseService lecturerCourseService;
    @Autowired
    LecturerService lecturerService;
    @Autowired
    CourseService courseService;

    @RequestMapping("/list-courses")
    public String findLecturerAssignedCourses(HttpSession session, Model model) {
    	Lecturer user = (Lecturer) UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);

    	Lecturer l = lecturerService.findLecturerById(user.getLecturerId());
        model.addAttribute("teachCoursesList", lecturerCourseService.findLecturerAssignedCourses(l));
        return "list-lecturer-courses";
    }
    
    @RequestMapping("/grade-course")
    public String findAssignedCoursesToGrade(HttpSession session, Model model) {
    	Lecturer user = (Lecturer) UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);

    	Lecturer l = lecturerService.findLecturerById(user.getLecturerId());
        model.addAttribute("teachCoursesList", lecturerCourseService.findLecturerAssignedCourses(l));
        return "list-grade-course";
    }
    
    @RequestMapping("/grade-course/{courseCode}")
    public String findStudentsInCourse(HttpSession session, Model model, @PathVariable("courseCode") String courseCode) {
    	User user = UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
        
        model.addAttribute("courseStudentList", lecturerCourseService.findStudentsInCourse(courseCode));
        model.addAttribute("c", courseService.findCourseByCourseCode(courseCode));
        return "list-courses-students";
    }
    
    @GetMapping("/add-score/{courseStudentId}")
    public String loadAddScoreForm(HttpSession session, Model model, @PathVariable("courseStudentId") Integer courseStudentId) {
    	User user = UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);

    	model.addAttribute("courseStudent", lecturerCourseService.findStudentGrade(courseStudentId));
        return "studentGrade-form";
    }
    
    @PostMapping("/add-score/{courseStudentId}")
    public String saveAddScoreForm(HttpSession session, Model model, @ModelAttribute("courseStudent") @Valid CourseStudent cs, BindingResult bindingResult, int score) {
    	User user = UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
    	if(bindingResult.hasErrors()) {
			return "studentGrade-form";
		}
    	CourseStudent cs2 = lecturerCourseService.findStudentGrade(cs.getCourseStudentId());
    	lecturerCourseService.saveStudentGrade(cs2, score);
        return "redirect:/lecturer/grade-course/" + cs2.getCourse().getCourseCode();
    }
    
//  @RequestMapping("/list-course-students/{courseCode}")
//  public String findStudentsInCourse(HttpSession session, Model model, @PathVariable("courseCode") String courseCode) {
//  	User user = UserSessionService.findUser(session);
//  	MenuNavBarUtil.generateNavBar(user, model);
//      
//      model.addAttribute("courseStudentList", lecturerCourseService.findStudentsInCourse(courseCode));
//      model.addAttribute("c", courseService.findCourseByCourseCode(courseCode));
//      return "list-courses-students";
//  }
    
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
