package br.com.passwordmanagement.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@SuppressWarnings("serial")
public class JwtAuthToken extends AbstractAuthenticationToken {

	private String token;

    private Object principal;

    public JwtAuthToken(String token) {
        super(null);
        this.token = token;
        this.setAuthenticated(false);
    }

    public JwtAuthToken(Object principal, String token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.eraseCredentials();
        this.token = token;
        this.principal = principal;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.token = null;
    }

}
