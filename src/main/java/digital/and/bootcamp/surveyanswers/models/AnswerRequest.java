package digital.and.bootcamp.surveyanswers.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class AnswerRequest {
    private String surveyId;
    private String personId;
    private String question;
    private String answer;
}
