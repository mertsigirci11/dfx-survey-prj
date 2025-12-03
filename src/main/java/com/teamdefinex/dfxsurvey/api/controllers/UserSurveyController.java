package com.teamdefinex.dfxsurvey.api.controllers;

import com.teamdefinex.dfxsurvey.application.AnswerService;
import com.teamdefinex.dfxsurvey.dto.AnswerDto;
import com.teamdefinex.dfxsurvey.dto.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/surveys")
@RequiredArgsConstructor
public class UserSurveyController {
    private final AnswerService answerService;
    @PostMapping("~/survey{surveyId}/answer")
    public Result<List<AnswerDto>> saveAnswer(@RequestBody List<AnswerDto> answers){
        return answerService.save(answers);
    }
}
