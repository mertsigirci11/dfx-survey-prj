package com.teamdefinex.dfxsurvey.data;

import com.teamdefinex.dfxsurvey.domain.survey.Survey;
import com.teamdefinex.dfxsurvey.domain.survey.SurveyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, UUID> {
    Page<Survey> findAllByOwnerId(String ownerId, Pageable pageable);
    String findParticipantById(UUID id);

    @Query("SELECT s.status FROM Survey s WHERE s.id = :id")
    SurveyStatus findStatusById(@Param("id") UUID id);
}
