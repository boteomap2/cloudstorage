package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/note")
@AllArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @GetMapping("/delete")
    public String handleDeleteNote(Authentication authentication, RedirectAttributes redirectAttributes, Integer noteId) {
        Integer userId = ((User) authentication.getPrincipal()).getUserId();
        int cnt = noteService.deleteNoteById(noteId, userId);

        if (cnt == 0) {
            redirectAttributes.addFlashAttribute("errMsg", "Delete Note failed. Try again!");
            return "redirect:/result?error";
        }
        return "redirect:/result?success";
    }

    @PostMapping("/save")
    public String handleAddNote(Authentication authentication, RedirectAttributes redirectAttributes, Note note) {
        Integer userId = ((User) authentication.getPrincipal()).getUserId();
        Integer noteId = note.getNoteId();
        note.setUserId(userId);
        int cnt = 0;

        if (noteId == null) {
            cnt = noteService.addNote(note);
        } else {
            if (noteService.getNoteById(noteId, userId) != null) {

                cnt = noteService.editNoteById(note);
            }
        }

        if (cnt == 0) {
            redirectAttributes.addFlashAttribute("errMsg", "Save Note failed. Try again!");
            return "redirect:/result?error";
        }
        return "redirect:/result?success";
    }




}
