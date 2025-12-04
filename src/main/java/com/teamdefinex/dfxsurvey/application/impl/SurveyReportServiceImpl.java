package com.teamdefinex.dfxsurvey.application.impl;

import com.teamdefinex.dfxsurvey.application.SurveyReportService;
import com.teamdefinex.dfxsurvey.data.AnswerRepository;
import com.teamdefinex.dfxsurvey.data.QuestionRepository;
import com.teamdefinex.dfxsurvey.data.SurveyRepository;
import com.teamdefinex.dfxsurvey.domain.admin.QuestionType;
import com.teamdefinex.dfxsurvey.domain.survey.Survey;
import com.teamdefinex.dfxsurvey.dto.report.OptionPercentageDTO;
import com.teamdefinex.dfxsurvey.dto.report.QuestionReportDTO;
import com.teamdefinex.dfxsurvey.dto.report.SurveyReportDTO;
import com.teamdefinex.dfxsurvey.dto.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.teamdefinex.dfxsurvey.application.impl.SurveyServiceImpl.getUserId;

@Service
@RequiredArgsConstructor
public class SurveyReportServiceImpl implements SurveyReportService {
    AnswerRepository answerRepository;
    QuestionRepository questionRepository;
    SurveyRepository surveyRepository;
    @Override
    public Result<SurveyReportDTO> getSurveyReport(UUID surveyId, Authentication authentication) {

        Optional<Survey> surveyOptional = surveyRepository.findById(surveyId);

        if(surveyOptional.isEmpty()) {
            return Result.failure("Survey not found");
        }

        Survey survey = surveyOptional.get();

        String userId = getUserId(authentication);

        if(!survey.getOwnerId().equals(userId)) {
            return Result.failure("You are not owner of this Survey");
        }

        var answers = answerRepository.findBySurveyId(surveyId);
        var questions = questionRepository.findAllBySurvey_Id(surveyId);

        List<QuestionReportDTO> questionReports = new ArrayList<>();

        for (var q : questions) {

            // FREETEXT sorular rapora eklenmez
            if (q.getType() == QuestionType.FREETEXT) {
                continue;
            }

            // Bu soruya gelen cevaplar
            var questionAnswers = answers.stream()
                    .filter(a -> a.getQuestionId().equals(q.getId()))
                    .toList();

            int totalAnswers = questionAnswers.size();

            List<OptionPercentageDTO> optionPercentages = new ArrayList<>();

            for (String option : q.getOptions()) {

                long count = questionAnswers.stream()
                        .filter(a -> a.getAnswer().equals(option))
                        .count();

                double percent = totalAnswers == 0
                        ? 0
                        : (count * 100.0) / totalAnswers;

                optionPercentages.add(
                        OptionPercentageDTO.builder()
                                .optionText(option)
                                .count(count)
                                .percent(percent)
                                .build()
                );
            }

            questionReports.add(
                    QuestionReportDTO.builder()
                            .questionId(q.getId().toString())
                            .questionText(q.getQuestion())
                            .type(q.getType().name())
                            .totalAnswers(totalAnswers)
                            .optionPercentages(optionPercentages)
                            .build()
            );
        }

        SurveyReportDTO surveyReportDTO = SurveyReportDTO.builder()
                .questions(questionReports)
                .build();
        return Result.success(surveyReportDTO);
    }

}