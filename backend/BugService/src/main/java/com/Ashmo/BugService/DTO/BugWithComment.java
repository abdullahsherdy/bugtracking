package com.Ashmo.BugService.DTO;

import java.util.List;

import com.Ashmo.BugService.Model.BugWrapper;
import com.Ashmo.BugService.Model.Comments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BugWithComment {

    private BugWrapper bug;

    private List<Comments> comments;

}
