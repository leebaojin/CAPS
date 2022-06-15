package sg.edu.iss.caps.model;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Account {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer accountId;
	private String username;
	private String password;
	//@Column(columnDefinition="BINARY(32) NOT NULL")
	//private byte[] passwordHash;
	@Enumerated(EnumType.STRING)
	private Role role;
	public Account(String username, String password, Role role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
}
