package com.myapp.team.Board.Attachement;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

// 첨부파일 관련 서비스 메서드 정의
public interface AttachementService {

    void addAttachement(Attachement attachement, MultipartFile file) throws IOException;   // 다중첨부파일 추가

    List<Attachement> getAttachementsByNo(int questionNo);  // 질문번호에 따른 첨부파일

    //
    Resource downloadAttachement(String storedFilename) throws MalformedURLException;

    //
    Attachement getAttachementNo(int attachementNo);
}
