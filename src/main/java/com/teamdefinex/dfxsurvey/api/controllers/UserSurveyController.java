package com.teamdefinex.dfxsurvey.api.controllers;

import com.teamdefinex.dfxsurvey.application.AnswerService;
import com.teamdefinex.dfxsurvey.dto.AnswerDto;
import com.teamdefinex.dfxsurvey.dto.UserSurveyDTO;
import com.teamdefinex.dfxsurvey.dto.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class UserSurveyController {
    private final AnswerService answerService;

    @PostMapping("/answers")
    public Result<List<AnswerDto>> saveAnswer(@RequestBody List<AnswerDto> answers){
        return answerService.save(answers);
    }

    @GetMapping("/{participantToken}")
    public Result<UserSurveyDTO> getSurvey(@PathVariable("participantToken") String token){
        return answerService.getSurvey(token);
    }

    @PostMapping("/{participantToken}/complete")
    public Result<Void> completeSurvey(@PathVariable("participantToken") String token){
        return answerService.completeSurvey(token);
    }

}