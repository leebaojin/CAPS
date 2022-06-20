package sg.edu.iss.caps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.caps.model.Course;
import sg.edu.iss.caps.model.CourseStudent;
import sg.edu.iss.caps.model.Student;
import sg.edu.iss.caps.repo.CourseRepository;
import sg.edu.iss.caps.repo.CourseStudentRepository;

@Service
public class StudentCourseServiceImpl implements StudentCourseService {
	
	@Autowired
	CourseStudentRepository courseStudentRepo;
	
	@Autowired
	CourseRepository courseRepo;
	
	@Override
	public CourseStudent CreateNewCourseStudent(Student student, String courseCode) {
		//To validate the course code
		//To check that the student has not registered for the course
		Course course = courseRepo.findById(courseCode).get();
		if(course == null) {
			return null;
		}
    	List<CourseStudent> courseStudentlist = courseStudentRepo.findCSByStudentAndCourse(student, course);
		
    	if(courseStudentlist.size() > 0) {
    		//Cannot register if course already present
			return null;
    	}
    	
    	CourseStudent cs = new CourseStudent(student,course);
    	try {
    		courseStudentRepo.saveAndFlush(cs);
    	}
    	catch(Exception e) {
    		//Failed to save repo
    		
    		return null;
    	}
    	
		return cs;
	}
	
	@Override
	public List<Course> findEnrolledCourse(Student s) {
		// Find the courses that the student have taken
		List<Course> courseTaken = courseRepo.findCourseTaken(s);
		return courseTaken;
	}
	
	
	@Override
	public List<Course> findStudentGrades(Student s){
		//List<Course> courseGrade = courseRepo.findGrades(s);
		return null;
	}
	
}
