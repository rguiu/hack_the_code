package com.aluzine.randory.hackthecode.user;

import lombok.Getter;
import lombok.Setter;

@Getter
public class User implements Comparable<User> {
    private String name;
    private String url;
    @Setter
    private double score;
    @Setter
    private int length;
    @Setter
    private double solved;

    public User(String name, String url) {
        this.name = name;
        this.url = url;
    }

    @Override
    public int compareTo(User o) {
        if (o.getScore() > getScore()) return 1;
        if (o.getScore() < getScore()) return -1;
        return 0;
    }
}
