package sg.edu.iss.caps.service;

import java.util.List;

import sg.edu.iss.caps.model.Student;

public interface StudentService {
	
	public List<Student> findAllStudents();
	
	public Student findStudent(Integer sId);

}
