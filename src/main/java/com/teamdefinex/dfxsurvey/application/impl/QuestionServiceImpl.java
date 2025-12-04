package com.teamdefinex.dfxsurvey.application.impl;

import com.teamdefinex.dfxsurvey.application.QuestionService;
import com.teamdefinex.dfxsurvey.data.QuestionRepository;
import com.teamdefinex.dfxsurvey.data.SurveyRepository;
import com.teamdefinex.dfxsurvey.domain.admin.QuestionType;
import com.teamdefinex.dfxsurvey.domain.admin.Questions;
import com.teamdefinex.dfxsurvey.domain.survey.Survey;
import com.teamdefinex.dfxsurvey.domain.survey.SurveyStatus;
import com.teamdefinex.dfxsurvey.dto.*;
import com.teamdefinex.dfxsurvey.dto.result.Result;

import java.util.Optional;
import java.util.UUID;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final SurveyRepository surveyRepository;

    public Result<QuestionSummaryResponseDTO> getQuestionSummary(UUID id) {
        Optional<Questions> questionOptional = questionRepository.findById(id);

        if(questionOptional.isEmpty()) {
            return Result.failure("Question not found");
        }

        Questions question = questionOptional.get();

        QuestionSummaryResponseDTO response = createQuestionSummaryResponseDTO(question);

        return Result.success(response);
    }

    public Result<QuestionDetailResponseDTO> getQuestionDetail(UUID id) {
        Optional<Questions> questionOptional = questionRepository.findById(id);

        if(questionOptional.isEmpty()) {
            return Result.failure("Question not found");
        }

        Questions question = questionOptional.get();

        QuestionDetailResponseDTO response = createQuestionDetailResponseDTO(question);

        return Result.success(response);
    }

    @Transactional
    public Result<QuestionDetailResponseDTO> editQuestion(UUID id, EditQuestionRequestDTO request) {
        Optional<Questions> questionOptional = questionRepository.findById(id);

        if(questionOptional.isEmpty()) {
            return Result.failure("Question not found");
        }

        Questions question = questionOptional.get();

        if(question.getSurvey().getStatus() == SurveyStatus.SENT) {
            return Result.failure("Survey already sent");
        }

        if(question.getSurvey().getStatus() == SurveyStatus.COMPLETED) {
            return Result.failure("Survey already completed");
        }

        if(question.getSurvey().getStatus() == SurveyStatus.EXPIRED) {
            return Result.failure("Survey expired");
        }

        if(request.getQuestion() != null) question.setQuestion(request.getQuestion());
        if(request.getType() != null) question.setType(QuestionType.valueOf(request.getType()));
        if(request.getOrder() != null) question.setQuestionOrder(request.getOrder());
        if (request.getOptions() != null && !request.getOptions().isEmpty()) {
            question.getOptions().clear();
            question.getOptions().addAll(request.getOptions());
        }
        question.setRequired(request.getRequired());

        Questions savedQuestion = questionRepository.save(question);

        QuestionDetailResponseDTO response = createQuestionDetailResponseDTO(savedQuestion);

        return Result.success(response);
    }

    public Result<Void> deleteQuestion(UUID id) {
        Optional<Questions> questionOptional = questionRepository.findById(id);

        if(questionOptional.isEmpty()) {
            return Result.failure("Question not found");
        }

        Questions question = questionOptional.get();

        if(question.getSurvey().getStatus() == SurveyStatus.SENT) {
            return Result.failure("Survey already sent");
        }

        if(question.getSurvey().getStatus() == SurveyStatus.COMPLETED) {
            return Result.failure("Survey already completed");
        }

        if(question.getSurvey().getStatus() == SurveyStatus.EXPIRED) {
            return Result.failure("Survey expired");
        }

        questionRepository.delete(question);

        return Result.success(null);
    }

    public Result<QuestionDetailResponseDTO> duplicateQuestion(UUID id) {
        Optional<Questions> questionOptional = questionRepository.findById(id);

        if(questionOptional.isEmpty()) {
            return Result.failure("Question not found");
        }

        Questions question = questionOptional.get();

        if(question.getSurvey().getStatus() == SurveyStatus.SENT) {
            return Result.failure("Survey already sent");
        }

        if(question.getSurvey().getStatus() == SurveyStatus.COMPLETED) {
            return Result.failure("Survey already completed");
        }

        if(question.getSurvey().getStatus() == SurveyStatus.EXPIRED) {
            return Result.failure("Survey expired");
        }

        Questions copy = question.duplicate();

        Questions savedQuestion = questionRepository.save(copy);

        QuestionDetailResponseDTO response = createQuestionDetailResponseDTO(savedQuestion);

        return Result.success(response);
    }

    public Result<QuestionDetailResponseDTO> addQuestion(UUID surveyId, EditQuestionRequestDTO request, Authentication authentication) {
        Optional<Survey> surveyOptional = surveyRepository.findById(surveyId);

        if(surveyOptional.isEmpty()) {
            return Result.failure("Survey not found");
        }

        Survey survey = surveyOptional.get();

        String userId = SurveyServiceImpl.getUserId(authentication);

        if(!survey.getOwnerId().equals(userId)) {
            return Result.failure("You are not owner of this Survey");
        }

        if(survey.getStatus() == SurveyStatus.SENT) {
            return Result.failure("Survey already sent");
        }

        if(survey.getStatus() == SurveyStatus.COMPLETED) {
            return Result.failure("Survey already completed");
        }

        if(survey.getStatus() == SurveyStatus.EXPIRED) {
            return Result.failure("Survey expired");
        }

        Questions question = new Questions();

        question.setQuestion(request.getQuestion());
        question.setType(QuestionType.valueOf(request.getType()));
        question.setQuestionOrder(request.getOrder());
        question.setSurvey(survey);
        question.setOptions(request.getOptions());
        question.setRequired(request.getRequired());

        Questions savedQuestion = questionRepository.save(question);

        QuestionDetailResponseDTO response = createQuestionDetailResponseDTO(savedQuestion);

        return Result.success(response);

    }

    private QuestionDetailResponseDTO createQuestionDetailResponseDTO(Questions question) {
        QuestionDetailResponseDTO response = new QuestionDetailResponseDTO();
        response.setId(question.getId().toString());
        response.setQuestion(question.getQuestion());
        response.setType(question.getType().name());
        response.setOrder(question.getQuestionOrder());
        response.setOptions(question.getOptions());
        response.setRequired(question.getRequired());

        return response;
    }

    private QuestionSummaryResponseDTO createQuestionSummaryResponseDTO(Questions question) {
        QuestionSummaryResponseDTO response = new QuestionSummaryResponseDTO();
        response.setId(question.getId().toString());
        response.setQuestion(question.getQuestion());
        response.setType(question.getType().name());
        response.setOrder(question.getQuestionOrder());
        response.setRequired(question.getRequired());

        return response;
    }


}