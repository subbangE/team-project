package com.myapp.team.Board.Attachement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/attachements")
public class AttachementController {

    @Autowired
    private AttachementService attachementService;

    // 첨부파일 업로드하는 컨트롤러
    @PostMapping
    public ResponseEntity<Attachement> addAttachement(@RequestParam("file")MultipartFile file, @RequestParam("questionNo") int questionNo ) throws IOException {
        Attachement attachement = new Attachement();
        attachement.setQuestionNo(questionNo);
        attachementService.addAttachement(attachement, file);
        return ResponseEntity.ok().build();
    }

    // 특정 질문에 해당하는 첨부파일 목록 조회
    @GetMapping("/question/{questionNo}")
    public ResponseEntity<List<Attachement>> getAttachementsByNo(@PathVariable("questionNo") int questionNo) {
        List<Attachement> attachements = attachementService.getAttachementsByNo(questionNo);
        return ResponseEntity.ok(attachements);
    }

    @GetMapping("/download/{storedFilename}")
    public ResponseEntity<Resource> downloadAttachement(@PathVariable String storedFilename) throws MalformedURLException {
        Resource resource = attachementService.downloadAttachement(storedFilename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
