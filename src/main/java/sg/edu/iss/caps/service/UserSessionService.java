package sg.edu.iss.caps.service;

import javax.servlet.http.HttpSession;

import sg.edu.iss.caps.model.User;

public class UserSessionService {
	
	private static final String sessionIdentity = "loginUser";
	
	public static User findUser(HttpSession session) {
		User user = null;
		try{
			//Try to find and cast the into user
			user = (User) session.getAttribute(sessionIdentity);
		}
		catch(ClassCastException e){
			//Fail to cast. Object is not a user.
			return null;
		}
		return user;
	}
	
	public static void setUser(HttpSession session, User user) {
		session.setAttribute(sessionIdentity,user);
	}
	
	public static void removeSession(HttpSession session) {
		session.removeAttribute(sessionIdentity);
	}
}
