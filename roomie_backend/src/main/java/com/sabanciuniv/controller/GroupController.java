package com.sabanciuniv.controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sabanciuniv.exception.GroupException;
import com.sabanciuniv.service.GroupService;

@RestController
@RequestMapping("/roomies/groups")
public class GroupController {
	
	@Autowired private GroupService groupService;
	
	Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@PostMapping("/create-group")
    public Payload<String> createGroup(@RequestBody ObjectNode json) {
		String name = json.get("name").asText();
		String username = json.get("username").asText();
		try {
			if (name.equals("")) {
				throw new GroupException("Name field cannot be empty!");
			}
		}
		catch (Exception e){
			logger.error(e.getMessage());
			throw new GroupException(e.getMessage());
		}
        groupService.createGroup(name, username);

        return new Payload<String>(LocalDateTime.now(), "OK" , "Group Created Succesfully.");
    }
	
	@GetMapping("/{userId}")
	public ResponseEntity<Payload<String>> checkUserGroupMembership(@PathVariable String userId) {
	    boolean isInGroup = groupService.isUserInAnyGroup(userId);
	    String message = isInGroup ? "User has a group!" : "User has no group!";
	    return ResponseEntity.ok(new Payload<>(LocalDateTime.now(), "OK", message));
	}
}
