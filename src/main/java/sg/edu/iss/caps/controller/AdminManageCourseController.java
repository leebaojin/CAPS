package sg.edu.iss.caps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sg.edu.iss.caps.model.*;
import sg.edu.iss.caps.repo.CourseRepository;
import sg.edu.iss.caps.repo.LecturerRepository;
import sg.edu.iss.caps.service.UserSessionService;
import sg.edu.iss.caps.util.MenuNavBarUtil;

import java.util.List;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/course")
public class AdminManageCourseController {

    @Autowired
    CourseRepository courseRepo;

    @Autowired
    LecturerRepository lecturerRepo;

    @GetMapping("/create")
    public String createCoursePage(HttpSession session, Model model) {
    	User user = UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
    	
        Course c = new Course();
        List<Lecturer> lecturerList = lecturerRepo.findAll();
        model.addAttribute("course", c);
        model.addAttribute("lecturerList", lecturerList);
        return "course-form";
    }

    @RequestMapping("/list")
    public String listCourses(HttpSession session, Model model) {
    	User user = UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
    	
        List<Course> courseList = courseRepo.findAll();
        List<Lecturer> lecturerList = lecturerRepo.findAll();
        model.addAttribute("courseList", courseList);
        model.addAttribute("lecturerList", lecturerList);
        return "list-courses";
    }

    @PostMapping("/save")
    // validation tbc: courseCode
    public String saveCourse(@ModelAttribute("course") Course c, HttpSession session) {
        // if course present, get course object, otherwise create new course
    	
    	User user = UserSessionService.findUser(session);
    	
        Course c2 = courseRepo.findById(c.getCourseCode()).isPresent() ?
                courseRepo.findById(c.getCourseCode()).get(): new Course();

            c2.setCourseCode(c.getCourseCode());
            c2.setCourseTitle(c.getCourseTitle());
            c2.setCourseDescription(c.getCourseDescription());
            c2.setCourseCredits(c.getCourseCredits());
            c2.setCourseCapacity(c.getCourseCapacity());
            c2.setCourseStatus(c.getCourseStatus());
            c2.setCourseLecturers(c.getCourseLecturers());
            courseRepo.save(c2);
        return "forward:/course/list";
    }

//    // clarify purpose of editCoursePage
//    @GetMapping("/edit-page")
//    public String editCoursePage(Model model) {
//        return "edit-course";
//    }

    // clarify difference between this and editCoursePage
    @GetMapping("/edit/{courseId}")
    public String editCourse(Model model, @PathVariable("courseId") String courseId, HttpSession session) {
    	User user = UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
    	
        Course c = courseRepo.findById(courseId).get();
        List<Lecturer> lecturerList = lecturerRepo.findAll();
        model.addAttribute("course", c);
        model.addAttribute("lecturerList", lecturerList);
        return "course-form";
    }

    @GetMapping("/delete/{courseId}")
    public String deleteCourse(Model model, @PathVariable("courseId") String courseId, HttpSession session) {
    	User user = UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
    	
        Course c = courseRepo.findById(courseId).get();
        courseRepo.delete(c);
        return "forward:/course/list";
    }

    // can add validation for the form
//    @GetMapping("/{id}/lecturers")
//    public String listCourseLecturers(Model model, @PathVariable("id") String id) {
//        Course c = courseRepo.findById(id).get();
//        List<Lecturer> lecturerList = courseRepo.findById(id).get().getCourseLecturers();
//        model.addAttribute("lecturers", lecturerList);
//        return "course-info";
//    }

    @PostMapping("/{id}/add-lecturer")
    public String addCourseLecturer(Model model, @PathVariable("id") String id, @ModelAttribute("lecturer") Lecturer l, HttpSession session) {
    	User user = UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
    	
        Course c = courseRepo.findById(id).get();
        c.getCourseLecturers().add(l);
        courseRepo.save(c);
        return "forward:/course/{id}/lecturer";
    }

    @GetMapping("/{id}/remove-lecturer")
    public String removeCourseLecturer(Model model, @PathVariable("id") Integer id, HttpSession session) {
    	User user = UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
        return "forward:/course/{id}/lecturer";
    }

}
