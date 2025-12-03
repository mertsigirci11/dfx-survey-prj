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

    @PostMapping
    public Result<SurveyDetailResponseDTO> create(@RequestBody CreateSurveyRequestDTO request, Authentication authentication) {
        return surveyService.createSurvey(request, authentication);
    }

    @GetMapping("{surveyId}")
    public Result<SurveyDetailResponseDTO> getDetail(@PathVariable("surveyId") UUID surveyId, Authentication authentication) {
        return surveyService.getSurveyDetail(surveyId, authentication);
    }

    @PutMapping("{surveyId}")
    public Result<Void> edit(@PathVariable("surveyId") UUID surveyId, @RequestBody EditSurveyRequestDTO request, Authentication authentication) {
        return surveyService.editSurvey(surveyId, request, authentication);
    }

    @DeleteMapping("{surveyId}")
    public Result<Void> delete(@PathVariable("surveyId") UUID surveyId, Authentication authentication) {
        return surveyService.deleteSurvey(surveyId, authentication);
    }

    @PostMapping("{surveyId}/duplicate")
    public Result<SurveyDetailResponseDTO> duplicate(@PathVariable("surveyId") UUID surveyId, Authentication authentication) {
        return surveyService.duplicateSurvey(surveyId, authentication);
    }

    @PostMapping("{surveyId}/send")
    public Result<Void> send(@PathVariable("surveyId") UUID surveyId, Authentication authentication) {
        return surveyService.sendSurvey(surveyId, authentication);
    }

    @GetMapping("/list")
    public Result<SurveyListResponseDTO> send(
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            Authentication authentication
    ) {
        return surveyService.getSurveyList(pageNumber, authentication);
    }
}
