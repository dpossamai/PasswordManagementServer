package br.com.passwordmanagement.security;


import br.com.passwordmanagement.dto.RoleDto;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TokenTO  {

    private String jwtToken;
    private Long userId;
    private String subject;
    private List<RoleDto> roles;
    private String issuer;
    private Date issueDate;
    private Date expirationDate;
    private Date notBeforeDate;
    private Map<String, Object> headerClaims;

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }


    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getNotBeforeDate() {
        return notBeforeDate;
    }

    public void setNotBeforeDate(Date notBeforeDate) {
        this.notBeforeDate = notBeforeDate;
    }

    public Map<String, Object> getHeaderClaims() {
        return headerClaims;
    }

    public void setHeaderClaims(Map<String, Object> headerClaims) {
        this.headerClaims = headerClaims;
    }
}
