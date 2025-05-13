package com.Ashmo.UserService.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Ashmo.UserService.Model.MyUserDetails;
import com.Ashmo.UserService.Model.Users;
import com.Ashmo.UserService.Reposatory.UserRepo;


@Service
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepo.getByUsername(username);
        if(user == null){
            System.out.println("user not found");
            throw new UsernameNotFoundException("user not found in class MyUserDetailsService");
        }
        return new MyUserDetails(user);
    }
    
}
