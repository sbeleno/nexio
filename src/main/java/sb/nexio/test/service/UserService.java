package sb.nexio.test.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import sb.nexio.test.domain.User;

public interface UserService extends UserDetailsService {

	User findByUsername(String username);

}
