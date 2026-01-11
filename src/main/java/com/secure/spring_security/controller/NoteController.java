package com.secure.spring_security.controller;

import com.secure.spring_security.model.Note;
import com.secure.spring_security.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody String content,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        String username =  userDetails.getUsername();
        System.out.println("USER DETAILS: " + username);
        Note note = noteService.createNoteForUser(username, content);
        return new ResponseEntity<>(note, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Note>> getUserNotes(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        System.out.println("USER DETAILS: " + username);
        List<Note> noteList = noteService.getNotesForUser(username);
        return new ResponseEntity<>(noteList, HttpStatus.OK);
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<Note> updateNote(@PathVariable Long noteId,
                                           @RequestBody String content,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        Note note = noteService.updateNoteForUser(noteId, content, userDetails.getUsername());
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long noteId,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        noteService.deleteNoteForUser(noteId, userDetails.getUsername());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
