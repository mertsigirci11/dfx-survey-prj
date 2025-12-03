package com.teamdefinex.dfxsurvey.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SurveySummaryResponseDTO {
    private String id;
    private String title;
    private LocalDateTime expireDate;
    private String status;
    private Integer participantCount;
}
