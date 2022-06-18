package sg.edu.iss.caps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sg.edu.iss.caps.model.Student;
import sg.edu.iss.caps.repo.StudentRepository;

import java.util.List;

import javax.validation.Valid;

@Controller
@RequestMapping("/student-management")
public class StudentManagementController {

    @Autowired
    StudentRepository studentRepo;

    @GetMapping("/create")
    public String createStudentPage(Model model) {
        Student s = new Student();
        model.addAttribute("student",s);
        return "student-form";
    }

    @PostMapping("/save")
    public String saveStudent(@ModelAttribute("student") @Valid Student s, BindingResult bindingResult, Model model) {
    	if (bindingResult.hasErrors()) 
		{
			return "student.studentId";
		}
    	studentRepo.save(s);
        return "forward:/student-management/liststudents";
    }

    @GetMapping("/liststudents")
    public String listStudents(Model model) {
        List<Student> studentList = studentRepo.findAll();
        model.addAttribute("studentList", studentList);
        return "list-students";
    }

    @GetMapping("/liststudents/{name}")
    public String listStudentsByName(Model model, @PathVariable("name") String name) {
        List<Student> studentList = studentRepo.findStudentByFirstName(name);
        model.addAttribute("studentList", studentList);
        return "list-students";
    }

    @GetMapping("/edit/{id}")
    public String editStudent(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("student", studentRepo.findById(id).get());
        return "student-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(Model model, @PathVariable("id") Integer id) {
        Student s = studentRepo.findById(id).get();
        studentRepo.delete(s);
        return "forward:/student-management/liststudents";
    }
}
