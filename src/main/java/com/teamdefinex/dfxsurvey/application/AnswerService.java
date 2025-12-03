package com.teamdefinex.dfxsurvey.application;

import com.teamdefinex.dfxsurvey.dto.AnswerDto;
import com.teamdefinex.dfxsurvey.dto.result.Result;

import java.util.List;


public interface AnswerService {
    public Result<List<AnswerDto>> save(List<AnswerDto> answer);
}
