package br.com.passwordmanagement.security;

import br.com.passwordmanagement.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public class TokenAuthenticationService {

	static final String HEADER_STRING = "Authorization";

	static void addAuthentication(HttpServletResponse response, Authentication auth) throws UnsupportedEncodingException {
		TokenTO buildTokenTO = JwtUtil.buildTokenTO((User)auth.getPrincipal());
		response.addHeader(HEADER_STRING, JwtUtil.getToken(buildTokenTO));
	}

	static Authentication getAuthentication(HttpServletRequest request, CustomUserDetailsService userDetailsService) {
		String token = request.getHeader(HEADER_STRING);
		if (token != null) {
			try {
				Claims claims = Jwts.parser().setSigningKey(JwtUtil.SECRET.getBytes("UTF-8")).parseClaimsJws(token).getBody();

				User user = userDetailsService.loadUserByUsername((String) claims.getSubject());
				if (user == null) {
					throw new AuthenticationCredentialsNotFoundException("Usuário não encontrado");
				}
				if (!user.isEnabled()) {
					throw new AuthenticationCredentialsNotFoundException("Usuário está desabilitado");
				}

				return new JwtAuthToken(user, token, user.getAuthorities());
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
//				throw new AuthenticationCredentialsNotFoundException("User not found");
			} catch (ExpiredJwtException e){
				e.printStackTrace();
//				throw new CredentialsExpiredException("JWT token expirado");
			}
		}
		return null;
	}
}
