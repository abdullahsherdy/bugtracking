package com.Ashmo.CommentService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Ashmo.CommentService.Model.Comments;
import com.Ashmo.CommentService.Service.CommentsService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/comments")
public class CommentsController {
    
    @Autowired
    private CommentsService commentsService;

    @GetMapping("/by_bug/{bugId}")
    public List<Comments> getCommentsByBugId(@PathVariable int bugId) {
        List<Comments> comments = commentsService.getCommentsByBugId(bugId);
        if(comments.isEmpty())
            return null;
        else
            return comments;
    }

    @PostMapping("/create")
    public ResponseEntity<Comments> createNewComments(@RequestBody Comments comment) {
        Comments commentCreated = commentsService.createComment(comment);
        if(commentCreated == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(commentCreated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteComments(@PathVariable int id) {
        boolean isDeleted = commentsService.deleteComment(id);
        if(!isDeleted)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/delete_by_bug/{id}")
    public void deleteCommentsByBugId(@PathVariable int id) {
        commentsService.deleteCommentsByBugId(id);
    }
}
