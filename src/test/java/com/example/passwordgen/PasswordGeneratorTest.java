package com.example.passwordgen;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordGeneratorTest {

    @Test
    void generateIncludesAllSelectedTypes() {
        String password = PasswordGenerator.generate(16, true, true, true, true);

        assertTrue(password.chars().anyMatch(c -> Character.isUpperCase(c)));
        assertTrue(password.chars().anyMatch(c -> Character.isLowerCase(c)));
        assertTrue(password.chars().anyMatch(c -> Character.isDigit(c)));
        assertTrue(password.chars().anyMatch(c -> "!@#$%^&*()-_=+[]{}|;:,.<>?".indexOf(c) >= 0));
    }

    @Test
    void generateWithDigitsOnlyProducesDigits() {
        String password = PasswordGenerator.generate(12, false, false, true, false);

        assertEquals(12, password.length());
        assertTrue(password.chars().allMatch(Character::isDigit));
    }

    @Test
    void generateWithoutSelectionThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> PasswordGenerator.generate(10, false, false, false, false));

        assertEquals("Pelo menos um tipo de caractere deve ser selecionado.", exception.getMessage());
    }

    @Test
    void generateWithShortLengthThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> PasswordGenerator.generate(1, true, true, false, false));

        assertEquals("O tamanho da senha deve ser pelo menos igual ao número de tipos selecionados.", exception.getMessage());
    }
}
