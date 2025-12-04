package com.teamdefinex.dfxsurvey.mapper;

import com.teamdefinex.dfxsurvey.domain.survey.Answer;
import com.teamdefinex.dfxsurvey.dto.AnswerDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnswerMapper {
    public AnswerDto toDto(Answer answer){
        AnswerDto answerDto = new AnswerDto();

        answerDto.setAnswer(answer.getAnswer());
        answerDto.setQuestionId(answer.getQuestionId());
        answerDto.setParticipantToken(answer.getParticipantToken());

        return answerDto;
    }

    public Answer toEntity(AnswerDto answerDto){
        Answer answer = new Answer();

        answer.setAnswer(answerDto.getAnswer());
        answer.setQuestionId(answerDto.getQuestionId());
        answer.setParticipantToken(answerDto.getParticipantToken());

        return answer;
    }

    public List<AnswerDto> toDto(List<Answer> answers){
        List<AnswerDto> answerDtos = new ArrayList<>();
        for(Answer answer : answers){
            answerDtos.add(toDto(answer));
        }
        return answerDtos;
    }

    public List<Answer> toEntity(List<AnswerDto> answerDtos){
        List<Answer> answers = new ArrayList<>();
        for(AnswerDto answerDto : answerDtos){
            answers.add(toEntity(answerDto));
        }
        return answers;
    }
}
