package sg.edu.iss.caps.service;

public interface EmailService {
	public void sendResetPWMessage(String sendTo, String name, String address);
}
