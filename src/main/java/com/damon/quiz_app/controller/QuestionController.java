package com.damon.quiz_app.controller;

import com.damon.quiz_app.model.Question;
import com.damon.quiz_app.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
@Validated
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        List<Question> allQuestions = questionService.getAllQuestions();
        return new ResponseEntity<>(allQuestions, HttpStatus.OK);
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
        List<Question> questions = questionService.getQuestionsByCategory(category);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody @Valid Question question){
        String response = questionService.addQuestion(question);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
