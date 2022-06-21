package sg.edu.iss.caps.service;

import java.util.List;

import sg.edu.iss.caps.model.Lecturer;

public interface LecturerService {
	
	public void saveLecturer(Lecturer l); 
	public void saveEditLecturer(Lecturer l); 
	public List<Lecturer> findAllActiveLecturers(); 
	public void deleteLecturer(Lecturer l);
	public List<Lecturer> findAllLecturers();
	public Lecturer findLecturerById(Integer lectId);
	
//	public Lecturer findLecturerById(Integer lecturerId);
//	public List<Lecturer> findAllLecturers();
}
