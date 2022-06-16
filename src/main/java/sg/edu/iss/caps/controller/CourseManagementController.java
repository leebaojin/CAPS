package sg.edu.iss.caps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sg.edu.iss.caps.model.Course;
import sg.edu.iss.caps.model.Lecturer;
import sg.edu.iss.caps.repo.CourseRepository;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseManagementController {

    @Autowired
    CourseRepository courseRepo;

    @GetMapping("/create")
    public String createCoursePage(Model model) {
        Course c = new Course();
        model.addAttribute("course", c);
        return "create-course";
    }

    @GetMapping("/list")
    public String listCourses(Model model) {
        List<Course> courseList = courseRepo.findAll();
        model.addAttribute("courseList", courseList);
        return "list-courses";
    }

    @PostMapping("/save")
    public String saveCourse(@ModelAttribute("student") Course c) {
        courseRepo.save(c);
        return "forward:/courses/list";
    }

    // clarify purpose of editCoursePage
    @GetMapping("/edit-page")
    public String editSCoursePage(Model model) {
        return "edit-course";
    }

    // clarify difference between this and editCoursePage
    @GetMapping("/edit/{id}")
    public String editCourse(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("course", courseRepo.findById(id).get());
        return "edit-course";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(Model model, @PathVariable("id") Integer id) {
        Course c = courseRepo.findById(id).get();
        courseRepo.delete(c);
        return "forward:/courses/list";
    }

    @GetMapping("/{id}/lecturers")
    public String listCourseLecturers(Model model, @PathVariable("id") Integer id) {
        List<Lecturer> lecturerList = courseRepo.findById(id).get().getCourseLecturers();
        model.addAttribute("lecturers", lecturerList);
        return "/lecturers";
    }

    @PostMapping("/{id}/add-lecturer")
    public String addCourseLecturer(Model model, @PathVariable("id") Integer id, @ModelAttribute("lecturer") Lecturer l) {
        Course c = courseRepo.findById(id).get();
        c.getCourseLecturers().add(l);
        courseRepo.save(c);
        return "forward:/courses/{id}/lecturer";
    }

    @GetMapping("/{id}/remove-lecturer")
    public String removeCourseLecturer(Model model, @PathVariable("id") Integer id) {
        return "forward:/courses/{id}/lecturer";
    }

}
