package sg.edu.iss.caps.repotest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import sg.edu.iss.caps.model.Course;
import sg.edu.iss.caps.model.CourseStatus;
import sg.edu.iss.caps.model.Lecturer;
import sg.edu.iss.caps.model.Role;
import sg.edu.iss.caps.repo.CourseRepository;
import sg.edu.iss.caps.util.HashUtil;

public class CourseRepositoryTest {

	@Autowired
	CourseRepository courseRepo;

//	@Test
//	@Order(1)
//	public void testCreateCouse() {
//		Course c = new Course("UT9999", "Creating Fun Unit Tests" ,"Learn to make fun and innovative unit tests!", "10", 10, CourseStatus.OPEN);
//		courseRepo.saveAndFlush(c);
//		Assertions.assertNotNull(c);
//	}
//
//	@Test
//	@Order(2)
//	public void testUpdateCourse() {
//		Course result = courseRepo.findCourseCode("UT9999");
//		System.out.println("Before update: Title=" + result.getCourseTitle() + " Description=" + result.getCourseDescription());
//		assertEquals(result.getCourseTitle(), "Creating Fun Unit Tests", "Result title does not match");
//		assertEquals(result.getCourseDescription(), "Learn to make fun and innovative unit tests!", "Result description does not match");
//		result.setCourseTitle("updateCourseTitle");
//		result.setCourseDescription("updateCourseDescription");
//		courseRepo.saveAndFlush(result);
//		System.out.println("After update: Title=" + result.getCourseTitle() + " Description= " + result.getCourseDescription());
//		assertNotEquals(result.getCourseTitle(), "Creating Fun Unit Tests", "Result title does not match");
//		assertNotEquals(result.getCourseDescription(), "Learn to make fun and innovative unit tests!", "Result description does not match");
//	}
	
//	@Test
//	@Order(3)
//	public void testDeleteLecturer() {
//		Lecturer result = lecturerRepo.findFirstByUsername("gohmm");
//		Assertions.assertNotNull(result);
//		System.out.println("Existing lecturer found:  LecturerId=" + result.getLecturerId()  + " " + result.getFirstName() + " " + result.getLastName());
//		lecturerRepo.delete(result);
//		lecturerRepo.flush();
//		result = lecturerRepo.findFirstByUsername("gohmm");
//		Assertions.assertNull(result);
//	}
//	
//	@Test
//	@Order(4)
//	public void testFindLecturer() {
//		Lecturer result = lecturerRepo.findById(10).get();
//		assertEquals(result.getFirstName(), "Tri Tin", "Result first name does not match");
//		assertEquals(result.getLastName(), "Nguyen", "Result last name does not match");
//		assertEquals(result.getUsername(), "tritin", "Result username does not match");
//	}

}
