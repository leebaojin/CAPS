package sg.edu.iss.caps.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Course {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer courseId;
	private String courseTitle;
	private String courseDescription;
	private String courseCredits;
	private Integer courseCapacity;
	private String courseStatus;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="CourseLecturer")
	private List<Lecturer> courseLecturers;
	
	@OneToMany(mappedBy="course")
	private List<CourseStudent> courseStudents;

	public Course(String courseTitle, String courseDescription, String courseCredits, Integer courseCapacity,
			String courseStatus) {
		super();
		this.courseTitle = courseTitle;
		this.courseDescription = courseDescription;
		this.courseCredits = courseCredits;
		this.courseCapacity = courseCapacity;
		this.courseStatus = courseStatus;
	}
	
}
