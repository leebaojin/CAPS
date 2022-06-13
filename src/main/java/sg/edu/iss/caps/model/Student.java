package sg.edu.iss.caps.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Student {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer studentId;
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private LocalDateTime enrolledDate;
	public Student(String firstname, String lastname, String username, String password, LocalDateTime enrolledDate) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.enrolledDate = enrolledDate;
	}
	
	
	
	
}
