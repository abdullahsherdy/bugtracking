package com.Ashmo.CommentService.Service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ashmo.CommentService.Model.Comments;
import com.Ashmo.CommentService.Repository.CommentsRepo;

@Service
public class CommentsService {

    @Autowired
    private CommentsRepo commentRepo;
    
    public Comments createComment(Comments comment) {
        comment.setCreatedDate(new Date(System.currentTimeMillis()));
        return commentRepo.save(comment);
    }

    public boolean deleteComment(int id) {
        if (commentRepo.existsById(id)) {
            commentRepo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<Comments> getCommentsByBugId(int bugId) {
        return commentRepo.findByBugId(bugId);
    }
    
    public void deleteCommentsByBugId(int bugId) {
        List<Comments> comments = commentRepo.findByBugId(bugId);
        if (comments.isEmpty()) {
            return;
        }
        commentRepo.deleteAll(comments);
    }
}
