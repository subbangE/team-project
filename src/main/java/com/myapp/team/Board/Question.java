package com.myapp.team.Board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Question {
    private Integer question_no;

    private String question_title;

    private String question_content;
}
