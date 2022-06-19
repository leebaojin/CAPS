package sg.edu.iss.caps.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import sg.edu.iss.caps.model.Student;
import sg.edu.iss.caps.repo.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Resource
	StudentRepository srepo;

	@Override
	public List<Student> findAllStudents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Student findStudent(Integer sId) {
		// TODO Auto-generated method stub
		return null;
	}
}
