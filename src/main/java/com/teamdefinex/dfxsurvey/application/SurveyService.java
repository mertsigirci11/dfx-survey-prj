package com.teamdefinex.dfxsurvey.application;

import com.teamdefinex.dfxsurvey.dto.EditSurveyRequestDTO;
import com.teamdefinex.dfxsurvey.dto.SurveyDetailResponseDTO;
import com.teamdefinex.dfxsurvey.dto.SurveyListResponseDTO;
import com.teamdefinex.dfxsurvey.dto.result.Result;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface SurveyService {
    Result<SurveyDetailResponseDTO> getSurveyDetail(UUID id);
    Result<Void> editSurvey(UUID id, EditSurveyRequestDTO request);
    Result<Void> deleteSurvey(UUID id);
    Result<SurveyDetailResponseDTO> duplicateSurvey(UUID id);
    Result<Void> sendSurvey(UUID id);
    Result<SurveyListResponseDTO> getSurveyList(UUID id, int pageNumber, Authentication authentication);
}
