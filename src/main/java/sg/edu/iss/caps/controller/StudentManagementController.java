package sg.edu.iss.caps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sg.edu.iss.caps.model.Student;
import sg.edu.iss.caps.repo.StudentRepository;
import sg.edu.iss.caps.util.HeaderUtil;

import java.util.List;

@Controller
@RequestMapping("/student-management")
public class StudentManagementController {

    @Autowired
    StudentRepository studentRepo;

    @GetMapping("/create")
    public String createStudentPage(Model model) {
        return "create-student";
    }

    @GetMapping("/list")
    public String listStudents (Model model) {
        return "list-students";
    }

    @GetMapping("/list-name/{name}")
    public String listStudentsByName (Model model, @PathVariable("name") String name) {
        List<Student> studentList = studentRepo.findStudentByFirstName(name);
        model.addAttribute("allStudentList", studentList);
        return "list-students";
    }

    @PostMapping("/save")
    public String saveStudent (@ModelAttribute("student") Student s) {
        studentRepo.save(s);
        return "forward:/student-management/list";
    }

    // clarify purpose of editStudentPage
    @GetMapping("/edit-page")
    public String editStudentPage (Model model) {
        return "edit-student";
    }

    // clarify difference between this and editStudentPage
    @GetMapping("/edit/{id}")
    public String editStudent (Model model, @PathVariable("id") Integer id) {
        model.addAttribute("student", studentRepo.findById(id).get());
        return "edit-student";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent (Model model, @PathVariable("id") Integer id) {
        Student s = studentRepo.findById(id).get();
        studentRepo.delete(s);
        return "forward:/student-management/list";
    }



}
