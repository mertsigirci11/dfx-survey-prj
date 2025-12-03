package com.teamdefinex.dfxsurvey.application;

import com.teamdefinex.dfxsurvey.dto.SurveyDetailResponseDTO;
import com.teamdefinex.dfxsurvey.dto.result.Result;

import java.util.UUID;

public interface SurveyService {
    Result<SurveyDetailResponseDTO> getSurveyDetail(UUID id);
}
