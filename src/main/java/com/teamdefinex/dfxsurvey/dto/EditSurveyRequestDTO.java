package com.teamdefinex.dfxsurvey.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EditSurveyRequestDTO {
    private String title;
    private LocalDateTime validUntil;
}
