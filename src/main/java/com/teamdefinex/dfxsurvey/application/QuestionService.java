package com.teamdefinex.dfxsurvey.application;

import com.teamdefinex.dfxsurvey.dto.*;
import com.teamdefinex.dfxsurvey.dto.result.Result;
import org.springframework.security.core.Authentication;

import java.util.UUID;
public interface QuestionService {
    Result<QuestionSummaryResponseDTO> getQuestionSummary(UUID id);
    Result<QuestionDetailResponseDTO> getQuestionDetail(UUID id);
    Result<QuestionDetailResponseDTO> editQuestion(UUID questionId, EditQuestionRequestDTO request);
    Result<Void> deleteQuestion(UUID questionId);
    Result<QuestionDetailResponseDTO> duplicateQuestion(UUID id);
    Result<QuestionDetailResponseDTO> addQuestion(UUID id, EditQuestionRequestDTO request, Authentication authentication);
}
