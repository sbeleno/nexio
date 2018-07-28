package sb.nexio.test.security;

public class ConsSecurity {

	// Spring Security
	public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
	public static final String TOKEN_BEARER_PREFIX = "Bearer ";
	public static final String LOGIN_URL = "/login";
	
	// JWT
	public static final String ISSUER_INFO = "belenoshirley";
	//cambiar clave
	public static final String SUPER_SECRET_KEY = "nyE3xi#7iQ#zMW-SPqw2[#Q!|-bZta)I#I_m(==LdpqH0+2Kp/bUUKW*lEqwIZoX*1";
	public static final long TOKEN_EXPIRATION_TIME = 216_000_000; // 10 day
	
}
