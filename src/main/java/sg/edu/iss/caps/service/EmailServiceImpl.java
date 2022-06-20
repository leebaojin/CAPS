package sg.edu.iss.caps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {
	
	@Autowired
    private JavaMailSender emailSender;
	
	public void sendResetPWMessage(String sendTo, String name, String address) {

		        SimpleMailMessage message = new SimpleMailMessage(); 
		        message.setFrom("capssystem4@gmail.com");
		        message.setTo(sendTo); 
		        message.setSubject("Password Reset Request"); 
		        
		        String msg = "Hi " + name + ",\nClick this link to reset your password:\n" +
		        		address + "\nIgnore this mail if you did not make this request.\nRegards,\nCAPS team";
		        
		        message.setText(msg);
		        emailSender.send(message);
		        
	}

	@Override
	public void SendCourseRegisteredEmail(String sendTo, String name, String courseCode) {
		// Send email to user for registered course
		
		SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("capssystem4@gmail.com");
        message.setTo(sendTo); 
        message.setSubject("Successful Registration for course: " + courseCode); 
        
        String msg = "Hi " + name + ",\n\tThis is to notify you that you have successfully enrolled for: " +
        		courseCode+ "Please login to CAPS system to view your courses." + "\n" + "\nRegards,\nCAPS team";
        
        message.setText(msg);
        emailSender.send(message);
		
	}
}
