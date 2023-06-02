package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileUploadService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class HomeController {
    private FileUploadService fileUploadService;

    @GetMapping("/home")
    public String getHomePage(Authentication authentication, Model model) {
        User loginUser = (User) authentication.getPrincipal();
        Integer userId = loginUser.getUserId();
        model.addAttribute("ListFileUpload", fileUploadService.getAllFiles(userId));

        return "home";
    }
}
