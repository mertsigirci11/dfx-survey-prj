package com.teamdefinex.dfxsurvey.application.impl;

import com.teamdefinex.dfxsurvey.application.NotificationService;
import com.teamdefinex.dfxsurvey.application.SurveyService;
import com.teamdefinex.dfxsurvey.data.SurveyRepository;
import com.teamdefinex.dfxsurvey.domain.admin.Questions;
import com.teamdefinex.dfxsurvey.domain.survey.Survey;
import com.teamdefinex.dfxsurvey.dto.EditSurveyRequestDTO;
import com.teamdefinex.dfxsurvey.dto.QuestionSummaryResponseDTO;
import com.teamdefinex.dfxsurvey.dto.SurveyDetailResponseDTO;
import com.teamdefinex.dfxsurvey.dto.result.Result;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    private final NotificationService notificationService;
    private final SurveyRepository surveyRepository;

    @Override
    public Result<SurveyDetailResponseDTO> getSurveyDetail(UUID id) {
        Optional<Survey> surveyOptional = surveyRepository.findById(id);

        if(surveyOptional.isEmpty()) {
            return Result.failure("Survey not found");
        }

        Survey survey = surveyOptional.get();

        SurveyDetailResponseDTO response = createSurveyDetailResponseDTO(survey);

        return Result.success(response);
    }

    @Transactional
    @Override
    public Result<Void> editSurvey(UUID id, EditSurveyRequestDTO request) {
        Optional<Survey> surveyOptional = surveyRepository.findById(id);

        if(surveyOptional.isEmpty()) {
            return Result.failure("Survey not found");
        }

        Survey survey = surveyOptional.get();

        if(request.getTitle() != null) survey.setTitle(request.getTitle());
        if(request.getValidUntil() != null) survey.setExpiresAt(request.getValidUntil());

        surveyRepository.save(survey);

        return Result.success(null);
    }

    @Override
    public Result<Void> deleteSurvey(UUID id) {
        Optional<Survey> surveyOptional = surveyRepository.findById(id);

        if(surveyOptional.isEmpty()) {
            return Result.failure("Survey not found");
        }

        Survey survey = surveyOptional.get();

        surveyRepository.delete(survey);

        return Result.success(null);
    }

    @Override
    public Result<SurveyDetailResponseDTO> duplicateSurvey(UUID id) {
        Optional<Survey> surveyOptional = surveyRepository.findById(id);

        if(surveyOptional.isEmpty()) {
            return Result.failure("Survey not found");
        }

        Survey survey = surveyOptional.get();

        Survey copy = new Survey();
        BeanUtils.copyProperties(survey, copy);

        copy.setId(UUID.randomUUID());

        surveyRepository.save(copy);

        SurveyDetailResponseDTO response = createSurveyDetailResponseDTO(copy);

        return Result.success(response);
    }

    @Override
    public Result<Void> sendSurvey(UUID id) {
        Optional<Survey> surveyOptional = surveyRepository.findById(id);

        if(surveyOptional.isEmpty()) {
            return Result.failure("Survey not found");
        }

        Survey survey = surveyOptional.get();

        String token = UUID.randomUUID().toString();

        survey.getParticipants().forEach(participant -> {
            Dictionary<String, String> parameters = new Hashtable<>();
            parameters.put("token", token);
            parameters.put("title", survey.getTitle());
            parameters.put("baseUrl", "http://localhost:8081");

            notificationService.send(survey.getTitle(), participant, "survey-join", parameters);
        });

        return Result.success(null);
    }

    private SurveyDetailResponseDTO createSurveyDetailResponseDTO(Survey survey) {
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

        return response;
    }
}
