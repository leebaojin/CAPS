package sg.edu.iss.caps.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.caps.model.Lecturer;

public interface LecturerRepository extends JpaRepository<Lecturer, Integer> {

}
