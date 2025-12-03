package com.teamdefinex.dfxsurvey.application;

import com.teamdefinex.dfxsurvey.dto.QuestionDetailResponseDTO;
import com.teamdefinex.dfxsurvey.dto.QuestionSummaryResponseDTO;
import com.teamdefinex.dfxsurvey.dto.SurveyDetailResponseDTO;
import com.teamdefinex.dfxsurvey.dto.result.Result;

import java.util.UUID;
public interface QuestionService {
    Result<QuestionSummaryResponseDTO> getQuetionSummary(UUID id);
    Result<QuestionDetailResponseDTO> getQuetionDetail(UUID id);

}
