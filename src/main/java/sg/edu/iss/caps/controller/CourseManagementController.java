package sg.edu.iss.caps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sg.edu.iss.caps.model.*;
import sg.edu.iss.caps.repo.CourseRepository;

import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseManagementController {

    @Autowired
    CourseRepository courseRepo;

    @GetMapping("/create")
    public String createCoursePage(Model model) {
        Course c = new Course();
        model.addAttribute("course", c);
        return "course-form";
    }

    @RequestMapping("/list")
    public String listCourses(Model model) {
        List<Course> courseList = courseRepo.findAll();
        model.addAttribute("courseList", courseList);
        return "list-courses";
    }

    @PostMapping("/save")
    // validation tbc
    public String saveCourse(@ModelAttribute("course") Course c) {
        // if course present, get course object, otherwise create new course
        Course c2 = courseRepo.findById(c.getCourseCode()).isPresent() ?
                courseRepo.findById(c.getCourseCode()).get(): new Course();

            c2.setCourseCode(c.getCourseCode());
            c2.setCourseTitle(c.getCourseTitle());
            c2.setCourseDescription(c.getCourseDescription());
            c2.setCourseCredits(c.getCourseCredits());
            c2.setCourseCapacity(c.getCourseCapacity());
            c2.setCourseStatus(c.getCourseStatus());
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
    public String editCourse(Model model, @PathVariable("courseId") String courseId) {
        Course c = courseRepo.findById(courseId).get();
        model.addAttribute("course", c);
        return "course-form";
    }

    @GetMapping("/delete/{courseId}")
    public String deleteCourse(Model model, @PathVariable("courseId") String courseId) {
        Course c = courseRepo.findById(courseId).get();
        courseRepo.delete(c);
        return "forward:/course/list";
    }

    // can add validation for the form
    @GetMapping("/{id}/lecturers")
    public String listCourseLecturers(Model model, @PathVariable("id") String id) {
        List<Lecturer> lecturerList = courseRepo.findById(id).get().getCourseLecturers();
        model.addAttribute("lecturers", lecturerList);
        return "/lecturers";
    }

    @PostMapping("/{id}/add-lecturer")
    public String addCourseLecturer(Model model, @PathVariable("id") String id, @ModelAttribute("lecturer") Lecturer l) {
        Course c = courseRepo.findById(id).get();
        c.getCourseLecturers().add(l);
        courseRepo.save(c);
        return "forward:/course/{id}/lecturer";
    }

    @GetMapping("/{id}/remove-lecturer")
    public String removeCourseLecturer(Model model, @PathVariable("id") Integer id) {
        return "forward:/course/{id}/lecturer";
    }

}
