package com.example.springaccountmicroservicepr.services.repository;


import com.example.springaccountmicroservicepr.pojo.dao.RolesType;
import com.example.springaccountmicroservicepr.pojo.vo.ERole;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RolesType, Long> {

	Optional<RolesType> findByName(ERole name);
}
