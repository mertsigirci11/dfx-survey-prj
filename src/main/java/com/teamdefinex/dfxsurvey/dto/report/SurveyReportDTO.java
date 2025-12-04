package com.teamdefinex.dfxsurvey.dto.report;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyReportDTO {

    private List<QuestionReportDTO> questions;

}