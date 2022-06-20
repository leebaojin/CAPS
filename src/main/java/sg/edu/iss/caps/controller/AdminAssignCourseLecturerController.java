package sg.edu.iss.caps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.iss.caps.model.Course;
import sg.edu.iss.caps.model.Lecturer;
import sg.edu.iss.caps.repo.CourseRepository;
import sg.edu.iss.caps.repo.LecturerRepository;

@CrossOrigin
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
		return null;
	}
	
	@GetMapping("/listLecturer")
	public List<Lecturer> getAvailableLecturers(@RequestBody Course course){
		//To obtain a list of lecturers that are not assigned to course
		return null;
	}
	
	@PutMapping("/addLecturer/{lecturerId}")
	public ResponseEntity<Course> addLecturerToCourse(@PathVariable("lecturerId") Integer lecturerId, @RequestBody Course course){
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/removeLecturer/{lecturerId}")
	public ResponseEntity<Course> removeLecturerFromCourse(@PathVariable("lecturerId") Integer lecturerId, @RequestBody Course course){
		//Remove lecturer from course
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/removeAllLecturer")
	public ResponseEntity<Course> removeAllLecturer(@RequestBody Course course){
		//Removes all lecturers from course
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	

}
