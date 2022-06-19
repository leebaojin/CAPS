package sg.edu.iss.caps.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.caps.model.Course;
import sg.edu.iss.caps.model.CourseStatus;
import sg.edu.iss.caps.model.Student;
import sg.edu.iss.caps.repo.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	CourseRepository courseRepo;
	
	@Override
	public List<Course> findAvailableCourseForStudent(Student s) {
		// Find the courses that the student have not taken
		List<Course> courseNotTaken = courseRepo.findCourseNotTaken(s);
		//Filter out the full courses
    	List<Course> courseAvailable = getCourseAvailable(courseNotTaken);
		return courseAvailable;
	}

	@Override
	public List<Course> findSearchCourseForStudent(Student s, String searchStr) {
		// Find the courses that the student have not taken that meets the search string
		List<Course> courseNotTaken = courseRepo.findCourseNotTakenSearch(s, searchStr);
		//Filter out the full courses
    	List<Course> courseAvailable = getCourseAvailable(courseNotTaken);
		return courseAvailable;
	}
	
	private List<Course> getCourseAvailable(List<Course> courses){
		//Filter away full courses and those that are closed
		return courses.stream()
					.filter(c -> (c.getCourseStudents().size() < c.getCourseCapacity()) &&
						c.getCourseStatus() == CourseStatus.OPEN)
					.collect(Collectors.toList());
	}

}
