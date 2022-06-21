package sg.edu.iss.caps.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.caps.model.Student;
import sg.edu.iss.caps.model.User;
import sg.edu.iss.caps.service.StudentService;
import sg.edu.iss.caps.service.UserSessionService;
import sg.edu.iss.caps.util.MenuNavBarUtil;

@Controller
@RequestMapping("/manage/student")
public class AdminManageStudentController {

//    @Autowired
//    StudentRepository studentRepo;
    
    @Autowired
    StudentService stuService;

    @GetMapping("/create")
    public String createStudentPage(HttpSession session, Model model) {
    	User user = UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
    	
        Student s = new Student();
        model.addAttribute("student",s);
        return "student-form";
    }

    @GetMapping("/save")
    public String saveStudent(@ModelAttribute("student") @Valid Student s, BindingResult bindingResult, HttpSession session, Model model) {
    	User user = UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
    	if (bindingResult.hasErrors()) 
		{
			return "student-form";
		}
    	
    	if(s.getStudentId() != null)
    	{
    		stuService.editStudent(s);
    	}
    	else 
    	{
    		stuService.createStudent(s);
		}
        return "forward:/manage/student/view";
    }

    @GetMapping("/view")
    public String listStudents(HttpSession session, Model model) {
    	User user = UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
    	
    	List<Student> studentActiveList = stuService.findAllActiveStudents();
        model.addAttribute("studentList", studentActiveList);
        return "list-students";
    }

    @GetMapping("/view/{name}")
    public String listStudentsByName(Model model, @PathVariable("name") String name, HttpSession session) {
    	User user = UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
    	
        List<Student> studentListByName = stuService.findAllStudentsByName(name);
        model.addAttribute("studentList", studentListByName);
        return "list-students";
    }

    @GetMapping("/edit/{id}")
    public String editStudent(Model model, @PathVariable("id") Integer id, HttpSession session) {
    	User user = UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
    	
    	Student studentById = stuService.findStudentById(id);
        model.addAttribute("student", studentById);
        return "student-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(Model model, @PathVariable("id") Integer id, HttpSession session) {
    	User user = UserSessionService.findUser(session);
    	MenuNavBarUtil.generateNavBar(user, model);
    	
        stuService.deleteStudent(id);
        return "forward:/manage/student/view";
    }
}
