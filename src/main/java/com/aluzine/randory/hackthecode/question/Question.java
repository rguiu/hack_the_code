package com.aluzine.randory.hackthecode.question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Question {
    private int id;
    private String question;
    private String hint;
}
