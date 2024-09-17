package com.damon.quiz_app.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class QuizResponse {

    private Integer id;
    private String responseFromUser;
}
