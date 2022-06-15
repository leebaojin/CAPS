package sg.edu.iss.caps.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.caps.model.CourseStudent;

public interface CourseStudentRepository extends JpaRepository<CourseStudent, Integer> {

}
