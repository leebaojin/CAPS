package sg.edu.iss.caps.service;

import sg.edu.iss.caps.model.CourseStudent;
import sg.edu.iss.caps.model.Student;

public interface CourseStudentService {
	
	public CourseStudent CreateNewCourseStudent(Student student, String courseCode);
	
}
