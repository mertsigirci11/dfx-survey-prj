package com.teamdefinex.dfxsurvey.domain.survey;

import com.teamdefinex.dfxsurvey.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "survey_participant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class SurveyParticipant extends BaseEntity {
    private String token;
    private String email;
    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    private SurveyParticipantStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Survey survey;
}
