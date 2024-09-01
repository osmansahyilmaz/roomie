// CODED BY OSMAN SAH YILMAZ
package com.sabanciuniv.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sabanciuniv.model.Group;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {
    Group findByUsersContains(String userId);  // Find groups by member ID
    boolean existsByIdAndUsersContains(String groupId, String userId);  // Check if user is part of the group
    Optional<Group> findById(String id);	
    Group findByJoinCode(String joinCode);
}
