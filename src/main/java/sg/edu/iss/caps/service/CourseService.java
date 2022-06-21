package sg.edu.iss.caps.service;

import java.util.List;

import org.springframework.data.domain.Page;

import sg.edu.iss.caps.model.Course;
import sg.edu.iss.caps.model.Lecturer;
import sg.edu.iss.caps.model.Student;

public interface CourseService {
	
	public List<Course> findAvailableCourseForStudent(Student s);
	
	public List<Course> findSearchCourseForStudent (Student s, String searchStr);
	
	public List<Course> findAllCourses();
	
	public Page<Course> findAllCoursesSortPage(int page, int view);
	
	public Course findCourseById(String courseId);
	
	public void SaveCourse(Course c);
	
	public void SaveCourseAddLecturer(Course c, Lecturer l);
	
	public void AddLecturerToCourse(String courseId, Lecturer l);
	
	public Course findCourseByCourseCode(String courseCode); 

}
