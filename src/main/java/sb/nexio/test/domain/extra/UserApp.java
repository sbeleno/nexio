package sb.nexio.test.domain.extra;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserApp extends User{
	
private static final long serialVersionUID = 1L;
	
	private Object information;
	
	public UserApp(String username, String password,Object information, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.information = information;
	}
	
	public Object getInformation() {
		return information;
	}


}
