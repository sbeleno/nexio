package sb.nexio.test.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Filtre pour vérifier si un utilisateur au moyen d'un token valide a l'accès
 * @author Shirley Beleno
 *
 */
public class AppAuthorizationFilter extends BasicAuthenticationFilter {

	public AppAuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader(ConsSecurity.HEADER_AUTHORIZACION_KEY);
        if (header == null || !header.startsWith(ConsSecurity.TOKEN_BEARER_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	/**
	 * Permet de convertir un token en objet utilisateur
	 * @param request
	 * @return
	 */
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(ConsSecurity.HEADER_AUTHORIZACION_KEY);
		logger.info(token+" Url: "+request.getRequestURI());
		if (token != null) {
			// Se procesa el token y se recupera el usuario.
			String user = Jwts.parser().setSigningKey(ConsSecurity.SUPER_SECRET_KEY)
					.parseClaimsJws(token.replace(ConsSecurity.TOKEN_BEARER_PREFIX, "")).getBody().getSubject();
			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
			}
			return null;
		}
		return null;
	}
}
