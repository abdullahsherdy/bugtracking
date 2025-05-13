package com.Ashmo.CommentService.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Ashmo.CommentService.Feign.UsersInterface;
import com.Ashmo.CommentService.Model.MyUserDetails;
import com.Ashmo.CommentService.Model.Users;


@Service
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    private UsersInterface usersInterface;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user;
        try {
            user = usersInterface.getUser(username);
        } catch (Exception e) {
            System.out.println("Error occurred while fetching user: " + e.getMessage());
            throw new UsernameNotFoundException("User not found");
        }
        if(user == null){
            System.out.println("user not found");
            throw new UsernameNotFoundException("user not found in class MyUserDetailsService");
        }
        return new MyUserDetails(user);
    }
    
}
