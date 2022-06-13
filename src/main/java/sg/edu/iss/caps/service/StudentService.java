package sg.edu.iss.caps.service;

import java.util.List;

import sg.edu.iss.caps.model.Student;

public interface StudentService {
	
	List<Student> findAllStudents();
	
	Student findStudent(Integer sId);

}
