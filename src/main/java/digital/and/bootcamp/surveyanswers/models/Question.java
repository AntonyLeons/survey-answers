package digital.and.bootcamp.surveyanswers.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class Question {
    private String question;
    private Map<String, Integer> answers;
    private int total = 0;
}
