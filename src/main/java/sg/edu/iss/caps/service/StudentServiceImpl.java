package sg.edu.iss.caps.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import sg.edu.iss.caps.model.Role;
import sg.edu.iss.caps.model.Student;
import sg.edu.iss.caps.model.UserStatus;
import sg.edu.iss.caps.repo.StudentRepository;
import sg.edu.iss.caps.util.HashUtil;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Resource
	StudentRepository studentRepo;

	@Override
	public List<Student> findAllActiveStudents() {
		List<Student> studentList = studentRepo.findAllActiveStudents();
		return studentList;
	}
	
	@Override
	public List<Student> findAllStudentsByName(String name) {
		List<Student> studentListByName = studentRepo.findStudentByFirstName(name);
		return studentListByName;
	}

	@Override
	public Student findStudentById(Integer id) {
		Student studentById = studentRepo.findById(id).get();
		return studentById;
	}
	
	@Override
	public void editStudent(Student s) {
		Student newStudent = studentRepo.findById(s.getStudentId()).get();
		newStudent.setFirstName(s.getFirstName());
		newStudent.setLastName(s.getLastName());
		newStudent.setUsername(s.getUsername());
		newStudent.setEmail(s.getEmail());
		studentRepo.save(newStudent);
	}

	@Override
	public void createStudent(Student s) {
		//Setting Date 
		Date date = new Date();
	    String strDateFormat = "dd-MM-yyyy";
	    DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
	    String formattedDate= dateFormat.format(date);
		try {
			s.setEnrolledDate(dateFormat.parse(formattedDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		s.setRole(Role.STUDENT);
		s.setUserStatus(UserStatus.ACTIVE);
		String defaultPwd = "123456";
		s.setPasswordHash(HashUtil.getHash(s.getUsername(),defaultPwd));
		studentRepo.save(s);
	}

	@Override
	public void deleteStudent(Integer id) {
		Student s = studentRepo.findById(id).get();
        if(s.getStudentId() != null)
        {
        	s.setUserStatus(UserStatus.INACTIVE);
        	studentRepo.save(s);
        }
	}



}
