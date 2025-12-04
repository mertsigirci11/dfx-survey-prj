package com.teamdefinex.dfxsurvey.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SurveyDetailResponseDTO {
    private String id;
    private String title;
    private String status;
    private LocalDateTime validUntil;
    private Integer participantCount;
    private List<QuestionSummaryResponseDTO> questions;
}
