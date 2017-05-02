package com.dulich.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dulich.model.dao.UserDAO;

public class MyDBAuthenticationService implements UserDetailsService{
	private UserDAO userDAO;
	 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.dulich.model.pojo.User user = userDAO.getUserByTaiKhoan(username);
 
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " was not found.");
        }
         
        List<GrantedAuthority> grantList= new ArrayList<GrantedAuthority>();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getLoai());
        grantList.add(authority);

        UserDetails userDetails = (UserDetails) new User(user.getTaiKhoan(), user.getMatKhau(),grantList);
 
        return userDetails;
    }
}
