package sg.edu.iss.caps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.caps.model.Lecturer;
import sg.edu.iss.caps.repo.LecturerRepository;

@Service
public class LecturerServiceImpl implements LecturerService {
	
	@Autowired
	LecturerRepository lecturerRepo;

	@Override
	public List<Lecturer> findAllLecturers() {
		//Find all lecturers
		return lecturerRepo.findAll();
	}

	@Override
	public Lecturer findLecturerById(Integer lectId) {
		// Find lecturer by Id
		if(lectId == null) {
			return null;
		}
		return lecturerRepo.findById(lectId).get();
	}

}
