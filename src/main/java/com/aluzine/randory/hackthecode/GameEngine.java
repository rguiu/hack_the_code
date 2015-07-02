package com.aluzine.randory.hackthecode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aluzine.randory.hackthecode.question.Question;
import com.aluzine.randory.hackthecode.question.QuestionGenerator;
import com.aluzine.randory.hackthecode.question.QuestionScore;
import com.aluzine.randory.hackthecode.question.ResponseValidator;
import com.aluzine.randory.hackthecode.user.FetchUserResponse;
import com.aluzine.randory.hackthecode.user.StatResponse;
import com.aluzine.randory.hackthecode.user.User;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GameEngine extends TimerTask {

    final static Logger log = LoggerFactory.getLogger(GameEngine.class);
    private Map<String, User> users = new HashMap<>();

    private static final long initTime = System.currentTimeMillis();
    private static final long TIME_TO_EXTENDED = 30 * 60 * 1000;

    private static final long QUESTION_BLOCK = 2 * 60 * 1000;

    public void addUser(String name, User user) {
        synchronized(users) {
            log.info("Adding user: {} {}", name, user.getUrl());
            log.info("Adding userobd: {} {}", user.getName(), user.getUrl());
            if (users.containsKey(name)) {
                throw new RuntimeException("User already exit");
            }
            users.put(name, user);
        }
    }

    public void run() {
        log.info("Starting engine questions round: " + numberOfQuestions());
        Multimap<String,QuestionScore> responses = ArrayListMultimap.create();

        final List<Question> questions = QuestionGenerator.getQuestions(numberOfQuestions());
        log.info("Got {} questions", questions.size());
        List<User> localUsers = getUsers();

        System.out.println(localUsers.size());
        for (Question q:questions) {
            List<QuestionScore> questionScores = localUsers.stream().parallel().map(u -> check(u,q)).collect(Collectors.toList());
            System.out.println(questionScores.size());
            if (questionScores.size()>1) {
                double divisor = questionScores.size() - 1;
                for (int i = 0; i < questionScores.size(); i++) {

                    for (int j = i + 1; j < questionScores.size(); j++) {
                        if (i == j) continue;
                        if (questionScores.get(i).isSolved() &&
                                (!questionScores.get(j).isSolved() || questionScores.get(j).getCode().length() >  questionScores.get(i).getCode().length())) {
                            questionScores.get(i).addScore(1.0/divisor);
                        } else if (questionScores.get(i).isSolved() &&
                                (!questionScores.get(j).isSolved() || questionScores.get(j).getCode().length() ==  questionScores.get(i).getCode().length())) {
                            questionScores.get(i).addScore(0.5/divisor);
                            questionScores.get(j).addScore(0.5/divisor);
                        } else if (questionScores.get(j).isSolved()) {
                            questionScores.get(j).addScore(1.0/divisor);
                        }
                    }
                }
            }
            questionScores.stream().forEach(qu -> responses.put(qu.getUsername(), qu));
        }

        localUsers.stream().parallel()
                  .forEach(u -> updateScoreAndSendStats(u, responses.get(u.getName())));
    }

    public String rankedUsersAsJson() {
        List<User> rUsers = getUsers();
        Collections.sort(rUsers);
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclStrat())
                .create();
        return gson.toJson(rUsers);
    }

    private int numberOfQuestions() {
        return 2 + (int)((System.currentTimeMillis() - initTime) / QUESTION_BLOCK);
    }

    public boolean isTimeForExtendedInstructions() {
        return System.currentTimeMillis() - initTime > TIME_TO_EXTENDED;
    }

    private void updateScoreAndSendStats(User user, Collection<QuestionScore>scores) {
        final StatResponse statResponse = new StatResponse(scores);
        user.setScore(statResponse.getScore());
        user.setLength(statResponse.getLength());
        long countCorrect = scores.stream().filter(s -> s.isSolved()).count();
        user.setSolved((countCorrect*100)/scores.size());
        FetchUserResponse.sendStats(user.getUrl(),statResponse);
    }

    private QuestionScore check(final User u, final Question question) {
        final QuestionScore.QuestionScoreBuilder questionScoreBuilder = QuestionScore.builder().magicWords(question.getQuestion())
                                                                                 .username(u.getName());
        try {
            log.info("Asking {}: {}", u.getName(), question.getQuestion());
            String response = FetchUserResponse.callRemote(u.getUrl(), question.getQuestion());
            questionScoreBuilder.code(response);
            if (ResponseValidator.verifier(response, question.getQuestion())) {
                questionScoreBuilder.solved(true).details("OK");
            }
        } catch (Exception e) {
//            e.printStackTrace();
            log.info("ERROR: {}", e.getMessage());
            questionScoreBuilder.details(e.getMessage());
        }
        return questionScoreBuilder.hint(question.getHint()).build();
    }

    private List<User> getUsers() {
        synchronized (users) {
            return new ArrayList<>(users.values());
        }
    }

    class ExclStrat implements ExclusionStrategy {
        public boolean shouldSkipClass(Class<?> arg0) {
            return false;
        }
        public boolean shouldSkipField(FieldAttributes f) {
            return (f.getDeclaringClass() == User.class && f.getName().equals("url"));
        }
    }
}
