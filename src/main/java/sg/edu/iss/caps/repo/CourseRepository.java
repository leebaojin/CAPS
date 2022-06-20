package sg.edu.iss.caps.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.caps.model.Course;
import sg.edu.iss.caps.model.Student;

public interface CourseRepository extends JpaRepository<Course, String> {

	@Query("SELECT c FROM Course c WHERE c NOT IN(SELECT c FROM Course c  JOIN c.courseStudents cs WHERE cs.student = :s)")
	public List<Course> findCourseNotTaken(Student s);
	
	@Query("SELECT c FROM Course c WHERE c NOT IN(SELECT c FROM Course c  JOIN c.courseStudents cs WHERE cs.student = :s) "
			+ "AND (c.courseCode LIKE CONCAT('%',:searchStr,'%') or c.courseTitle LIKE CONCAT('%',:searchStr,'%'))")
	public List<Course> findCourseNotTakenSearch(Student s, String searchStr);

	@Query("SELECT c FROM Course c WHERE c IN(SELECT c FROM Course c  JOIN c.courseStudents cs WHERE cs.student = :s)")
	public List<Course> findCourseTaken(Student s);
	
	@Query("Select c from Course c where course_code = :courseCode")
	Course findCourseCode(@Param("courseCode") String courseCode);
	
//	@Query("SELECT c FROM Course c WHERE c IN(SELECT c FROM Course c  JOIN c.courseStudents cs WHERE cs.student = :s AND cs.courseStudentStatus IN ('COMPLETED','FAILED'))")
//	public List<Course> findGrades(Student s);
}
