package sb.nexio.test.service.impl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sb.nexio.test.domain.User;
import sb.nexio.test.domain.extra.UserApp;
import sb.nexio.test.repository.UserRepository;
import sb.nexio.test.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		return new UserApp(user.getUsername(), user.getPassword(), user.getFullname(), Collections.emptyList());
	}
	
	@Override
	@Transactional(readOnly = true)
	public User findByUsername(String username){
		return userRepository.findByUsername(username);
	}
}
