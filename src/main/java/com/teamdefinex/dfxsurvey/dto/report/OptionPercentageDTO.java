package com.teamdefinex.dfxsurvey.dto.report;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OptionPercentageDTO {

    private String optionText;
    private double percent;
    private long count;
}