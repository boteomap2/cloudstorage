package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileUpload;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileUploadService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/fileUpload")
@AllArgsConstructor
public class FileUploadController {

    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    public String handleFileUpload(MultipartFile fileUpload, Authentication authentication, RedirectAttributes redirectAttributes) throws IOException {
        Integer userId = ((User) authentication.getPrincipal()).getUserId();
        String errMsg = "";

        if (fileUpload.isEmpty()) {
            errMsg = "File is not chosen or empty";
        }

        FileUpload existFile = fileUploadService.getFileByName(fileUpload, userId);
        if (existFile != null) {
            errMsg = "File is already exist";
        }

        if (!errMsg.isBlank()) {
            redirectAttributes.addFlashAttribute("errMsg", errMsg);
            return "redirect:/result?error";
        }

        fileUploadService.saveFile(fileUpload, userId);
        return "redirect:/result?success";
    }

    @GetMapping("/view")
    public ResponseEntity<byte[]> handleViewFile(Integer fileId) {
        FileUpload file = fileUploadService.getFileById(fileId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", file.getFileName());
        headers.setContentType(MediaType.parseMediaType(file.getContentType()));
        return new ResponseEntity<>(file.getFileData(), headers, HttpStatus.OK);
    }

    @GetMapping("/delete")
    public String handleDeleteFile(Integer fileId, RedirectAttributes redirectAttributes) {
        int cnt = fileUploadService.deleteFileById(fileId);
        if (cnt == 0) {
            redirectAttributes.addFlashAttribute("errMsg", "Not exist file");
            return "redirect:/result?error";
        }
        return "redirect:/result?success";
    }

}
