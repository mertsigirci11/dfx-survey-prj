package com.teamdefinex.dfxsurvey.application.impl;

import com.teamdefinex.dfxsurvey.data.AnswerRepository;
import com.teamdefinex.dfxsurvey.data.QuestionRepository;
import com.teamdefinex.dfxsurvey.data.SurveyRepository;
import com.teamdefinex.dfxsurvey.domain.admin.Questions;
import com.teamdefinex.dfxsurvey.domain.survey.Answer;
import com.teamdefinex.dfxsurvey.domain.survey.Survey;
import com.teamdefinex.dfxsurvey.dto.QuestionDetailResponseDTO;
import com.teamdefinex.dfxsurvey.dto.QuestionSummaryResponseDTO;
import com.teamdefinex.dfxsurvey.dto.SurveyDetailResponseDTO;
import com.teamdefinex.dfxsurvey.dto.result.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

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
        response.setOrder(question.getOrder());

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
        response.setOrder(question.getOrder());
        response.setAnswer(answer.getAnswer());

        return Result.success(response);
    }

}