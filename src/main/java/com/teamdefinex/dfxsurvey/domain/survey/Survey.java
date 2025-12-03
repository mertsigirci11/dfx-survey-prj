package com.teamdefinex.dfxsurvey.domain.survey;

import com.teamdefinex.dfxsurvey.domain.admin.Questions;
import com.teamdefinex.dfxsurvey.domain.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "survey")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Survey extends BaseEntity {
    private String ownerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SurveyStatus status;
    private LocalDateTime expiresAt;

    private String title;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "participants")
    private String participants;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<Questions> questions = new ArrayList<>();

    @Transient
    public List<String> getParticipants() {
        if (this.participants == null || this.participants.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(this.participants.split(","))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Transient
    public void setParticipants(List<String> participantsList) {
        if (participantsList == null || participantsList.isEmpty()) {
            this.participants = null;
        } else {
            this.participants = String.join(",", participantsList);
        }
    }

    public Survey duplicate() {
        Survey copy = new Survey();

        // Copy basic fields
        copy.setOwnerId(this.ownerId);
        copy.setStatus(this.status);
        copy.setExpiresAt(this.expiresAt);
        copy.setTitle(this.title);
        copy.setParticipants(this.getParticipants());

        if (this.questions != null && !this.questions.isEmpty()) {
            List<Questions> copiedQuestions = new ArrayList<>();
            for (Questions question : this.questions) {
                Questions copiedQuestion = question.duplicate();
                copiedQuestion.setSurvey(copy);
                copiedQuestions.add(copiedQuestion);
            }
            copy.setQuestions(copiedQuestions);
        }

        return copy;
    }
}
