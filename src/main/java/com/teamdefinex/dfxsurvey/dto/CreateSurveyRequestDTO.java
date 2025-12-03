package com.teamdefinex.dfxsurvey.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateSurveyRequestDTO {
    private String title;
    private LocalDateTime validUntil;
}
