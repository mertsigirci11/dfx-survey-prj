package com.teamdefinex.dfxsurvey.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserSurveyQuestionDTO {
    private String question;
    private String answer;
    private List<String> options;
}
