package com.teamdefinex.dfxsurvey.application;

import com.teamdefinex.dfxsurvey.dto.report.SurveyReportDTO;
import com.teamdefinex.dfxsurvey.dto.result.Result;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface SurveyReportService {
    Result<SurveyReportDTO> getSurveyReport(UUID surveyId, Authentication authentication);
}
