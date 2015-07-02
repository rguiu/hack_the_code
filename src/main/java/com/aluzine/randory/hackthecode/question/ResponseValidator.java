package com.aluzine.randory.hackthecode.question;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ResponseValidator {
    public static boolean verifier(String code, String magicPhrase) {
        if (code.length()>4000) {
            throw new RuntimeException("Incorrect solution. More than 4000 instructions.");
        }
        char runes[] = new char[30];
        int position = 0;
        Arrays.fill(runes, ' ');

        StringBuffer solution = new StringBuffer();

        Set<String> memo =new HashSet<>();

        int index = 0;
        while(index<code.length()) {
            String key = getVisitedKey(runes, position, index, solution.length());
            if (memo.contains(key)) {
                throw new RuntimeException("Infinite cycle");
            }
            memo.add(key);
            if(code.charAt(index) == '[') {
                if (runes[position] == ' ') {
                    while (code.charAt(index) != ']') {
                        index++;
                    }
                    continue;
                }
            } else if (code.charAt(index) == ']') {
                if (runes[position] == ' ') {
                    index++;
                    continue;
                } else {
                    while (code.charAt(index) != '[') {
                        index--;
                    }
                    continue;
                }
            } else if (code.charAt(index) == '+') {
                String LETTERS = " ABCDEFGHIJKLMNOPQRSTUVWXYZ ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                int u = LETTERS.indexOf(runes[position]);
                runes[position] = LETTERS.charAt(u + 1);
            } else if (code.charAt(index) == '-') {
                String LETTERS = " ZYXWVUTSRQPONMLKJIHGFEDCBA ZYXWVUTSRQPONMLKJIHGFEDCBA";
                int u = LETTERS.indexOf(runes[position]);
                runes[position] = LETTERS.charAt(u + 1);
            } else if (code.charAt(index) == '>') {
                position++;
                if (position==30) position = 0;
            } else if (code.charAt(index) == '<') {
                position--;
                if (position==-1) position = 29;
            } else if (code.charAt(index) == '.') {
                solution.append(runes[position]);
            }
            if (solution.length()>magicPhrase.length()) {
                throw new RuntimeException(solution.toString()+"...");
            }
            index++;
        }
        if (!magicPhrase.equals(solution.toString())) {
            System.out.println("GOT: " + solution);
        }
        System.out.println("MAF: " + magicPhrase);
        System.out.println(code);
        if (!magicPhrase.equals(solution.toString())) {
            throw new RuntimeException(solution.toString());
        }
        return true;
    }

    private static String getVisitedKey(char runes[], int position, int index, int solutionLength) {
        return Arrays.toString(runes) + "_" + position + "_" + index + "_" + solutionLength;
    }

}
