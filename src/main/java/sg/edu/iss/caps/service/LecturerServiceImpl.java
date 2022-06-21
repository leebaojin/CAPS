package sg.edu.iss.caps.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.caps.model.Lecturer;
import sg.edu.iss.caps.model.Role;
import sg.edu.iss.caps.model.UserStatus;
import sg.edu.iss.caps.repo.LecturerRepository;
import sg.edu.iss.caps.util.HashUtil;

@Service
public class LecturerServiceImpl implements LecturerService {
	
	@Autowired
	LecturerRepository lecturerRepo;

	@Transactional
	@Override
	public List<Lecturer> findAllLecturers() {
		//Find all lecturers
		return lecturerRepo.findAll();
	}

	@Transactional
	@Override
	public Lecturer findLecturerById(Integer lectId) {
		// Find lecturer by Id
		if(lectId == null) {
			return null;
		}
		return lecturerRepo.findById(lectId).get();
	}
	
	@Transactional
	@Override
	public void saveLecturer(Lecturer l) {
		l.setRole(Role.LECTURER);
		l.setUserStatus(UserStatus.ACTIVE);
		String defaultPwd = "123456";
		l.setPasswordHash(HashUtil.getHash(l.getUsername(),defaultPwd));
		lecturerRepo.save(l);
	}
	
	@Transactional
	@Override
	public void saveEditLecturer(Lecturer l) {
		Lecturer l2 = lecturerRepo.findById(l.getLecturerId() ).get();
		l2.setFirstName(l.getFirstName());
		l2.setLastName(l.getLastName());
		l2.setUsername( l.getUsername());
		l2.setEmail(l.getEmail());
		lecturerRepo.save(l2);
	}
	
	@Transactional
	@Override
	public List<Lecturer> findAllActiveLecturers() {
		List<Lecturer> lecturerList = lecturerRepo.findAllActiveLecturers();
		return lecturerList;
	}

	@Transactional
	@Override
	public void deleteLecturer(Lecturer l) {
		if (l.getLecturerId() != null) {
			l.setUserStatus(UserStatus.INACTIVE);
			lecturerRepo.save(l);
		}
	}

//	@Transactional
//	@Override
//	public Lecturer findLecturerById(Integer lecturerId) {
//		return lecturerRepo.findById(lecturerId).get();
//	}
	
//	@Transactional
//	@Override
//	public List<Lecturer> findAllLecturers(){
//		List<Lecturer> lecturerList = lecturerRepo.findAll();
//		return lecturerList;
//	}
	
}
