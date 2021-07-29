package br.com.passwordmanagement.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "role")
public class Role implements Serializable, GrantedAuthority {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ROLE")
	private String roleName;

	@ManyToMany(mappedBy="roles")
	@JsonManagedReference
	private List<User> users;

	public Role(){}
	public Role(String role) {
	    this.roleName = role;
	}

    @Override
    public String getAuthority() {
        return this.roleName;
    }

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}