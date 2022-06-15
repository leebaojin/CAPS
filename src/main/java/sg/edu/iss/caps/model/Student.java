package sg.edu.iss.caps.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

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
	//@DateTimeFormat(pattern = "dd-mm-yyyy")
	//private LocalDateTime enrolledDate;
	@DateTimeFormat(pattern = "dd-mm-yyyy")
	private Date enrolledDate;
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	private Account account;
	@OneToMany(mappedBy="student",cascade=CascadeType.ALL)
	private List<CourseStudent> courseAttended;
	public Student(String firstname, String lastname, Date enrolledDate) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.enrolledDate = enrolledDate;
	}
	public Student(String firstname, String lastname, Date enrolledDate, Account account) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.enrolledDate = enrolledDate;
		this.account = account;
	}
	
	
	
	
	
}
