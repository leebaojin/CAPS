package sg.edu.iss.caps.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.caps.model.Account;
import sg.edu.iss.caps.model.ChangePWRequest;
import sg.edu.iss.caps.model.Lecturer;
import sg.edu.iss.caps.model.Student;
import sg.edu.iss.caps.model.User;
import sg.edu.iss.caps.repo.AdministratorRepository;
import sg.edu.iss.caps.repo.ChangePWRequestRepository;
import sg.edu.iss.caps.repo.LecturerRepository;
import sg.edu.iss.caps.repo.StudentRepository;
import sg.edu.iss.caps.util.HashUtil;

@Service
public class AccountAuthenticationServiceImpl implements AccountAuthenticationService {
	
	@Autowired
	private StudentRepository stuRepo;
	
	@Autowired
	private AdministratorRepository adminRepo;
	
	@Autowired
	private LecturerRepository lecRepo;
	
	@Autowired
	private ChangePWRequestRepository chpwRepo;
	
	@Autowired
	private EmailService emailService;

	@Override
	public User authenticateAccount(Account account) {
		// To receive an account and return the user of that account
		// Return null if no such user exist
		if(account.getUsername().trim() == "" || account.getPassword().trim() == "") {
			//No username or password
			return null;
		}
		User user = null;
		byte[] passwordHash = HashUtil.getHash(account.getUsername(), account.getPassword());
		
		//Check which domain was selected and find if the username exist
		if(account.getDomain().equalsIgnoreCase("LECTURER")) {
			user = lecRepo.findFirstByUsername(account.getUsername());
		}
		else if(account.getDomain().equalsIgnoreCase("STUDENT")) {
			user = stuRepo.findFirstByUsername(account.getUsername());
		}
		else if(account.getDomain().equalsIgnoreCase("ADMIN")) {
			user = adminRepo.findFirstByUsername(account.getUsername());
		}
		
		if(user == null) {
			//User not found - to implement exception?
			return null;
		}
		
		if(Arrays.equals(user.getPasswordHash(), passwordHash)) {
			//Return the user if password matches
			return user;
		}
		return null;
	}

	@Override
	public Student isStudent(User user) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public  User findUserByEmail(String email) {
		//Find user by email
		User user = stuRepo.findFirstByEmail(email);
		if(user == null) {
			user = lecRepo.findFirstByEmail(email);
		}
		return user;
	}
	
	@Override
	public void sendPasswordResetEmail(User user, String hostUrl) {
		
		UUID uuid = UUID.randomUUID();
		//To clear old requests
		if(!updatePasswordResetRequest(user, uuid)) {
			//Unable to create a request
			return;
		}
		String resetAddress = hostUrl + "login/passwordreset?resetId=" + uuid.toString();
		emailService.sendResetPWMessage(user.getEmail(), user.getFirstName() + user.getLastName(), resetAddress);
	}
	
	@Override
	public boolean updatePasswordResetRequest(User user, UUID uuid) {
		Long expiredTime = Instant.now().getEpochSecond() + 3600; //Expire after 1 hour
		ChangePWRequest newRequest = null;
		List<ChangePWRequest> requestlist = new ArrayList<>();
		//Find any old request present in the system
		if(user instanceof Lecturer) {
			Lecturer lecturer = (Lecturer) user;
			requestlist = chpwRepo.findByLecturerId(lecturer.getLecturerId());	
			if(!requestlist.isEmpty()) {
				//Delete any old request present in the system
				chpwRepo.deleteAll(requestlist);
			}
			//Create a new request
			if(uuid != null) {
				newRequest = new ChangePWRequest(uuid,expiredTime,lecturer);
			}
		}
		else if(user instanceof Student) {
			Student student = (Student) user;
			requestlist = chpwRepo.findByStudentId(student.getStudentId());
			if(!requestlist.isEmpty()) {
				//Delete any old request present in the system
				chpwRepo.deleteAll(requestlist);
			}	
			//Create a new request
			if(uuid != null) {
				newRequest = new ChangePWRequest(uuid,expiredTime,student);
			}
		}
		else {
			return false;
		}
		if(newRequest !=null) {
			//Succeed in creating new request
			chpwRepo.saveAndFlush(newRequest);
			return true;
		}
		return false;
	}
	
	@Override
	public ChangePWRequest findPasswordResetRequestById(String uuidStr) {
		//This searches for the password request made
		UUID uuid = UUID.fromString(uuidStr);
		ChangePWRequest chPwRequest = null;
		try {
			chPwRequest= chpwRepo.findById(uuid).get();
			if(chPwRequest.getExpiredTime() < Instant.now().getEpochSecond()) {
				//Delete request if already expired
				chpwRepo.delete(chPwRequest);
				return null;
			}
		} catch(NoSuchElementException e) {
			//Nothing was found
			return null;
		}
		
		return chPwRequest;
	}
	
	@Override
	public void changeNewPassword(String uuidStr, String newPW) {
		ChangePWRequest chPwRequest = findPasswordResetRequestById(uuidStr);
		
		if(chPwRequest.getLecturer() != null) {
			Lecturer lecturer = chPwRequest.getLecturer();
			byte[] passwordHash = HashUtil.getHash(lecturer.getUsername(), newPW);
			lecturer.setPasswordHash(passwordHash);
			lecRepo.save(lecturer);
		}
		else if(chPwRequest.getStudent() != null) {
			Student student = chPwRequest.getStudent();
			byte[] passwordHash = HashUtil.getHash(student.getUsername(), newPW);
			student.setPasswordHash(passwordHash);
			stuRepo.save(student);
		}
		else {
			return;
		}
		
		//Clean up the request
		chpwRepo.delete(chPwRequest);
	}

}
