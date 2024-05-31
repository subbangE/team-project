package com.myapp.team.Board.Attachement;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AttachementMapper {

    @Insert("insert into attachement (attachement_no, question_no, original_filename, stored_filename, attachement_path) values (#{attachementNo}, #{questionNo}, #{originalFilename}, #{storedFilename}, #{attachementPath})")
    void insertAttachement(Attachement attachement);

    @Select("select * from attachement where question_no = #{questionNo}")
    List<Attachement> getAttachementsByNo(int questionNo);

    @Select("select * from attachement where attachement_no = #{attachementNo}")
    Attachement selectAttachementByNo(int attachementNo);
}
