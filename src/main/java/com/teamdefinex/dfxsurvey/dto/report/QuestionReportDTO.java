package com.teamdefinex.dfxsurvey.dto.report;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionReportDTO {

    private String questionId;
    private String questionText;
    private String type;
    private int totalAnswers;
    private List<String> answers;

    private List<OptionPercentageDTO> optionPercentages;
}
