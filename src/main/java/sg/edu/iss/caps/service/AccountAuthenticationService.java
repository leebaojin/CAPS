package sg.edu.iss.caps.service;


import java.util.UUID;

import sg.edu.iss.caps.model.Account;
import sg.edu.iss.caps.model.ChangePWRequest;
import sg.edu.iss.caps.model.Student;
import sg.edu.iss.caps.model.User;

public interface AccountAuthenticationService {
	
	public User authenticateAccount(Account account);
	public User findUserByEmail(String email);
	public Student isStudent(User user);
	public void sendPasswordResetEmail(User user, String hostUrl);
	public boolean updatePasswordResetRequest(User user, UUID uuid);
	public ChangePWRequest findPasswordResetRequestById(String uuidStr);
	public void changeNewPassword(String uuidStr, String newPW);
	
	
}
