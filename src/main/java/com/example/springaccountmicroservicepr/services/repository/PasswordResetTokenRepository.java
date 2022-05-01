package com.example.springaccountmicroservicepr.services.repository;

import com.example.springaccountmicroservicepr.pojo.dao.PasswordResetToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {


	Optional<PasswordResetToken> findByToken(String uuid);
}
