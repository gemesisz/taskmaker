package hu.gemesi.taskmaker.common.util.random;

import java.util.Random;

public class RandomUtil {

    // [a-z]
    private static final char[] LOWERCASE;
    // [A-Z]
    private static final char[] UPPERCASE;
    // [0-9a-zA-Z]
    private static final char[] ALL_LETTER;
    private static int generatedIndex = 0;
    private static final Random RANDOM = new Random();
    // [0-9a-zA-Z]
    private static final int MAX_NUM_SYS = 62;

    static {
        UPPERCASE = new char[26];
        for (int i = 65; i < 65 + 26; i++) {
            UPPERCASE[i - 65] = (char) i;
        }
        LOWERCASE = new char[26];
        for (int i = 97; i < 97 + 26; i++) {
            LOWERCASE[i - 97] = (char) i;
        }
        ALL_LETTER = new char[MAX_NUM_SYS];
        for (int i = 48; i < 48 + 10; i++) {
            ALL_LETTER[i - 48] = (char) i;
        }
        for (int i = 10; i < 10 + 26; i++) {
            ALL_LETTER[i] = UPPERCASE[i - 10];
        }
        for (int i = 10 + 26; i < 10 + 26 + 26; i++) {
            ALL_LETTER[i] = LOWERCASE[i - 10 - 26];
        }
    }

    public static String generateId() {
        int length = 0;
        generatedIndex++;
        if (generatedIndex > 1296) {
            generatedIndex = 0;
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (length < 20) {
            var result = ALL_LETTER[RANDOM.nextInt(1296 + generatedIndex) % ALL_LETTER.length];
            stringBuilder.append(result);
            length++;
        }

        return stringBuilder.toString();
    }
}
