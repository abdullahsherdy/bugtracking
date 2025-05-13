package com.Ashmo.UserService.Service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Ashmo.UserService.Model.Users;
import com.Ashmo.UserService.Reposatory.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users addUser(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public Map<String, String> verify(Users user) {
        Authentication authen = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(authen.isAuthenticated()){
            Map<String,String> map = new HashMap<>();
            map.put("access_token", jwtService.generateToken(user.getUsername(),1000*60*1));
            map.put("refresh_token", jwtService.generateToken(user.getUsername(),1000*60*60*24));
            return map;
        }
        return new HashMap<>();
    }

    public Map<String, String> newAccessToken(String token) {
        String username = jwtService.extractUsername(token);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", jwtService.generateToken(username, 1000 * 60 * 30));
        tokens.put("refresh_token", token);
        return tokens;
    }

    public String getUsername(int id) {
        return userRepo.getUsername(id);
    }

    public Users getUser(String username) {
        return userRepo.getByUsername(username);
    }
    
}
