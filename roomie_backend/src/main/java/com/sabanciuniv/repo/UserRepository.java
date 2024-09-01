// CODED BY OSMAN SAH YILMAZ
package com.sabanciuniv.repo;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.sabanciuniv.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    public User findByUsername(String username);
    boolean existsByUsername(String username);  // Check if user exists by username
    boolean existsByEmail(String email);        // Check if user exists by email
    Optional<User> findByEmail(String email);
    public User findByUsernameAndPassword(String username,String password);
	@Query("{'token.token':?0}")
	public User findByTokenString(String tokenStr);
    boolean existsByIdAndGroupIsNotNull(String userId);
    Optional<User> findById(String id);
}