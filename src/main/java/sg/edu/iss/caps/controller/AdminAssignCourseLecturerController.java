package sg.edu.iss.caps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.iss.caps.model.Course;
import sg.edu.iss.caps.model.Lecturer;
import sg.edu.iss.caps.repo.CourseRepository;
import sg.edu.iss.caps.repo.LecturerRepository;

@CrossOrigin(origins= "http://localhost:3000")
@RestController
@RequestMapping("/api/manage/courselecturer")
public class AdminAssignCourseLecturerController {
	
	@Autowired
	private CourseRepository courseRepo;
	
	@Autowired
	private LecturerRepository lecturerRepo;
	
	@GetMapping("/listCourse")
	public List<Course> getCourses(){
		//To obtain full list of courses and send back
		List<Course> courselist = courseRepo.findAll();
		return courselist;
	}
	
	@GetMapping("/listLecturer")
	public List<Lecturer> getLecturers(){
		//To obtain a list of lecturers that are not assigned to course
		List<Lecturer> lecturerlist = lecturerRepo.findAll();
		return lecturerlist;
	}
	
	@GetMapping("/listLecturerByCourseId/{id}")
	public List<Lecturer> getLecturerByCourseId(@PathVariable("id") String courseId){
		List<Lecturer> lecturerlist = lecturerRepo.findLecturerByCourseId(courseId);
		return lecturerlist;
	}
	
	@GetMapping("/listAvilLecturerByCourseId/{id}")
	public List<Lecturer> getAvailableLecturerByCourseId(@PathVariable("id") String courseId){
		List<Lecturer> lecturerlist = lecturerRepo.findAvailLecturerByCourseId(courseId);
		return lecturerlist;
	}
	
	@PostMapping("/addLecturersByCourseId/{courseId}")
	public ResponseEntity<Course> addLecturerToCourse(@PathVariable("courseId") String courseCode, @RequestBody List<Lecturer> lecturers){
		Course course = courseRepo.findById(courseCode).get();
		if(course == null) {
			//No such course
			new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		course.getCourseLecturers().clear();
		course.getCourseLecturers().addAll(lecturers);
		courseRepo.save(course);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	

}
