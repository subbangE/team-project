package com.myapp.team.Board.Question;

import com.myapp.team.Board.Attachement.Attachement;
import com.myapp.team.Board.Attachement.AttachementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

//질문 보는 거 - 상품 안에서
//질문 작성 - 새로운 url
//질문 수정, 삭제 - 마이페이지

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AttachementService attachementService;

    private final Path AttachementStorageLocation = Paths.get("uploaded_files").toAbsolutePath().normalize();

    // questionNo에 해당되는 답변 가져 오는 컨트롤러
//    @GetMapping("/{questionNo}")
//    public String showAnswerForm(@PathVariable("questionNo") int questionNo, Model model) {
//        List<Question> answerList = questionMapper.getAnswerByQuestionNo(questionNo);
//        model.addAttribute("answerList", answerList);
//        return "QuestionDetail";
//    }

    // 회원일련번호별로 질문한 것들 가져오게 하는 컨트롤러 => URL 수정해야함 마이페이지용
    @GetMapping("/mypage/{userNo}")
    public String getQuestion(@PathVariable("userNo") int userNo, Model model) {
        List<Question> userquestionList = questionMapper.selectQuestionById(userNo);
        model.addAttribute("userquestionList", userquestionList);
//        System.out.println(userquestionList);
        return "board/MypageBoard";
    }

    // 질문 번호, 질문 제목 및 회원일련번호만 가져오게 하는 컨트롤러
    @GetMapping
    public String getQuestion(Model model) {
        List<Question> questionList = questionMapper.getQuestion();
        model.addAttribute("questionList", questionList);
        System.out.println(questionList);
        return "board/Board";
    }

    // 질문과 답변 하나씩 가져 오게 하는 컨트롤러 (QuestionService 사용)
    @GetMapping("/{questionNo}")
    public String showQuestionDetailForm(@PathVariable int questionNo, Model model) {
        Question question = questionService.getQuestionById(questionNo);
        model.addAttribute("question", question);
        //model.addAttribute("questionNo", questionNo);
        System.out.println(question);
        return "board/QuestionDetail";
    }

    // 첨부파일 다운로드 컨트롤러
    @GetMapping("/download/{attachementNo}")
    public ResponseEntity<Resource> downloadAttachement(@PathVariable int attachementNo) throws MalformedURLException, UnsupportedEncodingException {
        Attachement attachement = attachementService.getAttachementNo(attachementNo);
        Path attachementPath = Paths.get(attachement.getAttachementPath()).toAbsolutePath().normalize();
        Resource resource = new UrlResource(attachementPath.toUri());

        if (resource.exists()) {
            String encodedAttachementName = URLEncoder.encode(attachement.getOriginalFilename(), StandardCharsets.UTF_8.toString());
            String contentDisposition = "attachment; filename*=UTF-8''" + encodedAttachementName;

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .body(resource);
        } else {
            throw new RuntimeException("File not found " + attachement.getStoredFilename());
        }

    }

    // 질문 생성 페이지 보여주는 컨트롤러
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("question", new Question());
        return "board/CreateQuestion";
    }

    //  질문 등록하는 컨트롤러 => URL("/question/create/{prodId}")
    @PostMapping("/create")
    public String createQuestion(@RequestParam("questionTitle") String questionTitle,
                                 @RequestParam("questionContent") String questionContent,
                                 @RequestParam("userNo") int userNo,
                                 @RequestParam("file")MultipartFile file,
                                 Model model) throws IOException {
        Question question = new Question(questionTitle, questionContent, userNo);
//        System.out.println(question);
        questionMapper.insertQuestion(question);

        // 첨부 파일이 있다면 DB에 등록(데이터 가져오기)
        if (!file.isEmpty()) {

            if (!Files.exists(AttachementStorageLocation)) {
                Files.createDirectory(AttachementStorageLocation);
            }

            Path AttachementLocation = AttachementStorageLocation.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), AttachementLocation);

            Attachement attachement = new Attachement();
            attachement.setQuestionNo(question.getQuestionNo());
            attachement.setOriginalFilename(file.getOriginalFilename());
            attachement.setAttachementPath(AttachementLocation.toString());
            attachementService.addAttachement(attachement, file);
            System.out.println(file);
        }
        return "redirect:/question";
    }

    // 질문 하나씩 가져 오게 하는 컨트롤러
    @GetMapping("/update/{questionNo}")
    public String showQuestionUpdateForm(@PathVariable int questionNo, Model model) {
        Question question = questionMapper.selectQuestion(questionNo);
        model.addAttribute("question", question);
        return "board/UpdateQuestion";
    }

    // 질문 수정하는 컨트롤러 => URL("/수정하기 아직 모름") 마이페이지에서 수정하기
    @PostMapping("/update/{questionNo}")
    public String editQuestion(@PathVariable("questionNo") int questionNo,
                               @RequestParam("questionTitle") String questionTitle,
                               @RequestParam("questionContent") String questionContent) {
        questionMapper.updateQuestion(questionNo, questionTitle, questionContent);
        return "redirect:/question/{questionNo}";
    }

    // 질문 삭제하는 컨트롤러 => URL("/삭제하기 아직 모름") 마이페이지에서 삭제하기
    @PostMapping("/delete")
    public String deleteQuestion(@RequestParam int questionNo) {
//        System.out.println("삭제 질문 번호: " + questionNo);
        questionMapper.deleteQuestion(questionNo);
        return "redirect:/question";
    }

}
