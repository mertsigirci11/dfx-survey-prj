package com.teamdefinex.dfxsurvey.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EditQuestionRequestDTO {
    private String question;
    private String type;
    private Integer order;
}
