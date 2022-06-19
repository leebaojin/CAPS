package sg.edu.iss.caps.service;

import java.util.List;

import sg.edu.iss.caps.model.Course;
import sg.edu.iss.caps.model.Student;

public interface CourseService {
	
	public List<Course> findAvailableCourseForStudent(Student s);

}
