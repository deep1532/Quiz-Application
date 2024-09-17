package com.damon.quiz_app.service;

import com.damon.quiz_app.model.Question;
import com.damon.quiz_app.dao.QuestionDao;
import com.damon.quiz_app.dao.QuizDao;
import com.damon.quiz_app.model.QuestionWrapper;
import com.damon.quiz_app.model.Quiz;
import com.damon.quiz_app.model.QuizResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;


    public String createQuiz(String category, Integer numOfQuestions, String title) {
        try{
            List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numOfQuestions);

            if (questions.size() < numOfQuestions) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough questions in the category.");
            }

            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestions(questions);
            quizDao.save(quiz);

            return "Quiz is created successfully.";
        } catch(ConstraintViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input: " + e.getMessage(), e);
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create a quiz.", e);
        }
    }

    public List<QuestionWrapper> getQuestionsByQuizId(Integer id) {
        try {
            Optional<Quiz> quiz = quizDao.findById(id);

            if (quiz.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found");
            }

            List<Question> questionsFromDB = quiz.get().getQuestions();
            List<QuestionWrapper> questionsOfQuiz = new ArrayList<>();

            for (Question q : questionsFromDB) {
                QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
                questionsOfQuiz.add(qw);
            }

            return questionsOfQuiz;

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch a quiz. ", e);
        }
    }

    public Integer calculateQuizScore(Integer id, List<QuizResponse> quizResponse) {
        try {
            Optional<Quiz> quiz = quizDao.findById(id);

            if (quiz.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found");
            }

            List<Question> questions = quiz.get().getQuestions();

            int i = 0, score = 0;
            for (QuizResponse qr : quizResponse) {
                if (qr.getResponseFromUser().equals(questions.get(i).getRightAnswer()))
                    score++;
                i++;
            }
            return score;

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to calculate score for the quiz.", e);
        }
    }
}
