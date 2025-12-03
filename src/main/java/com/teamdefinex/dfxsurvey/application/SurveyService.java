package com.teamdefinex.dfxsurvey.application;

import com.teamdefinex.dfxsurvey.domain.survey.Survey;
import com.teamdefinex.dfxsurvey.dto.CreateSurveyRequestDTO;
import com.teamdefinex.dfxsurvey.dto.EditSurveyRequestDTO;
import com.teamdefinex.dfxsurvey.dto.SurveyDetailResponseDTO;
import com.teamdefinex.dfxsurvey.dto.SurveyListResponseDTO;
import com.teamdefinex.dfxsurvey.dto.SurveyParticipantSaveDTO;
import com.teamdefinex.dfxsurvey.dto.result.Result;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface SurveyService {
    Result<SurveyDetailResponseDTO> getSurveyDetail(UUID id, Authentication authentication);
    Result<Void> editSurvey(UUID id, EditSurveyRequestDTO request, Authentication authentication);
    Result<Void> deleteSurvey(UUID id, Authentication authentication);
    Result<SurveyDetailResponseDTO> duplicateSurvey(UUID id, Authentication authentication);
    Result<Void> sendSurvey(UUID id, Authentication authentication);
    Result<SurveyListResponseDTO> getSurveyList(int pageNumber, Authentication authentication);
    Result<String> getParticipants(UUID id, Authentication authentication);
    Result<Void> deleteParticipant(UUID id, String email,Authentication authentication);
    Result<Void> addParticipant(SurveyParticipantSaveDTO dto, Authentication authentication);
    Result<SurveyDetailResponseDTO> createSurvey(CreateSurveyRequestDTO request, Authentication authentication);
}
