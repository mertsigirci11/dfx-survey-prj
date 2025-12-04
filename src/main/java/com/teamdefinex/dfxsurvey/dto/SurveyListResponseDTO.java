package com.teamdefinex.dfxsurvey.dto;

import lombok.Data;

import java.util.List;

@Data
public class SurveyListResponseDTO {
    List<SurveySummaryResponseDTO> surveys;
    private int totalPages;
    private long totalElements;
}
