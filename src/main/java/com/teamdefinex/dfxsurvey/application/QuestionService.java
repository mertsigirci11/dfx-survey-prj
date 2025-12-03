package com.teamdefinex.dfxsurvey.application;

import com.teamdefinex.dfxsurvey.dto.*;
import com.teamdefinex.dfxsurvey.dto.result.Result;

import java.util.UUID;
public interface QuestionService {
    Result<QuestionSummaryResponseDTO> getQuestionSummary(UUID id);
    Result<QuestionDetailResponseDTO> getQuestionDetail(UUID id);
    Result<Void> editQuestion(UUID questionId, EditQuestionRequestDTO request);
    Result<Void> deleteQuestion(UUID questionId);
    Result<QuestionSummaryResponseDTO> duplicateQuestion(UUID id);
    Result<QuestionSummaryResponseDTO> addQuestion(UUID id, EditQuestionRequestDTO request);
}
