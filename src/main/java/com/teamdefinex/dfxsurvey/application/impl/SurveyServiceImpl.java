package com.teamdefinex.dfxsurvey.application.impl;

import com.teamdefinex.dfxsurvey.application.SurveyService;
import com.teamdefinex.dfxsurvey.data.SurveyRepository;
import com.teamdefinex.dfxsurvey.domain.admin.Questions;
import com.teamdefinex.dfxsurvey.domain.survey.Survey;
import com.teamdefinex.dfxsurvey.dto.QuestionSummaryResponseDTO;
import com.teamdefinex.dfxsurvey.dto.SurveyDetailResponseDTO;
import com.teamdefinex.dfxsurvey.dto.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository surveyRepository;

    @Override
    public Result<SurveyDetailResponseDTO> getSurveyDetail(UUID id) {
        Optional<Survey> surveyOptional = surveyRepository.findById(id);

        if(surveyOptional.isEmpty()) {
            return Result.failure("Survey not found");
        }

        Survey survey = surveyOptional.get();

        SurveyDetailResponseDTO response = new SurveyDetailResponseDTO();
        response.setId(survey.getId().toString());
        response.setTitle(survey.getTitle());
        response.setStatus(survey.getStatus().name());
        response.setValidUntil(survey.getExpiresAt());

        List<QuestionSummaryResponseDTO> questions = new ArrayList<>();

        for(Questions question : survey.getQuestions()) {
            QuestionSummaryResponseDTO questionSummaryResponseDTO = new QuestionSummaryResponseDTO();
            questionSummaryResponseDTO.setId(question.getId().toString());
            questionSummaryResponseDTO.setText(question.getQuestion());
            questionSummaryResponseDTO.setType(question.getType().name());
            questionSummaryResponseDTO.setOrder(question.getOrder());
            questions.add(questionSummaryResponseDTO);
        }

        response.setQuestions(questions);

        return Result.success(response);
    }
}
