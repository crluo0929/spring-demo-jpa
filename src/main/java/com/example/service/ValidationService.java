package com.example.service;

import java.util.List;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.repo.UserRepository;

@Service
public class ValidationService {

	@Autowired UserRepository userDao ;
	
	public boolean validateUserPassword(String username, String password) throws AuthenticationException {
		List<Object[]> ret = userDao.getAuthInfoByName(username);
		
		if(ret==null || ret.size()==0) {
			throw new AuthenticationException("incorrect username or password");
		}
		//id,username,password,roles
		Object[] row = ret.get(0) ;
		User user = new User() ;
		user.setId((int)row[0]);
		user.setUsername((String) row[1]);
		user.setPassword((String) row[2]);
		user.setRoles((String) row[3]);
		
		if(user.getPassword().equals(password)) return true ;
		throw new AuthenticationException("incorrect username or password");
	}
	
}
