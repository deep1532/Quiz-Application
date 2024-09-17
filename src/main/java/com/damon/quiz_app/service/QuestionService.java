package com.damon.quiz_app.service;

import com.damon.quiz_app.model.Question;
import com.damon.quiz_app.dao.QuestionDao;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public List<Question> getAllQuestions() {
        try {
            return questionDao.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve questions", e);
        }
    }

    public List<Question> getQuestionsByCategory(String category) {
        try {
            return questionDao.findByCategory(category);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve questions by category", e);
        }
    }

    public String addQuestion(Question question) {
        try{
            questionDao.save(question);
            return "question added successfully.";
        } catch(ConstraintViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Input: " + e.getMessage(), e);
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to add question", e);
        }
    }
}
