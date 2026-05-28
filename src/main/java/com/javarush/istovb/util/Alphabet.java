package com.javarush.istovb.util;

public class Alphabet {
    private static final char[] RUSSIAN_LOWERCASE = {
            'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п',
            'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я'
    };
    private static final char[] RUSSIAN_UPPERCASE = {
            'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П',
            'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я'
    };
    private static final char[] ENGLISH_LOWERCASE = {
            'a','b','c','d','e','f','g','h','i','j','k','l','m',
            'n','o','p','q','r','s','t','u','v','w','x','y','z'
    };
    private static final char[] ENGLISH_UPPERCASE = {
            'A','B','C','D','E','F','G','H','I','J','K','L','M',
            'N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
    };
    private static final char[] DIGITS = {
            '0','1','2','3','4','5','6','7','8','9'
    };
    private static final char[] PUNCTUATION = {
            '.', ',', ':', ';',
            '"', '\'', '(', ')',
            '-', '_', '/', '\\',
            '[', ']', '{', '}',
            '<', '>', '=', '+',
            '*', '&', '^', '%',
            '$', '#', '@', '~',
            '!','?',
    };

    public static final char[] ALPHABET_RU = concatenate(
            RUSSIAN_LOWERCASE, RUSSIAN_UPPERCASE,
            DIGITS, PUNCTUATION, new char[]{' ', '\n'}
    );
    public static final char[] ALPHABET_EN = concatenate(
            ENGLISH_LOWERCASE, ENGLISH_UPPERCASE,
            DIGITS, PUNCTUATION, new char[]{' ', '\n'}
    );
    private static char[] concatenate(char[]... arrays) {
        int totalLength = 0;
        for (char[] array : arrays) {
            totalLength += array.length;
        }
        char[] result = new char[totalLength];
        int currentIndex = 0;
        for (char[] array : arrays) {
            System.arraycopy(array, 0, result, currentIndex, array.length);
            currentIndex += array.length;
        }
        return result;
    }
}