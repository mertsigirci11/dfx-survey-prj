package com.teamdefinex.dfxsurvey.application.impl;

import com.teamdefinex.dfxsurvey.application.NotificationService;
import com.teamdefinex.dfxsurvey.application.SurveyService;
import com.teamdefinex.dfxsurvey.data.SurveyParticipantRepository;
import com.teamdefinex.dfxsurvey.data.SurveyRepository;
import com.teamdefinex.dfxsurvey.domain.admin.Questions;
import com.teamdefinex.dfxsurvey.domain.survey.Survey;
import com.teamdefinex.dfxsurvey.domain.survey.SurveyParticipant;
import com.teamdefinex.dfxsurvey.domain.survey.SurveyParticipantStatus;
import com.teamdefinex.dfxsurvey.domain.survey.SurveyStatus;
import com.teamdefinex.dfxsurvey.dto.*;
import com.teamdefinex.dfxsurvey.dto.result.Result;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    private final NotificationService notificationService;
    private final SurveyRepository surveyRepository;
    private final SurveyParticipantRepository surveyParticipantRepository;

    @Override
    public Result<SurveyDetailResponseDTO> getSurveyDetail(UUID id, Authentication authentication) {
        Optional<Survey> surveyOptional = surveyRepository.findById(id);

        if(surveyOptional.isEmpty()) {
            return Result.failure("Survey not found");
        }

        Survey survey = surveyOptional.get();

        String userId = getUserId(authentication);

        if(!survey.getOwnerId().equals(userId)) {
            return Result.failure("You are not owner of this Survey");
        }

        SurveyDetailResponseDTO response = createSurveyDetailResponseDTO(survey);

        return Result.success(response);
    }

    @Transactional
    @Override
    public Result<Void> editSurvey(UUID id, EditSurveyRequestDTO request, Authentication authentication) {
        Optional<Survey> surveyOptional = surveyRepository.findById(id);

        if(surveyOptional.isEmpty()) {
            return Result.failure("Survey not found");
        }

        Survey survey = surveyOptional.get();

        String userId = getUserId(authentication);

        if(!survey.getOwnerId().equals(userId)) {
            return Result.failure("You are not owner of this Survey");
        }

        if(request.getTitle() != null) survey.setTitle(request.getTitle());
        if(request.getValidUntil() != null) survey.setExpiresAt(request.getValidUntil());

        surveyRepository.save(survey);

        return Result.success(null);
    }

    @Override
    public Result<Void> deleteSurvey(UUID id, Authentication authentication) {
        Optional<Survey> surveyOptional = surveyRepository.findById(id);

        if(surveyOptional.isEmpty()) {
            return Result.failure("Survey not found");
        }

        Survey survey = surveyOptional.get();

        String userId = getUserId(authentication);

        if(!survey.getOwnerId().equals(userId)) {
            return Result.failure("You are not owner of this Survey");
        }

        surveyRepository.delete(survey);

        return Result.success(null);
    }

    @Override
    public Result<SurveyDetailResponseDTO> duplicateSurvey(UUID id, Authentication authentication) {
        Optional<Survey> surveyOptional = surveyRepository.findById(id);

        if(surveyOptional.isEmpty()) {
            return Result.failure("Survey not found");
        }

        Survey survey = surveyOptional.get();

        String userId = getUserId(authentication);

        if(!survey.getOwnerId().equals(userId)) {
            return Result.failure("You are not owner of this Survey");
        }

        Survey copy = survey.duplicate();

        Survey savedSurvey =  surveyRepository.save(copy);

        SurveyDetailResponseDTO response = createSurveyDetailResponseDTO(savedSurvey);

        return Result.success(response);
    }

    @Transactional
    @Override
    public Result<Void> sendSurvey(UUID id, Authentication authentication) {
        Optional<Survey> surveyOptional = surveyRepository.findById(id);

        if(surveyOptional.isEmpty()) {
            return Result.failure("Survey not found");
        }

        Survey survey = surveyOptional.get();

        String userId = getUserId(authentication);

        if(!survey.getOwnerId().equals(userId)) {
            return Result.failure("You are not owner of this Survey");
        }

        String token = UUID.randomUUID().toString();

        survey.getParticipants().forEach(participant -> {
            Dictionary<String, String> parameters = new Hashtable<>();
            parameters.put("token", token);
            parameters.put("title", survey.getTitle());
            parameters.put("baseUrl", "http://localhost:8081");

            notificationService.send(survey.getTitle(), participant, "survey-join", parameters);

            SurveyParticipant surveyParticipant = new SurveyParticipant();
            surveyParticipant.setEmail(participant);
            surveyParticipant.setToken(token);
            surveyParticipant.setStatus(SurveyParticipantStatus.SENT);
            surveyParticipant.setSurvey(survey);
            surveyParticipant.setExpiresAt(survey.getExpiresAt());

            surveyParticipantRepository.save(surveyParticipant);
        });

        survey.setStatus(SurveyStatus.SENT);

        surveyRepository.save(survey);

        return Result.success(null);
    }

    @Override
    public Result<SurveyListResponseDTO> getSurveyList(int pageNumber, Authentication authentication) {
        String userId = getUserId(authentication);

        Pageable pageable = PageRequest.of(pageNumber, 10);

        List<Survey> surveys = surveyRepository.findAllByOwnerId(userId, pageable);

        SurveyListResponseDTO response = new SurveyListResponseDTO();

        List<SurveySummaryResponseDTO> surveySummaries = surveys.
                stream()
                .map(this::createSurveySummaryResponseDTO)
                .toList();

        response.setSurveys(surveySummaries);

        return Result.success(response);
    }

    @Override
    public Result<SurveyDetailResponseDTO> createSurvey(CreateSurveyRequestDTO request, Authentication authentication) {
        String ownerId = getUserId(authentication);

        Survey survey = new Survey();
        survey.setOwnerId(ownerId);
        survey.setStatus(SurveyStatus.CREATED);
        survey.setTitle(request.getTitle());
        survey.setExpiresAt(request.getValidUntil());

        Survey savedSurvey = surveyRepository.save(survey);

        SurveyDetailResponseDTO response = createSurveyDetailResponseDTO(savedSurvey);

        return Result.success(response);
    }

    @Override
    public Result<String> getParticipants(UUID id, Authentication authentication) {
        Optional<Survey> surveyOptional = surveyRepository.findById(id);

        if(surveyOptional.isEmpty()) {
            return Result.failure("Survey not found");
        }

        Survey survey = surveyOptional.get();

        String userId = getUserId(authentication);

        if(!survey.getOwnerId().equals(userId)) {
            return Result.failure("You are not owner of this Survey");
        }

        return Result.success(surveyRepository.findParticipantById(id));
    }

    @Transactional
    @Override
    public Result<Void> deleteParticipant(UUID id, String email, Authentication authentication) {
        Optional<Survey> surveyOptional = surveyRepository.findById(id);

        if(surveyOptional.isEmpty()) {
            return Result.failure("Survey not found");
        }

        Survey survey = surveyOptional.get();

        String userId = getUserId(authentication);

        if(!survey.getOwnerId().equals(userId)) {
            return Result.failure("You are not owner of this Survey");
        }

        List<String> participantList = new ArrayList<>(survey.getParticipants());
        if(participantList.contains(email)) {
            participantList.remove(email);
            survey.setParticipants(participantList);
            surveyRepository.save(survey);
        }

        return Result.success(null);
    }

    @Transactional
    @Override
    public Result<Void> addParticipant(SurveyParticipantSaveDTO dto, Authentication authentication) {
        Optional<Survey> surveyOptional = surveyRepository.findById(dto.getId());

        if(surveyOptional.isEmpty()) {
            return Result.failure("Survey not found");
        }

        Survey survey = surveyOptional.get();

        String userId = getUserId(authentication);

        if(!survey.getOwnerId().equals(userId)) {
            return Result.failure("You are not owner of this Survey");
        }

        if(survey.getParticipants().contains(dto.getEmail())) {
            return Result.failure("Already in participants");
        }

        List<String> participants = new ArrayList<>(survey.getParticipants());
        participants.add(dto.getEmail());
        survey.setParticipants(participants);

        surveyRepository.save(survey);

        return Result.success(null);
    }

    public static String getUserId(Authentication authentication) {
        return ((Jwt) Objects.requireNonNull(authentication.getPrincipal())).getSubject();
    }

    private SurveySummaryResponseDTO createSurveySummaryResponseDTO(Survey survey) {
        SurveySummaryResponseDTO response = new SurveySummaryResponseDTO();
        response.setId(survey.getId().toString());
        response.setTitle(survey.getTitle());
        response.setExpireDate(survey.getExpiresAt());
        response.setStatus(survey.getStatus().name());
        response.setParticipantCount(survey.getParticipants().size());

        return response;
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
            questionSummaryResponseDTO.setQuestion(question.getQuestion());
            questionSummaryResponseDTO.setType(question.getType().name());
            questionSummaryResponseDTO.setOrder(question.getQuestionOrder());
            questions.add(questionSummaryResponseDTO);
        }

        response.setQuestions(questions);

        return response;
    }

}
