package br.com.passwordmanagement.security;

import br.com.passwordmanagement.dto.RoleDto;
import br.com.passwordmanagement.model.Role;
import br.com.passwordmanagement.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class JwtUtil {

	public static final String AUTHORIZATION = "Authorization";
	public static final String AUTH_SERVICE_JWT = "auth-service-jwt";
	static final String SECRET = "E937RJFM";

	private static ModelMapper MAPPER = new ModelMapper();

	public static User getUser(String token, CustomUserDetailsService userDetailsService) {
		try {
			Claims claims = Jwts.parser().setSigningKey(SECRET.getBytes("UTF-8")).parseClaimsJws(token)
					.getBody();

			User user = userDetailsService.loadUserByUsername(claims.getSubject());
			if (user == null) {
				throw new AuthenticationCredentialsNotFoundException("User not found");
			}
			if (!user.isEnabled()) {
				throw new AuthenticationCredentialsNotFoundException("Usuário está desabilitado");
			}
			return user;
		} catch (UnsupportedEncodingException e) {
			throw new AuthenticationCredentialsNotFoundException("User not found");
		}
	}

	public static String getToken(TokenTO tokenTO) throws UnsupportedEncodingException {
		return Jwts.builder().setSubject(tokenTO.getSubject()).setExpiration(tokenTO.getExpirationDate())
				.setIssuer(AUTH_SERVICE_JWT).setIssuedAt(new Date()).setNotBefore(new Date())
				.claim("roles", tokenTO.getRoles()).claim("id", tokenTO.getUserId())
				.setHeaderParams(tokenTO.getHeaderClaims())
				.signWith(SignatureAlgorithm.HS256, SECRET.getBytes("UTF-8")).compact();
	}


	public static TokenTO buildTokenTO(User user) {
		LocalDateTime ldt = LocalDateTime.now().plusHours(1);
		Date expirationDate = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

		Map<String, Object> headerClaims = new HashMap<>();
		headerClaims.put("typ", "JWT");
		TokenTO tokenTO = new TokenTO();
		tokenTO.setSubject(user.getUsername());
		tokenTO.setIssueDate(new Date());
		tokenTO.setIssuer(JwtUtil.AUTH_SERVICE_JWT);
		tokenTO.setExpirationDate(expirationDate);
		List<Role> roles = user.getRoles();
		List<RoleDto> roleDtoList = new ArrayList<>();
		roles.forEach(r -> {
			roleDtoList.add(MAPPER.map(r, RoleDto.class));
		});
		tokenTO.setRoles(roleDtoList);
		tokenTO.setHeaderClaims(headerClaims);
		tokenTO.setUserId(user.getId());

		return tokenTO;
	}

}
