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
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.iss.caps.model.Course;
import sg.edu.iss.caps.model.Lecturer;
import sg.edu.iss.caps.model.User;
import sg.edu.iss.caps.service.CourseService;
import sg.edu.iss.caps.service.LecturerService;
import sg.edu.iss.caps.util.MenuNavBarUtil;
import sg.edu.iss.caps.util.UserSessionUtil;

@Controller
@RequestMapping("/manage/course")
public class AdminManageCourseController {
    
    @Autowired
    CourseService courseService;
    
    @Autowired
    LecturerService lecturerService;
    
    @RequestMapping("/list")
    public String listCourses(HttpSession session, Model model, @RequestParam(value="pageNo", required = false) Integer pageNo) {
    	User user = UserSessionUtil.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
    	
    	//Page<Course> pageCourse = courseService.findAllCoursesSortPage(0, 5);
    	//List<Course> courseList = pageCourse.getContent();
    	
        List<Course> courseList = courseService.findAllCourses();
        List<Lecturer> lecturerList = lecturerService.findAllLecturers();
        model.addAttribute("courseList", courseList);
        model.addAttribute("lecturerList", lecturerList);
        return "list-courses";
    }
    
    @GetMapping("/delete/{courseId}")
    public String deleteCourse(Model model, @PathVariable("courseId") String courseId, HttpSession session) {
    	User user = UserSessionUtil.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
    	
        courseService.deleteCourse(courseId);
        return "redirect:/manage/course/list";
    }
    
    /*
    @GetMapping("/create")
    public String createCoursePage(HttpSession session, Model model) {
    	User user = UserSessionUtil.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
    	
        Course c = new Course();
        
        List<Lecturer> lecturerList = lecturerService.findAllLecturers();
        model.addAttribute("course", c);
        model.addAttribute("lecturerList", lecturerList);
        return "course-form";
    }
	*/
    
    /*
    @PostMapping("/save")
    // validation tbc: courseCode
    public String saveCourse(@ModelAttribute("course") Course c, @RequestParam(value="courseLecturerAdd", required = false) Integer lecturerId, HttpSession session) {
        // if course present, get course object, otherwise create new course
    	
    	User user = UserSessionUtil.findUser(session);
    	
    	Lecturer l = lecturerService.findLecturerById(lecturerId);
    	courseService.SaveCourseAddLecturer(c, l);

        return "forward:/manage/course/list";
    }

	*/
//    // clarify purpose of editCoursePage
//    @GetMapping("/edit-page")
//    public String editCoursePage(Model model) {
//        return "edit-course";
//    }

    // clarify difference between this and editCoursePage
    

/*
    @GetMapping("/edit/{courseId}")
    public String editCourse(Model model, @PathVariable("courseId") String courseId, HttpSession session) {
    	User user = UserSessionUtil.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
    	
        Course c = courseService.findCourseById(courseId);
        List<Lecturer> lecturerList = lecturerService.findAllLecturers();
        model.addAttribute("course", c);
        model.addAttribute("lecturerList", lecturerList);
        return "course-form";
    }
*/
    //Comment out as the course should not be deleted. Instead should be closed
    
   

}
