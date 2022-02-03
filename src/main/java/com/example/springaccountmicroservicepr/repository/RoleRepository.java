package com.example.springaccountmicroservicepr.repository;


import com.example.springaccountmicroservicepr.pojo.dao.Role;
import com.example.springaccountmicroservicepr.pojo.vo.ERole;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(ERole name);
}
