package com.Ashmo.BugService.Model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comments {

    private int id;
    
    private String username;

    private int userId;

    private String comment;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date createdDate;

    public Comments(int id, String comment, int userId, Date createdDate) {
        this.id = id;
        this.comment = comment;
        this.userId = userId;
        this.createdDate = createdDate;
    }

    
}
