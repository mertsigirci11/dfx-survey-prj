package com.teamdefinex.dfxsurvey.application.impl;

import com.teamdefinex.dfxsurvey.application.AnswerService;
import com.teamdefinex.dfxsurvey.data.AnswerRepository;
import com.teamdefinex.dfxsurvey.domain.survey.Answer;
import com.teamdefinex.dfxsurvey.dto.AnswerDto;
import com.teamdefinex.dfxsurvey.dto.result.Result;
import com.teamdefinex.dfxsurvey.mapper.AnswerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;

    @Override
    public Result<List<AnswerDto>> save(List<AnswerDto> answers) {
        Result<List<AnswerDto>>  result = new Result<>();
        try{
            List<Answer> newAnswers = answerRepository
                    .saveAll(answerMapper.toEntity(answers));
            result.setSuccess(true);

            result.setData(answerMapper.toDto(newAnswers));
            result.setCode("201");
        }catch(Exception e){
            result.setCode("500");
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }

        return result;
    }


}
