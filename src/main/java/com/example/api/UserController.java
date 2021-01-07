package com.example.api;

import java.util.List;
import java.util.Map;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.User;
import com.example.service.UserService;
import com.example.service.ValidationService;

@RestController
public class UserController {

	@Autowired UserService userService ;
	@Autowired ValidationService validationService ;
	
	@GetMapping("/user/id/{id}")
	public User queryUserById(@PathVariable int id) {
		return userService.findUserById(id) ;
	}
	
	@GetMapping("/user/city/{city}")
	public List<User> queryUsersLiveInCity(@PathVariable String city){
		return userService.findByCity(city);
	}
	
	@PostMapping("/user/create")
	public User createUser(@RequestBody User user) {
		return userService.createUser(user) ;
	}
	
	@PostMapping("/user/validate")
	public String validateUser(@RequestBody Map<String,String> map) {
		try {
			String username = map.get("username") ;
			String password = map.get("password") ;
			if(validationService.validateUserPassword(username, password)) {
				return "success" ;
			}
			return "something wrong" ;
		} catch (AuthenticationException e) {
			return e.getMessage() ;
		}
	}
	
}
