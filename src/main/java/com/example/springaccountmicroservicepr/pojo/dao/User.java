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
	@Getter
	private Long id;

	@NotBlank
	@Size(max = 20)
	@Getter
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	@Getter
	private String email;

	@NotBlank
	@Size(max = 120)
	@Getter
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	@Getter
	private Set<Role> roles = new HashSet<>();
}
