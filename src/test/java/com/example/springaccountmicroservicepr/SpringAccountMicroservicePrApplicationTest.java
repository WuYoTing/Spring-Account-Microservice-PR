package com.example.springaccountmicroservicepr;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("ApplicationTest")
class SpringAccountMicroservicePrApplicationTest {

	@Autowired
	private RedissonClient redissonClient;

	@Nested
	@DisplayName("Redis Test")
	class test_redis {

		@Test
		@DisplayName("redis_should_be_ok")
		public void test_redis_be_ok() {
			// Arrange
			String bucketName = "Test";
			String testValue = "Test";
			redissonClient.getBucket(bucketName).set(testValue);

			// Act
			String resultValue = (String) redissonClient.getBucket(bucketName).get();

			// Assert
			Assertions.assertEquals(testValue, resultValue);
			redissonClient.getBucket(bucketName).delete();
		}
	}
}
