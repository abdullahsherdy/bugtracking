package com.Ashmo.BugService.Feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.Ashmo.BugService.Model.Comments;

@FeignClient("COMMENTSERVICE")
public interface CommentsInterface {

    @GetMapping("/comments/by_bug/{bugId}")
    public List<Comments> getCommentsByBugId(@PathVariable int bugId);

    @DeleteMapping("/comments/delete_by_bug/{id}")
    public void deleteCommentsByBugId(@PathVariable int id);
}
