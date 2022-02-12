package com.example.springaccountmicroservicepr.repository;

import com.example.springaccountmicroservicepr.pojo.dao.RefreshToken;
import com.example.springaccountmicroservicepr.pojo.dao.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	@Override
	Optional<RefreshToken> findById(Long id);

	Optional<RefreshToken> findByToken(String token);

	int deleteByUser(User user);
}
