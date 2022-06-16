package sg.edu.iss.caps.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.caps.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	
	@Query("SELECT s FROM Student s WHERE s.account.username=:un")
	public List<Student> findStudentByUsername(@Param("un") String username);

	@Query("SELECT s FROM Student s WHERE s.firstname=:fn")
	public List<Student> findStudentByFirstName(@Param("fn") String firstName);
}
