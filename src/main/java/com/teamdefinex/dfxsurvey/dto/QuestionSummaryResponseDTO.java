package com.teamdefinex.dfxsurvey.dto;

import lombok.Data;

@Data
public class QuestionSummaryResponseDTO {
    private String id;
    private String question;
    private String type;
    private Integer order;
    private Boolean required;
}
