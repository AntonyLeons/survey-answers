package digital.and.bootcamp.surveyanswers.models;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Survey {
    private Map<String, PersonResponses> responses = new HashMap<>();
}
