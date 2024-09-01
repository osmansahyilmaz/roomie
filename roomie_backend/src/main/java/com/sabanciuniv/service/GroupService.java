// IMPLEMENTED BY OSMAN SAH YILMAZ
package com.sabanciuniv.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sabanciuniv.model.Group;
import com.sabanciuniv.model.User;
import com.sabanciuniv.repo.GroupRepository;
import com.sabanciuniv.repo.UserRepository;

@Service
public class GroupService {

	@Autowired
    private GroupRepository groupRepository;
	@Autowired
    private UserRepository userRepository;
	
	public Optional<Group> findById(String groupId) {
		return groupRepository.findById(groupId);
	}
	
	public void createGroup(String name, String username) {
		User user = userRepository.findByUsername(username);
        Group group = new Group();
        group.setName(name);
        group.addUserToGroup(group, user);
        group.setjoinCode(generateUniqueJoinCode());
        groupRepository.save(group);
    }
	
	public boolean joinGroup(String joinCode, User user) {
        Group group = groupRepository.findByJoinCode(joinCode);
        if (group != null && group.getUsers().size() < 6) {
            group.getUsers().add(user);
            groupRepository.save(group);
            return true;
        }
        return false;
    }
	
	private String generateUniqueJoinCode() {
        Random random = new Random();
        String joinCode;
        do {
            int number = 100000 + random.nextInt(900000);  // Generates a 6-digit number
            joinCode = String.valueOf(number);
        } while (groupRepository.findByJoinCode(joinCode) != null);
        return joinCode;
    }
	
	public boolean isUserInAnyGroup(String userId) {
        // Fetch the user by ID and check if they are part of any group
        Optional<User> user = userRepository.findById(userId);
        return user.isPresent() && user.get().getGroup() != null;
    }
}
