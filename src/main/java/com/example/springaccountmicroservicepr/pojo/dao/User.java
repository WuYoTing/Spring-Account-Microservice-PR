package com.example.springaccountmicroservicepr.pojo.dao;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "username"),
	@UniqueConstraint(columnNames = "email")})
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

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

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
}
