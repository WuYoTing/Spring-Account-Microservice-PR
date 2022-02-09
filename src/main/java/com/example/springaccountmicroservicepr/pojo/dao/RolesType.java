package com.example.springaccountmicroservicepr.pojo.dao;

import com.example.springaccountmicroservicepr.pojo.vo.ERole;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles_type")
public class RolesType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	@Getter
	private ERole name;
}
