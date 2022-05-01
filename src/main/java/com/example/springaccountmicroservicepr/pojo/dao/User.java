package com.example.springaccountmicroservicepr.pojo.dao;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
	@UniqueConstraint(columnNames = "username"),
	@UniqueConstraint(columnNames = "email")})
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private RefreshToken refreshToken;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_type_id"))
	private List<RolesType> rolesTypes = new ArrayList<>();

	public User(String username, String email, String password, List<RolesType> rolesTypes) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.rolesTypes = rolesTypes;
	}

	public void updatePassword(String newPassword) {
		this.password = newPassword;
	}
}
