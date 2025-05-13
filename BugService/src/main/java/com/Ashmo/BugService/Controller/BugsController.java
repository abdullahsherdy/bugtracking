package com.Ashmo.BugService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.Ashmo.BugService.DTO.BugWithComment;
import com.Ashmo.BugService.DTO.BugWithoutComment;
import com.Ashmo.BugService.Model.Bugs;
import com.Ashmo.BugService.Service.BugsService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/bugs")
public class BugsController {

    @Autowired
    private BugsService bugsService;

    @PutMapping("/update")
    public ResponseEntity<Bugs> UpdateBug(@RequestBody Bugs bug) {
        Bugs bugUpdated = bugsService.updateBug(bug);
        if (bugUpdated == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(bugUpdated);
    }

    @PostMapping("/create")
    public ResponseEntity<Bugs> CreateNewBug(@RequestBody Bugs bug) {
        Bugs bugCreated = bugsService.createBug(bug);
        if (bugCreated == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(bugCreated);
    }
    
    @GetMapping("/by_priority/{priority}")
    public ResponseEntity<BugWithoutComment> getBugsByPriority(@PathVariable String priority) {
        BugWithoutComment bugs = bugsService.getBugsByPriority(priority);
        if(bugs == null)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(bugs);
    }

    @GetMapping("/by_status/{status}")
    public ResponseEntity<BugWithoutComment> getBugsByStatus(@PathVariable String status) {
        BugWithoutComment bugs = bugsService.getBugsByStatus(status);
        if(bugs == null)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(bugs);
    }
    

    @GetMapping("/developer_id/{id}")
    public ResponseEntity<BugWithoutComment> getBugByDevId(@PathVariable int id) {
        BugWithoutComment bugs = bugsService.getBugByDeveloperId(id);
        if (bugs == null)
            return ResponseEntity.notFound().build();
        else 
            return ResponseEntity.ok(bugs);
    }

    @GetMapping("/tester_id/{id}")
    public ResponseEntity<BugWithoutComment> getBugByTesId(@PathVariable int id) {
        BugWithoutComment bugs = bugsService.getBugByTesterId(id);
        if (bugs == null)
            return ResponseEntity.notFound().build();
        else 
            return ResponseEntity.ok(bugs);
    }

    @GetMapping("/bug_id/{id}")
    public ResponseEntity<BugWithComment> getBugById(@PathVariable int id) {
        BugWithComment bug = bugsService.getBugById(id);
        if (bug.getBug() == null)
            return ResponseEntity.notFound().build();
        else 
            return ResponseEntity.ok(bug);
    }

    @GetMapping("/all")
    public ResponseEntity<BugWithoutComment> getAllBugs() {
        BugWithoutComment bugs = bugsService.getAllBugs();
        if (bugs == null)
            return ResponseEntity.noContent().build();
        else 
            return ResponseEntity.ok(bugs);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBug(@PathVariable int id) {
        Boolean isDeleted = bugsService.deleteBug(id);
        if (!isDeleted)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }
    
}
