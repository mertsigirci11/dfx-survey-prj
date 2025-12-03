package com.teamdefinex.dfxsurvey.domain.admin;

import com.teamdefinex.dfxsurvey.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "questions")
@Getter
@Setter

public class Questions extends BaseEntity {

    private String surveyId;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private QuestionType type;
    private LocalDateTime expiresAt;
    private String question;
    private Integer order;

}
