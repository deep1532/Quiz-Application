package com.damon.quiz_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Quiz title cannot be empty.")
    private String title;

    @ManyToMany
    @NotEmpty(message = "Quiz must have at least one question.")
    private List<Question> questions;

}
