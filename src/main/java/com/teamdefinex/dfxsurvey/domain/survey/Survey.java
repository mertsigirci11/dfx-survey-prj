package com.teamdefinex.dfxsurvey.domain.survey;

import com.teamdefinex.dfxsurvey.domain.admin.Questions;
import com.teamdefinex.dfxsurvey.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private String participants;

    @OneToMany
    private List<Questions> questions = new ArrayList<>();

    @Transient
    public List<String> getParticipants() {
        if (this.participants == null || this.participants.isEmpty()) {
            return List.of();
        }
        return Arrays.asList(this.participants.split(","));
    }

    @Transient
    public void setParticipants(List<String> participantsList) {
        if (participantsList == null || participantsList.isEmpty()) {
            this.participants = null;
        } else {
            this.participants = String.join(",", participantsList);
        }
    }
}
