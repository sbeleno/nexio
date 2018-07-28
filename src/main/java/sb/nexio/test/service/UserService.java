package sb.nexio.test.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import sb.nexio.test.domain.User;

public interface UserService extends UserDetailsService {

	/**
	 * Permet de consulter l'utilisateur en fonction du username
	 * @param username 
	 * @return  
	 */
	User findByUsername(String username);

}
