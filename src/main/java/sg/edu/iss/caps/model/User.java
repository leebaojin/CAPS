package sg.edu.iss.caps.model;


import javax.persistence.Column;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
public class User {

	private String username;
	private String firstName;
	private String lastName;
	private String email;
	//private String password;
	@Column(columnDefinition="BINARY(32) NOT NULL")
	private byte[] passwordHash;
	@Enumerated(EnumType.STRING)
	private Role role;
	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;
	
	public User(String username, String firstName, String lastName, String email, byte[] passwordHash, Role role) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.passwordHash = passwordHash;
		this.role = role;
	}
	
	
	
}
