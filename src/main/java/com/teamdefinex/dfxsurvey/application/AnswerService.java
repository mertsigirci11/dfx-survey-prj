package com.teamdefinex.dfxsurvey.application;

import com.teamdefinex.dfxsurvey.dto.AnswerDto;
import com.teamdefinex.dfxsurvey.dto.UserSurveyDTO;
import com.teamdefinex.dfxsurvey.dto.result.Result;

import java.util.List;


public interface AnswerService {
    Result<List<AnswerDto>> save(List<AnswerDto> answer);
    Result<UserSurveyDTO> getSurvey(String participantToken);
}
