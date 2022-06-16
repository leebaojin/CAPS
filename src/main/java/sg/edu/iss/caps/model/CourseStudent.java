package sg.edu.iss.caps.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class CourseStudent {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer courseStudentId;
	@Enumerated(EnumType.STRING)
	private Grade grade;
	private String status;
	@ManyToOne
	private Student student;
	@ManyToOne
	private Course course;
	public CourseStudent(String status, Student student, Course course) {
		super();
		this.status = status;
		this.student = student;
		this.course = course;
	}
	
	
}
