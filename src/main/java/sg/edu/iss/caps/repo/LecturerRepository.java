package sg.edu.iss.caps.repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.iss.caps.model.Lecturer;


public interface LecturerRepository extends JpaRepository<Lecturer, Integer> {

	Lecturer findFirstByUsername(String username);
	Lecturer findFirstByEmail(String email);
	
	@Query("Select l from Lecturer l where user_status = 'ACTIVE'")
	ArrayList<Lecturer>findAllActiveLecturers();
}
