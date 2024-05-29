package com.myapp.team.Board;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    private Integer question_no;    // 질문 번호 (자동 증가)

    private String question_title;  // 질문 제목

    private String question_content;    // 질문 내용

//    참조키라서 일단 주석 처리 필요하다면 사용, user별 질문 select하기 위해 사용함
    private Integer user_no;

    public Question(String question_title, String question_content, Integer user_no) {
        this.question_title = question_title;
        this.question_content = question_content;
        this.user_no = user_no;
    }
}
