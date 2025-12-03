package com.teamdefinex.dfxsurvey.data;

import com.teamdefinex.dfxsurvey.domain.survey.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, UUID> {
    List<Answer> findBySurveyId(UUID surveyId);
    //List<Answer> findByQuestionId(UUID questionId);
}