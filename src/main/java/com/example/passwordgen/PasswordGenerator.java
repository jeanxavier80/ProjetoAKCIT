package com.example.passwordgen;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class PasswordGenerator {
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+[]{}|;:,.<>?";
    private static final SecureRandom RANDOM = new SecureRandom();

    private PasswordGenerator() {
        // Utilitário estático, não deve ser instanciado.
    }

    public static String generate(int length, boolean includeUpper, boolean includeLower,
                                  boolean includeDigits, boolean includeSymbols) {
        if (length <= 0) {
            throw new IllegalArgumentException("O tamanho deve ser maior que zero.");
        }

        StringBuilder available = new StringBuilder();
        List<Character> required = new ArrayList<>();

        if (includeLower) {
            available.append(LOWERCASE);
            required.add(randomCharFrom(LOWERCASE));
        }
        if (includeUpper) {
            available.append(UPPERCASE);
            required.add(randomCharFrom(UPPERCASE));
        }
        if (includeDigits) {
            available.append(DIGITS);
            required.add(randomCharFrom(DIGITS));
        }
        if (includeSymbols) {
            available.append(SYMBOLS);
            required.add(randomCharFrom(SYMBOLS));
        }

        if (available.isEmpty()) {
            throw new IllegalArgumentException("Pelo menos um tipo de caractere deve ser selecionado.");
        }
        if (length < required.size()) {
            throw new IllegalArgumentException("O tamanho da senha deve ser pelo menos igual ao número de tipos selecionados.");
        }

        List<Character> passwordChars = new ArrayList<>(required);
        for (int i = passwordChars.size(); i < length; i++) {
            passwordChars.add(randomCharFrom(available.toString()));
        }

        Collections.shuffle(passwordChars, RANDOM);
        StringBuilder password = new StringBuilder(length);
        for (char c : passwordChars) {
            password.append(c);
        }

        return password.toString();
    }

    private static char randomCharFrom(String source) {
        int index = random_int(source.length());
        return source.charAt(index);
    }

    private static int random_int(int bound) {
        return RANDOM.nextInt(bound);
    }
}
