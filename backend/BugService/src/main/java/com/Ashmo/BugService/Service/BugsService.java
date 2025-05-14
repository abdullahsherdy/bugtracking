package com.Ashmo.BugService.Service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ashmo.BugService.DTO.BugWithComment;
import com.Ashmo.BugService.DTO.BugWithoutComment;
import com.Ashmo.BugService.Feign.CommentsInterface;
import com.Ashmo.BugService.Feign.UsersInterface;
import com.Ashmo.BugService.Model.BugWrapper;
import com.Ashmo.BugService.Model.Bugs;
import com.Ashmo.BugService.Model.Comments;
import com.Ashmo.BugService.Repository.BugsRepo;

@Service
public class BugsService {

    @Autowired
    private BugsRepo bugsRepo;

    @Autowired
    private CommentsInterface commentsInterface;
    @Autowired
    private UsersInterface usersInterface;
    
    private BugWithoutComment convertBugsToBugWithoutComment(List<Bugs> bugs) {
        BugWithoutComment bugWithoutComment = new BugWithoutComment();
        bugs.forEach(bug -> {
            BugWrapper bugWrapper = new BugWrapper();
            bugWrapper.setTitle(bug.getTitle());
            bugWrapper.setDescription(bug.getDescription());
            bugWrapper.setStatus(bug.getStatus());
            bugWrapper.setPriority(bug.getPriority());
            bugWrapper.setCreatedDate(bug.getCreatedDate());
            bugWrapper.setUpdatedDate(bug.getUpdatedDate());
            bugWrapper.setTesterName(usersInterface.getUsername(bug.getTesterId()));
            bugWrapper.setDeveloperName(usersInterface.getUsername(bug.getDeveloperId()));
            bugWithoutComment.getBugs().add(bugWrapper);
        });
        return bugWithoutComment;
    }

    public BugWithoutComment getAllBugs() {
        List<Bugs> bugs = bugsRepo.findAll();
        if(bugs == null)
            return null;
        return convertBugsToBugWithoutComment(bugs);
    }

    public BugWithoutComment getBugByDeveloperId(int id) {
        List<Bugs> bugs = bugsRepo.findByDeveloperId(id);
        if(bugs == null)
            return null;
        return convertBugsToBugWithoutComment(bugs);
    }

    public Bugs createBug(Bugs bug) {
        bug.setCreatedDate(new Date(System.currentTimeMillis()));
        bug.setStatus("new");
        return bugsRepo.save(bug);
    }

    public Bugs updateBug(Bugs bug) {
        bug.setUpdatedDate(new Date(System.currentTimeMillis()));
        return bugsRepo.save(bug);
    }
    
    public BugWithComment getBugById(int id) {
        BugWithComment bugWithComment = new BugWithComment();

        Bugs bug = bugsRepo.findById(id).orElse(null);
        if(bug == null)
            return null;

        List<Comments> comments = commentsInterface.getCommentsByBugId(id);
        if(comments != null){
            comments.forEach(comment -> {
                comment.setUsername(usersInterface.getUsername(comment.getUserId()));
            });
        }
        bugWithComment.setComments(comments);

        BugWrapper bugWrapper = new BugWrapper();
        bugWrapper.setTitle(bug.getTitle());
        bugWrapper.setDescription(bug.getDescription());
        bugWrapper.setStatus(bug.getStatus());
        bugWrapper.setPriority(bug.getPriority());
        bugWrapper.setCreatedDate(bug.getCreatedDate());
        bugWrapper.setUpdatedDate(bug.getUpdatedDate());
        bugWrapper.setTesterName(usersInterface.getUsername(bug.getTesterId()));
        bugWithComment.setBug(bugWrapper);
        
        return bugWithComment;
    }

    public BugWithoutComment getBugsByStatus(String status) {
        List<Bugs> bugs = bugsRepo.findByStatus(status);
        if(bugs == null)
            return null;
        return convertBugsToBugWithoutComment(bugs);
    }

    public BugWithoutComment getBugsByPriority(String priority) {
        List<Bugs> bugs = bugsRepo.findByPriority(priority);
        if(bugs == null)
            return null;
        return convertBugsToBugWithoutComment(bugs);
    }

    public BugWithoutComment getBugByTesterId(int id) {
        List<Bugs> bugs = bugsRepo.findByTesterId(id);
        if(bugs == null)
            return null;
        return convertBugsToBugWithoutComment(bugs);
    }

    public Boolean deleteBug(int id) {
        if(bugsRepo.existsById(id)){
            try {
                commentsInterface.deleteCommentsByBugId(id);
            } catch (Exception e) {
                System.out.println("Error deleting comments: " + e.getMessage());
                return false;
            }
            bugsRepo.deleteById(id);
            return true;
        }
        return false;
        
    }
    
}
