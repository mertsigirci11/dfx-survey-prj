package com.teamdefinex.dfxsurvey.application.impl;

import com.teamdefinex.dfxsurvey.application.AnswerService;
import com.teamdefinex.dfxsurvey.application.SurveyService;
import com.teamdefinex.dfxsurvey.data.AnswerRepository;
import com.teamdefinex.dfxsurvey.data.SurveyParticipantRepository;
import com.teamdefinex.dfxsurvey.data.SurveyRepository;
import com.teamdefinex.dfxsurvey.domain.survey.*;
import com.teamdefinex.dfxsurvey.dto.AnswerDto;
import com.teamdefinex.dfxsurvey.dto.UserSurveyDTO;
import com.teamdefinex.dfxsurvey.dto.UserSurveyQuestionDTO;
import com.teamdefinex.dfxsurvey.dto.result.Result;
import com.teamdefinex.dfxsurvey.mapper.AnswerMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;
    private final SurveyParticipantRepository surveyParticipantRepository;
    private final SurveyRepository surveyRepository;

    @Override
    public Result<List<AnswerDto>> save(List<AnswerDto> answers) {
        if (answers.isEmpty()) {
            return Result.success(List.of());
        }

        String participantToken = answers.getFirst().getParticipantToken();

        SurveyParticipant participant = surveyParticipantRepository.findByToken(participantToken);

        if(participant == null) {
            return Result.failure("Participant not found");
        }

        UUID surveyId = participant.getSurvey().getId();

        SurveyStatus surveyStatus = participant.getSurvey().getStatus();

        if(surveyStatus == SurveyStatus.CREATED) {
            return Result.failure("Survey not found");
        }

        if(surveyStatus == SurveyStatus.COMPLETED) {
            return Result.failure("Survey already completed");
        }

        if(surveyStatus == SurveyStatus.EXPIRED) {
            return Result.failure("Survey expired");
        }

        List<Answer> existingAnswers = answerRepository
                .findByParticipantTokenAndSurveyId(participantToken, surveyId);

        Map<UUID, Answer> existingAnswersMap = existingAnswers.stream()
                .collect(Collectors.toMap(Answer::getQuestionId, a -> a));

        List<Answer> answersToSave = new ArrayList<>();

        for (AnswerDto answerDto : answers) {
            Answer answer = existingAnswersMap.get(answerDto.getQuestionId());

            if (answer != null) {
                answer.setAnswer(answerDto.getAnswer());
            } else {
                answer = answerMapper.toEntity(answerDto);
            }

            answer.setSurveyId(surveyId);

            answersToSave.add(answer);
        }

        List<Answer> savedAnswers = answerRepository.saveAll(answersToSave);

        return Result.success(answerMapper.toDto(savedAnswers));
    }

    @Override
    public Result<UserSurveyDTO> getSurvey(String participantToken) {
        SurveyParticipant surveyParticipant = surveyParticipantRepository.findByToken(participantToken);

        if(surveyParticipant == null) {
            return Result.failure("No survey found");
        }

        if(surveyParticipant.getStatus() == SurveyParticipantStatus.EXPIRED) {
            return Result.failure("Survey expired");
        }

        if(surveyParticipant.getStatus() == SurveyParticipantStatus.COMPLETED) {
            return Result.failure("You already completed this survey");
        }

        Survey survey = surveyParticipant.getSurvey();

        if(survey.getStatus() == SurveyStatus.CREATED) {
            return Result.failure("Survey not found");
        }

        if(survey.getStatus() == SurveyStatus.COMPLETED) {
            return Result.failure("Survey already completed");
        }

        if(survey.getStatus() == SurveyStatus.EXPIRED) {
            return Result.failure("Survey expired");
        }

        List<Answer> existingAnswers = answerRepository
                .findByParticipantTokenAndSurveyId(participantToken, survey.getId());

        Map<UUID, String> answersMap = existingAnswers.stream()
                .collect(Collectors.toMap(Answer::getQuestionId, Answer::getAnswer));

        UserSurveyDTO userSurveyDTO = new UserSurveyDTO();
        List<UserSurveyQuestionDTO> questionDTOs = new ArrayList<>();

        survey.getQuestions().forEach(question -> {
            UserSurveyQuestionDTO questionDTO = new UserSurveyQuestionDTO();
            questionDTO.setQuestion(question.getQuestion());
            questionDTO.setId(question.getId().toString());
            questionDTO.setRequired(question.getRequired());

            String existingAnswer = answersMap.get(question.getId());
            questionDTO.setAnswer(existingAnswer);

            questionDTO.setOptions(question.getOptions());

            questionDTOs.add(questionDTO);
        });

        userSurveyDTO.setQuestions(questionDTOs);

        return Result.success(userSurveyDTO);
    }

    @Transactional
    @Override
    public Result<Void> completeSurvey(String participantToken) {
        SurveyParticipant surveyParticipant = surveyParticipantRepository.findByToken(participantToken);

        if(surveyParticipant == null) {
            return Result.failure("No survey found");
        }

        if(surveyParticipant.getStatus() == SurveyParticipantStatus.EXPIRED) {
            return Result.failure("Survey expired");
        }

        if(surveyParticipant.getStatus() == SurveyParticipantStatus.COMPLETED) {
            return Result.failure("You already completed this survey");
        }

        surveyParticipant.setStatus(SurveyParticipantStatus.COMPLETED);

        surveyParticipantRepository.save(surveyParticipant);

        return Result.success(null);
    }
}
