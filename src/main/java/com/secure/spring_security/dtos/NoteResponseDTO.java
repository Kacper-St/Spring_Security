package com.secure.spring_security.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteResponseDTO {

    private Long noteId;
    private String ownerUsername;
    private String content;
}
