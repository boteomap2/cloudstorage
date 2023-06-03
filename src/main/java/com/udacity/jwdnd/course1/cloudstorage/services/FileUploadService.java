package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileUploadMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.FileUpload;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class FileUploadService {

    private FileUploadMapper fileUploadMapper;

    public List<FileUpload> getAllFiles(Integer userId) {
        return fileUploadMapper.getAllFiles(userId);
    }

    public FileUpload getFileByName(MultipartFile fileUpload, Integer userId) {
        return fileUploadMapper.getFileByName(handleFileName(fileUpload), userId);
    }

    public FileUpload getFileById(Integer fileId, Integer userId) {
        return fileUploadMapper.getFileById(fileId, userId);
    }

    public int saveFile(MultipartFile fileUpload, Integer userId) throws IOException {
        FileUpload file = new FileUpload();
        file.setFileName(handleFileName(fileUpload));
        file.setContentType(fileUpload.getContentType());
        file.setFileSize(String.valueOf(fileUpload.getSize()));
        file.setFileData(fileUpload.getBytes());
        file.setUserId(userId);
        return fileUploadMapper.saveFile(file);
    }

    public int deleteFileById(Integer fileId, Integer userId) {
        return fileUploadMapper.deleteFileById(fileId, userId);
    }

    private String handleFileName(MultipartFile fileUpload) {
        String fileName = fileUpload.getOriginalFilename();
        int startIndex = fileName.replaceAll("\\\\", "/").lastIndexOf("/");
        return fileName.substring(startIndex + 1);
    }

}
