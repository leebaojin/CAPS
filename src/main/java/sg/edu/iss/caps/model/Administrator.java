package sg.edu.iss.caps.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class Administrator extends User{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer adminId;

	public Administrator(String username,  byte[] passwordHash, String firstname, String lastname, String email,
			Role role) {
		super(username, firstname, lastname, email, passwordHash, role);
	}

	

	
	
}
