package digital.and.bootcamp.surveyanswers;

import digital.and.bootcamp.surveyanswers.models.AnswerRequest;
import digital.and.bootcamp.surveyanswers.models.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SurveyAnswersApplicationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void testSubmitSurveyAnswers() {
        String surveyId = UUID.randomUUID().toString();
        String personId = UUID.randomUUID().toString();
        AnswerRequest answerRequest = AnswerRequest.builder()
                .surveyId(surveyId)
                .personId(personId)
                .question("How are you?")
                .answer("Good thanks")
                .build();
        ResponseEntity<Void> response = testRestTemplate.postForEntity("http://localhost:" + port + "/results/submit", answerRequest, Void.class);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetResults() {
        String surveyId = UUID.randomUUID().toString();
        for (int i = 0; i < 10; i++) {
            String personId = UUID.randomUUID().toString();
            AnswerRequest answerRequest = AnswerRequest.builder()
                    .surveyId(surveyId)
                    .personId(personId)
                    .question(i % 4 == 0 ? "Hey, hows it going?" : "How are you?")
                    .answer(i % 3 == 0 ? "Good thanks" : "Not bad")
                    .build();
            testRestTemplate.postForEntity("http://localhost:" + port + "/results/submit", answerRequest, Void.class);
        }
        Question[] questions = testRestTemplate.getForObject("http://localhost:" + port + "/results/" + surveyId, Question[].class);
        assertEquals(2, questions.length);
        assertTrue(Arrays.stream(questions).anyMatch(question -> question.getQuestion().equals("Hey, hows it going?")));
        assertTrue(Arrays.stream(questions).anyMatch(question -> question.getQuestion().equals("How are you?")));
    }


}
