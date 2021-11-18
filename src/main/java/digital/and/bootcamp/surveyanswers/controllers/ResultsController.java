package digital.and.bootcamp.surveyanswers.controllers;

import digital.and.bootcamp.surveyanswers.models.AnswerRequest;
import digital.and.bootcamp.surveyanswers.models.Question;
import digital.and.bootcamp.surveyanswers.service.ResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/results")
public class ResultsController {
    @Autowired
    ResultsService resultsService;

    @PostMapping
    @RequestMapping(path = "/submit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> submitAnswer(@RequestBody AnswerRequest answerRequest) {
        resultsService.updateResponse(answerRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @RequestMapping(path = "/{surveyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Question> getResults(@PathVariable String surveyId) {
        return resultsService.getSurveyResults(surveyId);
    }

}
