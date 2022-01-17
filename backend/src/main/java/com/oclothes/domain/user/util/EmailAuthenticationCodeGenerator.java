package com.oclothes.domain.user.util;

import java.util.Random;

public class EmailAuthenticationCodeGenerator {
    private static final int authCodeLength = 8;
    private static final char[] characterTable = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };

    public static String generateAuthCode() {
        Random random = new Random(System.currentTimeMillis());
        StringBuilder buffer = new StringBuilder();
        for(int i = 0; i < authCodeLength; i++) buffer.append(characterTable[random.nextInt(characterTable.length)]);
        return buffer.toString();
    }
}
