package com.teamdefinex.dfxsurvey.dto.report;

import lombok.*;

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