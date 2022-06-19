package sg.edu.iss.caps.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.caps.model.Course;

public interface CourseRepository extends JpaRepository<Course, String> {

}
