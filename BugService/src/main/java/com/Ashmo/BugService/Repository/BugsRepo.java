package com.Ashmo.BugService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Ashmo.BugService.Model.Bugs;

@Repository
public interface BugsRepo extends JpaRepository<Bugs, Integer> {

    List<Bugs> findByDeveloperId(int DeveloperId);

    List<Bugs> findByTesterId(int TesterId);

    List<Bugs> findByStatus(String status);
    
    List<Bugs> findByPriority(String priority);
    
}
