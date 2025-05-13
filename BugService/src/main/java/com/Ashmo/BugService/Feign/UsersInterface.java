package com.Ashmo.BugService.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.Ashmo.BugService.Model.Users;

@FeignClient(name = "USERSERVICE", configuration = FeignClientConfig.class)
public interface UsersInterface {

    @GetMapping("/username/{id}")
    public String getUsername(@PathVariable int id) ;

    @GetMapping("/user/{username}")
    public Users getUser(@PathVariable String username);
}
