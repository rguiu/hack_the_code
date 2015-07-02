package com.aluzine.randory.hackthecode.question;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class QuestionScore implements Comparable<QuestionScore> {
    private String magicWords;
    private String code;
    private String details;
    private double score;
    private boolean solved;
    private String username;
    private String hint;

    @Override
    public int compareTo(QuestionScore o) {
        if (o.solved == solved && o.solved) {
            return code.length() - o.code.length();
        } else if (o.solved) {
            return 1;
        } else if (solved) {
            return -1;
        }
        return 0;
    }

    public void addScore(double d) {
        this.score += d;
    }
}
