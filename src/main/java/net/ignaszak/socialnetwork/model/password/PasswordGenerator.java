package net.ignaszak.socialnetwork.model.password;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class PasswordGenerator {

    /**
     * Default password length
     */
    private static final int LENGTH = 8;

    private static final String ALPHA = "abcdefgijklmnoprstuvxyz";
    private static final String NUMERIC = "0123456789";

    private SecureRandom random = new SecureRandom();

    /**
     * Generate password with default length
     * @return String
     */
    public String generate() {
        int numLength = random.nextInt(LENGTH - 2) + 1;
        int alphaLength = LENGTH - numLength;
        List<String> result = generatePartArray(alphaLength, ALPHA);
        result.addAll(generatePartArray(numLength, NUMERIC));
        Collections.shuffle(result);
        return String.join("", result);
    }

    private List<String> generatePartArray(int length, String dic) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < length; ++ i) result.add("" + dic.charAt(random.nextInt(dic.length())));
        return result;
    }
}
