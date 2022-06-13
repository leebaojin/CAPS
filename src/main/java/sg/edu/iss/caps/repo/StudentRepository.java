package sg.edu.iss.caps.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.caps.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	
	public List<Student> findByUsername(String username);
}
