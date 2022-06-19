package sg.edu.iss.caps.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.iss.caps.model.Course;
import sg.edu.iss.caps.model.Student;

public interface CourseRepository extends JpaRepository<Course, String> {

	@Query("SELECT c FROM Course c JOIN c.courseStudents cs WHERE cs.student = :s")
	public List<Course> findCourseNotTaken(Student s);
}
