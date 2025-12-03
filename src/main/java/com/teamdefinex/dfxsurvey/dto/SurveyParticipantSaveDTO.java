package com.teamdefinex.dfxsurvey.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SurveyParticipantSaveDTO {
    private String email;
    private UUID id;
}
