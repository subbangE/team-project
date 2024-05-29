package com.myapp.team.Board;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    // question_no를 통해 질문 한개씩 불러오기
    @Select("select * from question where question_no=#{question_no}")
    Question getQuestion(int question_no);

    // user_no와 관련된 질문 목록 불러오기
    @Select("select * from question where user_no=#{user_no}")
    List<Question> getQuestionListByuserId(int user_no);

    // 모든 question 불러오기
    @Select("select * from question")
    List<Question> getQuestionList();

    // question 등록
    @Insert("insert into question (question_title, question_content, user_no) values (#{question_title}, #{question_content}, #{user_no})")
    @Options(useGeneratedKeys = true, keyProperty = "question_no", keyColumn = "question_no")   //  Mybatis DB에서 자동으로 생성된 키 값을 사용하도록 설정
    int insertQuestion(Question question);

    // question 수정
    @Update("update question set question_title=#{question_title}, question_content=#{question_content} where question_no=#{question_no}")
    int updateQuestion(int question_no, String question_title, String question_content);

    // question 삭제
    @Delete("delete from question where question_no=#{question_no}")
    int deleteQuestion(int question_no);
}
