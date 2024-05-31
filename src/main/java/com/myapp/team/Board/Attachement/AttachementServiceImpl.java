package com.myapp.team.Board.Attachement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service    // AttachementService(인터페이스)를 구현해서 실질적인 로직 수행(기능이 수행되도록함)
public class AttachementServiceImpl implements AttachementService{
    // 파일 업로드할 디렉토리 경로 지정
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/java/com/myapp/team/Board/Attachement/uploads/";

    @Autowired
    private AttachementMapper attachementMapper;

    @Override
    public void addAttachement(Attachement attachement, MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        // 파일명이 중복으로 되지 않게 랜덤값 생성해서 붙여서 저장
        String storedFileName = UUID.randomUUID().toString() + "_" + originalFileName;

        // 파일 저장 디렉토리 확인 및 생성
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // 파일 저장
        File uploadFile = new File(UPLOAD_DIR + storedFileName);
        file.transferTo(uploadFile);

        attachement.setOriginalFilename(originalFileName);
        attachement.setStoredFilename(storedFileName);
        attachementMapper.insertAttachement(attachement);
    }

    @Override
    public List<Attachement> getAttachementsByNo(int questionNo) {
        return attachementMapper.getAttachementsByNo(questionNo);
    }

    @Override
    public Resource downloadAttachement(String stroedFilename) throws MalformedURLException {
        return new UrlResource(Paths.get(UPLOAD_DIR + stroedFilename).toUri());
    }

    @Override
    public Attachement getAttachementNo(int attachementNo) {
        return attachementMapper.selectAttachementByNo(attachementNo);
    }
}
