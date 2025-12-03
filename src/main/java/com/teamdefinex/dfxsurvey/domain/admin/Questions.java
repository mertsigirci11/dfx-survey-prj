package com.teamdefinex.dfxsurvey.domain.admin;

import com.teamdefinex.dfxsurvey.domain.base.BaseEntity;
import com.teamdefinex.dfxsurvey.domain.survey.Survey;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "questions")
@Getter
@Setter
public class Questions extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @Enumerated(EnumType.STRING)
    private QuestionType type;
    private LocalDateTime expiresAt;
    private String question;
    private Integer questionOrder;
}
