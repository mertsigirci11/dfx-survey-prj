package com.teamdefinex.dfxsurvey.application.impl;

import com.teamdefinex.dfxsurvey.application.QuestionService;
import com.teamdefinex.dfxsurvey.data.AnswerRepository;
import com.teamdefinex.dfxsurvey.data.QuestionRepository;
import com.teamdefinex.dfxsurvey.data.SurveyRepository;
import com.teamdefinex.dfxsurvey.domain.admin.QuestionType;
import com.teamdefinex.dfxsurvey.domain.admin.Questions;
import com.teamdefinex.dfxsurvey.domain.survey.Answer;
import com.teamdefinex.dfxsurvey.domain.survey.Survey;
import com.teamdefinex.dfxsurvey.dto.*;
import com.teamdefinex.dfxsurvey.dto.result.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final SurveyRepository surveyRepository;

    public Result<QuestionSummaryResponseDTO> getQuestionSummary(UUID id) {
        Optional<Questions> questionOptional = questionRepository.findById(id);

        if(questionOptional.isEmpty()) {
            return Result.failure("Question not found");
        }

        Questions question = questionOptional.get();

        QuestionSummaryResponseDTO response = new QuestionSummaryResponseDTO();
        response.setId(question.getId().toString());
        response.setQuestion(question.getQuestion());
        response.setType(question.getType().name());
        response.setOrder(question.getQuestionOrder());

        return Result.success(response);
    }

    public Result<QuestionDetailResponseDTO> getQuestionDetail(UUID id) {
        Optional<Questions> questionOptional = questionRepository.findById(id);

        if(questionOptional.isEmpty()) {
            return Result.failure("Question not found");
        }

        Optional<Answer> answerOptional = answerRepository.findByQuestionId(id);
        Questions question = questionOptional.get();
        Answer answer = answerOptional.get();

        QuestionDetailResponseDTO response = new QuestionDetailResponseDTO();
        response.setId(question.getId().toString());
        response.setQuestion(question.getQuestion());
        response.setType(question.getType().name());
        response.setOrder(question.getQuestionOrder());
        response.setAnswer(answer.getAnswer());

        return Result.success(response);
    }

    @Transactional
    public Result<Void> editQuestion(UUID id, EditQuestionRequestDTO request) {
        Optional<Questions> questionOptional = questionRepository.findById(id);

        if(questionOptional.isEmpty()) {
            return Result.failure("Question not found");
        }

        Questions question = questionOptional.get();

        if(request.getQuestion() != null) question.setQuestion(request.getQuestion());
        if(request.getType() != null) question.setType(QuestionType.valueOf(request.getType()));
        if(request.getOrder() != null) question.setQuestionOrder(request.getOrder());

        questionRepository.save(question);

        return Result.success(null);
    }

    public Result<Void> deleteQuestion(UUID id) {
        Optional<Questions> questionOptional = questionRepository.findById(id);

        if(questionOptional.isEmpty()) {
            return Result.failure("Question not found");
        }

        Questions question = questionOptional.get();

        questionRepository.delete(question);

        return Result.success(null);
    }

    public Result<QuestionSummaryResponseDTO> duplicateQuestion(UUID id) {
        Optional<Questions> questionOptional = questionRepository.findById(id);

        if(questionOptional.isEmpty()) {
            return Result.failure("Question not found");
        }

        Questions question = questionOptional.get();

        Questions copy = new Questions();
        BeanUtils.copyProperties(question, copy);

        copy.setId(UUID.randomUUID());

        questionRepository.save(copy);

        QuestionSummaryResponseDTO response = createQuestionSummaryResponseDTO(copy);

        return Result.success(response);
    }

    public Result<QuestionSummaryResponseDTO> addQuestion(UUID surveyId, EditQuestionRequestDTO request) {
        Questions question = new Questions();

        question.setQuestion(request.getQuestion());
        question.setType(QuestionType.valueOf(request.getType()));
        question.setQuestionOrder(request.getOrder());

        Questions savedQuestion= questionRepository.save(question);

        QuestionSummaryResponseDTO response = createQuestionSummaryResponseDTO(savedQuestion);

        return Result.success(response);

    }

    private QuestionSummaryResponseDTO createQuestionSummaryResponseDTO(Questions question) {
        QuestionSummaryResponseDTO response = new QuestionSummaryResponseDTO();
        response.setId(question.getId().toString());
        response.setQuestion(question.getQuestion());
        response.setType(question.getType().name());
        response.setOrder(question.getQuestionOrder());
        return response;
    }


}