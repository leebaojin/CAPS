package sg.edu.iss.caps.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Administrator {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer adminId;
	private String firstname;
	private String lastname;
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	private Account account;
	public Administrator(String firstname, String lastname) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
	}
	public Administrator(String firstname, String lastname, Account account) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.account = account;
	}
	
	
}
