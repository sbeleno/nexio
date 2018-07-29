package sb.nexio.test.controller;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import sb.nexio.test.domain.User;
import sb.nexio.test.domain.extra.ResultBuilder;
import sb.nexio.test.domain.extra.UserApp;
import sb.nexio.test.security.ConsSecurity;

/**
 * Permet l'access d'un utilisateur à partir de l'username et le mot de passe. 
 * @author Shirley Beleno
 * 
 */
@RestController
public class LoginController {

	private AuthenticationManager authenticationManager;

	public LoginController(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	public ResponseEntity<?> login(@RequestBody User user) {
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					user.getUsername(), user.getPassword(), new ArrayList<>()));
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserApp) {
				HttpHeaders headers = new HttpHeaders();
				headers.add("X-Token", getToken(((UserApp) principal).getUsername()));
				return new ResponseEntity<>(
						new ResultBuilder("X-Token", getToken(((UserApp) principal).getUsername()))
						.put("X-Info", ((UserApp) principal).getInformation().toString()).getPlayload(),headers,
						HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new ResultBuilder("X-Error", "On n'a pas pu compléter l'authentification"),
						HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(new ResultBuilder("X-Error", e.getMessage()).getPlayload(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Génère un token valide
	 * @param username d'utilisateur authentifié
	 * @return token généré
	 */
	public synchronized String getToken(String username) {
		String token = Jwts.builder().setIssuedAt(new Date()).setIssuer(ConsSecurity.ISSUER_INFO).setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + ConsSecurity.TOKEN_EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, ConsSecurity.SUPER_SECRET_KEY).compact();
		return token;
	}

}
