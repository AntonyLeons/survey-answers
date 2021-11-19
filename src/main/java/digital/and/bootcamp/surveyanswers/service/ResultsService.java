package digital.and.bootcamp.surveyanswers.service;

import digital.and.bootcamp.surveyanswers.models.AnswerRequest;
import digital.and.bootcamp.surveyanswers.models.PersonResponses;
import digital.and.bootcamp.surveyanswers.models.Question;
import digital.and.bootcamp.surveyanswers.models.Survey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ResultsService {
    private final Map<String, Survey> surveyMap = new HashMap<>();

    public void updateResponse(AnswerRequest answerRequest) {
        Survey survey = surveyMap.getOrDefault(answerRequest.getSurveyId(), new Survey());
        Map<String, PersonResponses> responses = survey.getResponses();
        PersonResponses personResponses = responses.getOrDefault(answerRequest.getPersonId(), new PersonResponses());
        Map<String, String> questionAndAnswer = personResponses.getQuestionAndAnswer();
        questionAndAnswer.put(answerRequest.getQuestion(), answerRequest.getAnswer());
        personResponses.setQuestionAndAnswer(questionAndAnswer);
        responses.put(answerRequest.getPersonId(), personResponses);
        survey.setResponses(responses);
        surveyMap.put(answerRequest.getSurveyId(), survey);
    }

    public List<Question> getSurveyResults(String surveyId) {
        Map<String, Question> questionMap = new HashMap<>();
        Survey survey = surveyMap.get(surveyId);
        if(Objects.isNull(survey)) {
            return Collections.emptyList();
        }
        survey.getResponses().values().stream().map(PersonResponses::getQuestionAndAnswer).forEach(personAnswers -> {
            personAnswers.forEach((question, answer) -> {
                if (questionMap.containsKey(question)) {
                    Question questionAndAnswer = questionMap.get(question);
                    Map<String, Integer> answers = questionAndAnswer.getAnswers();
                    answers.compute(answer, (answerKey, count) -> Objects.isNull(count) ? 1 : count + 1);
                    questionAndAnswer.setTotal(answers.values().stream().mapToInt(Integer::intValue).sum());
                    questionAndAnswer.setAnswers(answers);
                } else {
                    Map<String, Integer> answers = new HashMap<>();
                    answers.put(answer, 1);
                    questionMap.put(question, Question.builder()
                            .question(question)
                            .answers(answers)
                            .build());
                }
            });
        });
        return new ArrayList<>(questionMap.values());
    }
}
