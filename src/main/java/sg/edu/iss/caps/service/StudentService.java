package sg.edu.iss.caps.service;

import java.util.List;

import sg.edu.iss.caps.model.Student;
import sg.edu.iss.caps.repo.StudentRepository;

public interface StudentService {
	
	List<Student> findAllStudents();
	
	Student findStudent(Integer sId);
}
