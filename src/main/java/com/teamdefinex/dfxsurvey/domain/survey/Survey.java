package com.teamdefinex.dfxsurvey.domain.survey;

import com.teamdefinex.dfxsurvey.domain.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;

import java.time.LocalDateTime;
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
    private SurveyStatus status;
    private LocalDateTime expiresAt;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String participants;

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

