package com.teamdefinex.dfxsurvey.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AnswerDto {
    private String participantToken;
    private UUID questionId;
    private String answer;
}