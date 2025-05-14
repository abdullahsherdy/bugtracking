package com.Ashmo.CommentService.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.Ashmo.CommentService.Model.Users;

@FeignClient(name = "USERSERVICE", configuration = FeignClientConfig.class)
public interface UsersInterface {

    @GetMapping("/user/{username}")
    public Users getUser(@PathVariable String username);
}
