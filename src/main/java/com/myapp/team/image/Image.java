package com.myapp.team.image;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Image {

    // 이미지 id
    private int imageNo;

    // 상품 id
    private int productNo;

    // 원본 파일 이름
    private String originImageName;

    // 저장 파일 이름
    private String updateImageName;

    // 이미지 경로
    private String imagePath;

    // 이미지 타입
    private String imageType;

}
