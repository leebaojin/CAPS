package sg.edu.iss.caps.repotest;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jboss.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import sg.edu.iss.caps.CapsApplication;
import sg.edu.iss.caps.model.Account;
import sg.edu.iss.caps.model.Role;
import sg.edu.iss.caps.model.Student;
import sg.edu.iss.caps.repo.AccountRepository;
import sg.edu.iss.caps.repo.StudentRepository;
import sg.edu.iss.caps.util.DateUtil;
import sg.edu.iss.caps.util.HashUtil;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CapsApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryTest {
	
	private static final Logger LOGGER = Logger.getLogger(CapsApplication.class.getName());
	@Autowired
	StudentRepository srepo;
	
	@Autowired
	AccountRepository arepo;
	
	@Test
	@Order(1)
	public void testCreateStudent() {
		Calendar c = Calendar.getInstance();
		Student s = new Student("John","Teo",c.getTime());
		//Student s = new Student("John","Teo",LocalDateTime.now());
		Account a = new Account("johnteo","password",Role.STUDENT);
		byte[] hashedpw = HashUtil.getHash("johnteo","password");
		//a.setPasswordHash(hashedpw);
		LOGGER.info("\u001B[34m" + "Hashed : " + "\u001B[0m" + HashUtil.convertByteToHex(hashedpw));
		s.setAccount(a);
		srepo.saveAndFlush(s);
		
		//Test that the student is input into Database
		Assertions.assertNotNull(s.getStudentId());
	}
	
	@Test
	@Order(2)
	public void testFindStudent() {
		Student s = srepo.findStudentByUsername("johnteo").get(0);
		Date entrollmentDate = s.getEnrolledDate();
		//LocalDateTime entrollmentDate = s.getEnrolledDate();
		//Print out date
		LOGGER.info("\u001B[34m" + "Date Enrolled : " + "\u001B[0m" + DateUtil.ConvertFromDate(entrollmentDate));
		
		//Test that the correct person is found
		Assertions.assertEquals(s.getFirstname(),"John");
		List<Account> alist = arepo.findByUsername("johnteo");
		//Test that only 1 entry is found
		Assertions.assertEquals(alist.size(),1);
		//Test hashing of password is correct
		//Assertions.assertTrue(Arrays.equals(HashUtil.getHash("johnteo","password"), alist.get(0).getPasswordHash()));
		
	}
	
	@Test
	@Order(3)
	public void testDeleteStudent() {
		long beforeCount = srepo.count();
		Student s = srepo.findStudentByUsername("johnteo").get(0);
		srepo.delete(s);
		long afterCount = srepo.count();
		//Test that student is deleted
		Assertions.assertEquals(afterCount, beforeCount - 1);
		List<Account> alist = arepo.findByUsername("johnteo");
		//Test that account is deleted
		Assertions.assertEquals(alist.size(),0);
	}
	
}
