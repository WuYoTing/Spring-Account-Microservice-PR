package com.example.springaccountmicroservicepr.services;

import com.example.springaccountmicroservicepr.exception.NotFoundException;
import com.example.springaccountmicroservicepr.exception.TokenRefreshException;
import com.example.springaccountmicroservicepr.pojo.dao.PasswordResetToken;
import com.example.springaccountmicroservicepr.pojo.dao.RefreshToken;
import com.example.springaccountmicroservicepr.pojo.dao.User;
import com.example.springaccountmicroservicepr.services.repository.PasswordResetTokenRepository;
import com.example.springaccountmicroservicepr.services.repository.RefreshTokenRepository;
import com.example.springaccountmicroservicepr.services.repository.UserRepository;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class TokenService {

	@Value("${account.service.jwtRefreshExpirationMs}")
	private int jwtRefreshExpirationMs;

	@Value("${account.service.passwordResetTokenExpirationMs}")
	private int passwordResetTokenExpirationMs;

	private UserRepository userRepository;
	private RefreshTokenRepository refreshTokenRepository;
	private PasswordResetTokenRepository passwordResetTokenRepository;

	@Autowired
	public TokenService(RefreshTokenRepository refreshTokenRepository,
		UserRepository userRepository) {
		this.refreshTokenRepository = refreshTokenRepository;
		this.userRepository = userRepository;
	}


	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	public RefreshToken createRefreshToken(Long userId) {
		Optional<User> userOptional = userRepository.findById(userId);
		if (!userOptional.isPresent()) {
			log.error("Error: Username Not Exist");
			throw new NotFoundException("Username Not Exist!");
		}
		RefreshToken refreshToken = new RefreshToken(userOptional.get(), UUID.randomUUID().toString(),
			Instant.now().plusMillis(jwtRefreshExpirationMs));
		refreshToken = refreshTokenRepository.save(refreshToken);
		return refreshToken;
	}

	public RefreshToken verifyExpiration(RefreshToken token) {
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepository.delete(token);
			throw new TokenRefreshException(token.getToken(),
				"Refresh token was expired. Please make a new signin request");
		}
		return token;
	}

	@Transactional
	public int deleteByUserId(Long userId) {
		Optional<User> userOptional = userRepository.findById(userId);
		if (!userOptional.isPresent()) {
			log.error("Error: Username Not Exist");
			throw new NotFoundException("Username Not Exist!");
		}
		return refreshTokenRepository.deleteByUser(userOptional.get());
	}


	public PasswordResetToken passwordForget(String email) {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new NotFoundException("Username Not Exist!"));
		String uuid = UUID.randomUUID().toString();
		PasswordResetToken passwordResetToken = new PasswordResetToken(user, uuid,
			Instant.now().plusMillis(passwordResetTokenExpirationMs)
		);
		passwordResetToken = passwordResetTokenRepository.save(passwordResetToken);
		return passwordResetToken;
	}
}
