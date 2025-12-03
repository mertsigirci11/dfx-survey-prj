package com.teamdefinex.dfxsurvey.api.controllers;

import com.teamdefinex.dfxsurvey.application.AnswerService;
import com.teamdefinex.dfxsurvey.application.SurveyService;
import com.teamdefinex.dfxsurvey.dto.AnswerDto;
import com.teamdefinex.dfxsurvey.dto.EditSurveyRequestDTO;
import com.teamdefinex.dfxsurvey.dto.QuestionSummaryResponseDTO;
import com.teamdefinex.dfxsurvey.dto.SurveyDetailResponseDTO;
import com.teamdefinex.dfxsurvey.dto.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController("/admin/surveys")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;
    private final AnswerService answerService;

    @GetMapping("{surveyId}")
    public Result<SurveyDetailResponseDTO> getDetail(@RequestParam("surveyId") UUID surveyId) {
        return surveyService.getSurveyDetail(surveyId);
    }

    @PutMapping("{surveyId}")
    public Result<Void> edit(@RequestParam("surveyId") UUID surveyId, @RequestBody EditSurveyRequestDTO request) {
        return surveyService.editSurvey(surveyId, request);
    }

    @DeleteMapping("{surveyId}")
    public Result<Void> delete(@RequestParam("surveyId") UUID surveyId) {
        return surveyService.deleteSurvey(surveyId);
    }

    @PostMapping("{surveyId}")
    public Result<SurveyDetailResponseDTO> duplicate(@RequestParam("surveyId") UUID surveyId) {
        return surveyService.duplicateSurvey(surveyId);
    }

    @PostMapping("{surveyId}/answer")
    public Result<List<AnswerDto>> saveAnswer(@RequestBody List<AnswerDto> answers){
        return answerService.save(answers);
    }
}
