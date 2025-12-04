package com.teamdefinex.dfxsurvey.data;

import com.teamdefinex.dfxsurvey.domain.admin.Questions;
import com.teamdefinex.dfxsurvey.domain.survey.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Questions, UUID> {
    @Query("SELECT COALESCE(MAX(q.questionOrder), 1) FROM Questions q WHERE q.survey.id = :surveyId")
    int findMaxOrderBySurveyId(@Param("surveyId") UUID surveyId);

    List<Questions> findAllBySurvey_Id(@Param("surveyId") UUID surveyId);

}