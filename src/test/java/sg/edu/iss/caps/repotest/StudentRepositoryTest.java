package sg.edu.iss.caps.repotest;

import java.time.LocalDateTime;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import sg.edu.iss.caps.CapsApplication;
import sg.edu.iss.caps.model.Student;
import sg.edu.iss.caps.repo.StudentRepository;
import sg.edu.iss.caps.util.DateUtil;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CapsApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryTest {
	@Autowired
	StudentRepository srepo;
	
	@Test
	@Order(1)
	public void testCreateStudent() {
		Student s = new Student("John","Teo","johnteo","johnteo",LocalDateTime.now());
		srepo.saveAndFlush(s);
		Assertions.assertNotNull(s.getStudentId());
	}
	
	@Test
	@Order(2)
	public void testFindStudent() {
		Student s = srepo.findByUsername("johnteo").get(0);
		LocalDateTime entrollmentDate = s.getEnrolledDate();
		System.out.println("\u001B[34m" + "\t Date Enrolled: " + "\u001B[0m" + DateUtil.ConvertFromLocalDateTime(entrollmentDate));
		System.out.println();
		Assertions.assertEquals(s.getFirstname(),"John");
		
	}
	
	@Test
	@Order(3)
	public void testDeleteStudent() {
		long beforeCount = srepo.count();
		Student s = srepo.findByUsername("johnteo").get(0);
		srepo.delete(s);
		long afterCount = srepo.count();
		Assertions.assertEquals(afterCount, beforeCount - 1);
	}
}
