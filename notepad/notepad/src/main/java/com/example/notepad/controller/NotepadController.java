package com.example.notepad.controller;

import com.example.notepad.model.Note;
import com.example.notepad.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notepad")
public class NotepadController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/save")
    public String saveNoteAndSendEmail(@RequestBody Note note, @RequestParam String email) {
        emailService.sendEmail(email, note.getTitle(), note.getContent());
        return "e-mail enviado!: " + email;
    }

    @GetMapping("/test")
    public String test() {
        return "ta beleza!!!";
    }
}
