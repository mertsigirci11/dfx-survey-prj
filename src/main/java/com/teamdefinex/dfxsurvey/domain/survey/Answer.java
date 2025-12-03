package com.teamdefinex.dfxsurvey.domain.survey;

import com.teamdefinex.dfxsurvey.domain.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Answer extends BaseEntity {
    private String participantToken;
    private UUID surveyId;
    private UUID questionId;
    private String answer;
}