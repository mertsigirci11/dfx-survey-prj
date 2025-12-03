package com.teamdefinex.dfxsurvey.api.controllers;

import com.teamdefinex.dfxsurvey.application.QuestionService;
import com.teamdefinex.dfxsurvey.dto.*;
import com.teamdefinex.dfxsurvey.dto.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RestController("/admin/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("{questionId}")
    public Result<QuestionDetailResponseDTO> getQuestionDetail(@RequestParam("questionId") UUID questionId) {
        return questionService.getQuestionDetail(questionId);
    }

    @GetMapping("{questionId}/summary")
    public Result<QuestionSummaryResponseDTO> getQuestionSummary(@RequestParam("questionId") UUID questionId) {
        return questionService.getQuestionSummary(questionId);
    }

    @PutMapping("{questionId}")
    public Result<Void> edit(@RequestParam("questionId") UUID questionId, @RequestBody EditQuestionRequestDTO request) {
        return questionService.editQuestion(questionId, request);
    }

    @DeleteMapping("{questionId}")
    public Result<Void> delete(@RequestParam("questionId") UUID questionId) {
        return questionService.deleteQuestion(questionId);
    }

    @PostMapping("{questionId}")
    public Result<QuestionSummaryResponseDTO> duplicate(@RequestParam("questionId") UUID questionId) {
        return questionService.duplicateQuestion(questionId);
    }

    @PostMapping("{surveyId}")
    public Result<QuestionSummaryResponseDTO> add(@RequestParam("surveyId") UUID surveyId, @RequestBody EditQuestionRequestDTO request) {
        return questionService.addQuestion(surveyId, request);
    }
}
