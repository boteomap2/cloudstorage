package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.FileUpload;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileUploadMapper {

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<FileUpload> getAllFiles(Integer userId);

    @Select("SELECT * FROM FILES WHERE filename = #{fileName} AND userid = #{userId}")
    FileUpload getFileByName(String fileName, Integer userId);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    FileUpload getFileById(Integer fileId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int saveFile(FileUpload file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int deleteFileById(Integer fileId);

}
