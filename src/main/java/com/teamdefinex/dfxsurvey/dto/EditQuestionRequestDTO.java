package com.teamdefinex.dfxsurvey.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EditQuestionRequestDTO {
    private String question;
    private String type;
    private Integer order;
    private List<String> options;
}
