package sg.edu.iss.caps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import sg.edu.iss.caps.model.Role;
import sg.edu.iss.caps.model.Student;
import sg.edu.iss.caps.model.UserStatus;
import sg.edu.iss.caps.repo.StudentRepository;
import sg.edu.iss.caps.util.HashUtil;

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
    	
    	//Creating a new Student Code Block
    	if(s.getStudentId() != null)
    	{
    		//For Editing the existing Student
    		Student newStudent = studentRepo.findById(s.getStudentId()).get();
    		newStudent.setFirstName(s.getFirstName());
    		newStudent.setLastName(s.getLastName());
    		newStudent.setUsername(s.getUsername());
    		newStudent.setEmail(s.getEmail());
//    		newStudent.setEnrolledDate(s.getEnrolledDate());
    		studentRepo.save(newStudent);
    	}
    	else 
    	{
    		//For Adding a new student
			s.setRole(Role.STUDENT);
			s.setUserStatus(UserStatus.ACTIVE);
			String defaultPwd = "123456";
			s.setPasswordHash(HashUtil.getHash(s.getUsername(),defaultPwd));
			studentRepo.save(s);
		}
        return "forward:/student-management/liststudents";
    }

    @RequestMapping("/liststudents")
    public String listStudents(Model model) {
        List<Student> studentList = studentRepo.findAllActiveStudents();
        model.addAttribute("studentList", studentList);
        return "list-students";
    }

    @GetMapping("/{name}")
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
        if(s.getStudentId() != null)
        {
        	s.setUserStatus(UserStatus.INACTIVE);
        	studentRepo.save(s);
        }
        return "forward:/student-management/liststudents";
    }
}
