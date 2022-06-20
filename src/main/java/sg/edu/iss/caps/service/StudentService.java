package sg.edu.iss.caps.service;

import java.util.List;

import sg.edu.iss.caps.model.Student;

public interface StudentService {
	void createStudent(Student s);
	
	List<Student> findAllActiveStudents();
	
	List<Student> findAllStudentsByName(String name);
	
	Student findStudentById(Integer id);

	void editStudent(Student s);
	
	void deleteStudent(Integer id);
}
