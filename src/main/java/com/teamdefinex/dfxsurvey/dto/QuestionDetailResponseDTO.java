package com.teamdefinex.dfxsurvey.dto;


import lombok.Data;

import java.util.List;

@Data
public class QuestionDetailResponseDTO {
    private String id;
    private String question;
    private String type;
    private Integer order;
    private List<String> options;
    private Boolean required;
}
