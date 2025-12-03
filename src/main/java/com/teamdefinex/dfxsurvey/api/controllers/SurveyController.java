package com.teamdefinex.dfxsurvey.api.controllers;

import com.teamdefinex.dfxsurvey.application.AnswerService;
import com.teamdefinex.dfxsurvey.application.SurveyService;
import com.teamdefinex.dfxsurvey.dto.*;
import com.teamdefinex.dfxsurvey.dto.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/surveys")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @GetMapping("{surveyId}")
    public Result<SurveyDetailResponseDTO> getDetail(@PathVariable("surveyId") UUID surveyId) {
        return surveyService.getSurveyDetail(surveyId);
    }

    @PutMapping("{surveyId}")
    public Result<Void> edit(@PathVariable("surveyId") UUID surveyId, @RequestBody EditSurveyRequestDTO request) {
        return surveyService.editSurvey(surveyId, request);
    }

    @DeleteMapping("{surveyId}")
    public Result<Void> delete(@PathVariable("surveyId") UUID surveyId) {
        return surveyService.deleteSurvey(surveyId);
    }

    @PostMapping("{surveyId}/duplicate")
    public Result<SurveyDetailResponseDTO> duplicate(@PathVariable("surveyId") UUID surveyId) {
        return surveyService.duplicateSurvey(surveyId);
    }

    @PostMapping("{surveyId}/send")
    public Result<Void> send(@PathVariable("surveyId") UUID surveyId) {
        return surveyService.sendSurvey(surveyId);
    }

    @GetMapping("{surveyId}/summary")
    public Result<SurveyListResponseDTO> send(
            @PathVariable("surveyId") UUID surveyId,
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            Authentication authentication
    ) {
        return surveyService.getSurveyList(surveyId, pageNumber, authentication);
    }
}
