package com.Ashmo.CommentService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Ashmo.CommentService.Model.Comments;

@Repository
public interface CommentsRepo extends JpaRepository<Comments, Integer> {

    List<Comments> findByBugId(int bugId);
    
}
