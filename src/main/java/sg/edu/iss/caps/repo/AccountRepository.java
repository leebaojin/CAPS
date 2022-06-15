package sg.edu.iss.caps.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.caps.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
	public List<Account> findByUsername(String username);
}
