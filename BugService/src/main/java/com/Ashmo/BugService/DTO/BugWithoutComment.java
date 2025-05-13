package com.Ashmo.BugService.DTO;

import java.util.ArrayList;
import java.util.List;

import com.Ashmo.BugService.Model.BugWrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BugWithoutComment {

    private List<BugWrapper> bugs = new ArrayList<>();

}
