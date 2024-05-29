package com.myapp.team.Board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//질문 보는 거 - 상품 안에서
//질문 작성 - 새로운 url
//질문 수정, 삭제 - 마이페이지

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionMapper questionMapper;

    // 회원일련번호별로 질문한 것들 가져오게 하는 컨트롤러 => URL 수정해야함 마이페이지용
//    @GetMapping("/mypage/{userId}")
//    public String getQuestionsbyuserId(@PathVariable("userId") int userId, Model model) {
//        List<Question> userquestionList = questionMapper.getQuestionListByuserId(userId);
//        model.addAttribute("userquestionList", userquestionList);
//        return "board";
//    }

    // 질문 다가져오게 하는 컨트롤러 ( 질문 목록 ) => URL("/prod/{prodId}")로 수정해야함 (제품 상세페이지)
    @GetMapping
    public String getQuestions(Model model) {
        List<Question> questionList = questionMapper.getQuestionList();
        model.addAttribute("questions", questionList);
        System.out.println(questionList);
        return "board";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("question", new Question());
        return "CreateBoard";
    }

    //  질문 등록하는 컨트롤러 => URL("/question/create/{prodId}")
    @PostMapping("/create")
    public String createQuestion(@RequestParam("question_title") String question_title,
                               @RequestParam("question_content") String question_content,
                               @RequestParam("user_no") int user_no) {
        Question question = new Question(question_title, question_content, user_no);
        System.out.println(question);
        questionMapper.insertQuestion(question);
        return "redirect:/question";
    }

    // 질문 하나씩 가져 오게 하는 컨트롤러
    @GetMapping("/update/{question_no}")
    public String showUpdateForm(@PathVariable int question_no, Model model) {
        Question question = questionMapper.getQuestion(question_no);
        model.addAttribute("question", question);
        return "UpdateBoard";
    }

    // 질문 수정하는 컨트롤러 => URL("/수정하기 아직 모름") 마이페이지에서 수정하기
    @PostMapping("/update/{question_no}")
    public String updateQuestion(@PathVariable("question_no") int question_no,
                               @RequestParam("question_title") String question_title,
                               @RequestParam("question_content") String question_content) {
        questionMapper.updateQuestion(question_no, question_title, question_content);
        return "redirect:/question";
    }

    // 질문 삭제하는 컨트롤러 => URL("/삭제하기 아직 모름") 마이페이지에서 삭제하기
    @PostMapping("/delete")
    public String deleteQuestion(@RequestParam int question_no) {
        System.out.println("삭제 비용 번호: " + question_no);
        questionMapper.deleteQuestion(question_no);
        return "redirect:/question";
    }
}
