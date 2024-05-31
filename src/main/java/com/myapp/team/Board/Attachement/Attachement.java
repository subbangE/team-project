package com.myapp.team.Board.Attachement;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Attachement {

    private Integer attachementNo;

    private Integer questionNo;

    private String originalFilename;

    private String storedFilename;

    private String attachementPath;
}
