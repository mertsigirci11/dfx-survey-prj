package com.teamdefinex.dfxsurvey.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserSurveyDTO {
    private List<UserSurveyQuestionDTO> questions;
}

