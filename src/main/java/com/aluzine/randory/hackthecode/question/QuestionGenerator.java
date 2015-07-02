package com.aluzine.randory.hackthecode.question;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public class QuestionGenerator {

    private static Random r = new Random();

    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LETTERS_EXT = " ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static List<Supplier<Question>> questions = Arrays.asList(
            () -> new Question(1,"AZ", "Two letters word"),
            () -> new Question(2, oneOf("MINAS","SANTO","PICAS", "PUNTO"),"Sample 1"),
            () -> new Question(3, shuffleWords("UMNE TALMAR RAHTAINE NIXENEN UMIR"),"Short spell"),
            () -> new Question(4, repeatLetterNTimes(15),"One letter x 15"),
            () -> new Question(5, "BABCDEDCBABCDCB","Close letters"),
            () -> new Question(6, "ZAZYA YAZ","Edge of the alphabet"),
            () -> new Question(7, repeatLetterNTimes(31),"One letter x 31"),
            () -> new Question(8, "NONONONONONONONONONONONONONONONONONONONO","Two letter word x 20"),
            () -> new Question(9, "GUZ MUG ZOG GUMMOG ZUMGUM ZUM MOZMOZ MOG ZOGMOG GUZMUGGUM", "Far away letters"),
            () -> new Question(10, repeatLetterNTimes(53) + repeatLetterNTimes(28),"One letter x 53 + One letter x28"),
            () -> new Question(11, repeatLetterNTimes(70),"One letter x 70"),
            () -> new Question(12, "GAAVOOOLLUGAAVOOOLLUGAAVOOOLLUGAAVOOOLLUGAAVOOOLLUGAAVOOOLLUGAAVOOOLLUGAAVOOOLLU","ten letter word x 8"),
            () -> new Question(13, "O OROFARNE LASSEMISTA CARNIMIRIE O ROWAN FAIR UPON YOUR HAIR HOW WHITE THE BLOSSOM LAY","Medium spell"),
            () -> new Question(14, "ALROG BALROG BALROG BALROG BALROG BALROG BALROG BALROG BALROG BALROG BALROG BALROG BALROG BALROG BALROG BALROG BALROG BALROG BALROG BALROG BALROG BALROG BALROG BALROG BALROG BALROG B","Seven letter word x 26"),
            () -> new Question(15, "OYLO Y OOOYYY LLLYOOYY O YO YLOO O OLY YL OY L YY L YOO LYL YYYOOYLOL L Y O YYYLLOY O L YYYOOYLOL YOLOLOY","Long random sequence of 4 letters"),
            () -> new Question(16, "TUVWXYZ ABCDEFGHIJ","Incremental sequence of 18 letters"),
            () -> new Question(17, entireAlphabetSeparatedBy1Letter(),"Entire alphabet x 11 separated by 1 letter"),
            () -> new Question(18, sameLetterPlusRandomLetter32(),"Same latter + random letter x 32"),
            () -> new Question(19, "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z", "Incremental space separated sequence"),
            () -> new Question(20, patternFollowedByOtherPattern(),"Pattern followed by another pattern"),
            () -> new Question(21, "GY GY GY GY GY GY GY GY GY GY GY GY GY GY GY GY GY GY GY GY GY GY GY GY GY GY GY GY HIJIHIJIJIJIHIHIHIJIHIJIHIJIJIJIHHHIJIJHIHH","Pattern followed by random sequence of two letters"),
            () -> new Question(22, "MELLON MORIAMELLON MORIAMORIAMORIAMELLON MELLON MELLON MORIAMORIAMELLON MELLON MORIA","Two patterns repeated randomly"),
            () -> new Question(23, "ZAZAZAZAZAZAZAZAZAZAZAZAZAZAZAZAZAZAZAZACEGIKMOQSUWY BDFHJLNPRTVXZACEGIKMOQSUWY BDFHJLNPRTVXZA", "Pattern followed by incremental sequence"),
            () -> new Question(24, "THREE RINGS FOR THE ELVEN KINGS UNDER THE SKY SEVEN FOR THE DWARF LORDS IN THEIR HALLS OF STONE NINE FOR MORTAL MEN DOOMED TO DIE ONE FOR THE DARK LORD ON HIS DARK THRONEIN THE LAND OF MORDOR WHERE THE SHADOWS LIE ONE RING TO RULE THEM ALL ONE RING TO FIND THEM ONE RING TO BRING THEM ALL AND IN THE DARKNESS BIND THEM IN THE LAND OF MORDOR WHERE THE SHADOWS LIE","Long spell")
    );

    private static String oneOf(String ...words) {
        return words[r.nextInt(words.length)];
    }

    private static String shuffleWords(String s) {
        List<String> words= Arrays.asList(s.split(" "));
        Collections.shuffle(words);

        return words.stream().collect(Collectors.joining(" "));
    }

    private static String repeatLetterNTimes(int n) {
        char c = LETTERS.charAt(r.nextInt(LETTERS.length()));
        return StringUtils.repeat(c, n);
    }

    private static String entireAlphabetSeparatedBy1Letter() {
        char c = LETTERS.charAt(r.nextInt(LETTERS.length()));
        String yu = LETTERS + c;
        return StringUtils.repeat(yu, 11);
    }

    private static String sameLetterPlusRandomLetter32() {
        char c = LETTERS.charAt(r.nextInt(LETTERS.length()));
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            char d = LETTERS_EXT.charAt(r.nextInt(LETTERS_EXT.length()));
            b.append(c).append(d);
        }
        return b.toString();
    }

    private static String patternFollowedByOtherPattern() {
        String [] patterns = new String[] {"FIFO"," FUM", "COSA", "MAQ", "BILB", "MUMY", "QUES"};
        String res = StringUtils.repeat(patterns[r.nextInt(patterns.length)], 11 + r.nextInt(3)) +
                StringUtils.repeat(patterns[r.nextInt(patterns.length)], 12 + r.nextInt(5));
        return res;
    }
    public static List<Question> getQuestions(int n) {
        return questions.stream().map(Supplier::get).limit(n).collect(Collectors.toList());
    }
}
