package com.aluzine.randory.hackthecode.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.aluzine.randory.hackthecode.question.QuestionScore;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StatResponse {
    private List<QuestionScore> questions;
    private double score;
    private int length;

    public StatResponse() {
    }

    public StatResponse(Collection<QuestionScore> questions) {
        this(questions,
                questions.stream().mapToDouble(QuestionScore::getScore).sum(),
                questions.stream().filter(q -> q.isSolved()).mapToInt(q -> q.getCode().length()).sum()
        );
    }

    public StatResponse(Collection<QuestionScore> questions, double score, int length) {
        this.questions = new ArrayList<>(questions);
        this.score = score;
        this.length = length;
    }
}
