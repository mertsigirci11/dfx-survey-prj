package com.teamdefinex.dfxsurvey.data;

import com.teamdefinex.dfxsurvey.domain.survey.SurveyParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SurveyParticipantRepository extends JpaRepository<SurveyParticipant, UUID> {
    SurveyParticipant findByToken(String token);
}
