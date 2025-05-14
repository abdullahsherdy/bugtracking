package com.Ashmo.UserService.Reposatory;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Ashmo.UserService.Model.Users;


@Repository
public interface UserRepo extends JpaRepository<Users,Integer>{
    
    @Query("SELECT u.username FROM Users u WHERE u.id = ?1")
    String getUsername(int id);
    
    Users getByUsername(String username);
}
