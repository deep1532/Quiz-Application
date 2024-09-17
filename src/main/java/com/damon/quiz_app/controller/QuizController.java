package com.damon.quiz_app.controller;

import com.damon.quiz_app.model.QuestionWrapper;
import com.damon.quiz_app.model.QuizResponse;
import com.damon.quiz_app.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
@Validated
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam @Valid String category, @RequestParam @Valid Integer numOfQuestions, @RequestParam @Valid String title){
        String response = quizService.createQuiz(category, numOfQuestions, title);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("getQuiz/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsByQuizId(@PathVariable Integer id){
        List<QuestionWrapper> questions = quizService.getQuestionsByQuizId(id);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @PostMapping("calculateScore/{id}")
    public ResponseEntity<Integer> calculateQuizScore(@PathVariable Integer id, @RequestBody List<QuizResponse> quizResponse){
        Integer score = quizService.calculateQuizScore(id, quizResponse);
        return new ResponseEntity<>(score, HttpStatus.OK);
    }
}
