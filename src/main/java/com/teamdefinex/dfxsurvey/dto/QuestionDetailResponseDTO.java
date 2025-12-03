package com.teamdefinex.dfxsurvey.dto;


import lombok.Data;

@Data
public class QuestionDetailResponseDTO {
    private String id;
    private String question;
    private String type;
    private Integer order;
    private String answer;
}
