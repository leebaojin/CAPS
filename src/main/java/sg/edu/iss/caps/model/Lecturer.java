package sg.edu.iss.caps.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Lecturer {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer lecturerId;
	private String firstname;
	private String lastname;
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="ACCOUNT_ID")
	private Account account;
	
	@ManyToMany(mappedBy="courseLecturers", fetch = FetchType.EAGER)
	private List<Course> teachCourses;

	public Lecturer(String firstname, String lastname) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public Lecturer(String firstname, String lastname, Account account) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.account = account;
	}
	
}
