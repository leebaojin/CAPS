package sg.edu.iss.caps.service;

import java.util.List;

import sg.edu.iss.caps.model.Lecturer;

public interface LecturerService {
	
	public List<Lecturer> findAllLecturers();
	
	public Lecturer findLecturerById(Integer lectId);
	
}
