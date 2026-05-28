package com.javarush.istovb;

import java.util.List;

public class StatisticalAnalyzer {
    private static final String RUSSIAN_FREQ_ORDER = "оеаинтсрвлкмдпуяыьгзбчйхжшюцщэфъ";
    private static final String ENGLISH_FREQ_ORDER = "etaoinshrdlcumwfgypbvkjxqz";
    private final CaesarCipher cipher;
    public StatisticalAnalyzer(CaesarCipher cipher) {
        this.cipher = cipher;
    }
    public List<String> decryptByStatistics(List<String> encryptedText) {
        int bestKey = 0;
        int bestScore = Integer.MIN_VALUE; // Начинаем с самого плохого результата

        char[] currentAlphabet = cipher.getAlphabet();
        int alphabetLength = currentAlphabet.length;

        for (int key = 0; key < alphabetLength; key++) {
            List<String> decrypted = cipher.decrypt(encryptedText, key);
            int score = calculateScore(decrypted);
            if (score > bestScore) {
                bestScore = score;
                bestKey = key;
            }
        }
        System.out.println("Наиболее вероятный ключ: " + bestKey);
        return cipher.decrypt(encryptedText, bestKey);
    }
    private int calculateScore(List<String> text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        String fullText = String.join(" ", text).toLowerCase();
        int[] frequencies = new int[65536];
        for (char ch : fullText.toCharArray()) {
            frequencies[ch]++;
        }
        char[] alphabet = cipher.getAlphabet();
        char[] sortedAlphabet = alphabet.clone();
        for (int i = 0; i < sortedAlphabet.length - 1; i++)
        {
            for (int j = 0; j < sortedAlphabet.length - i - 1; j++)
            {
                if (frequencies[sortedAlphabet[j]] < frequencies[sortedAlphabet[j + 1]])
                {
                    char temp = sortedAlphabet[j];
                    sortedAlphabet[j] = sortedAlphabet[j + 1];
                    sortedAlphabet[j + 1] = temp;
                }
            }
        }
        String freqOrderToUse = (cipher.getAlphabet()[0] <= 127 && new String(cipher.getAlphabet()).contains("e"))
                ? ENGLISH_FREQ_ORDER : RUSSIAN_FREQ_ORDER;
        int score = 0;
        for (int i = 0; i < Math.min(sortedAlphabet.length, freqOrderToUse.length()); i++) {
            if (sortedAlphabet[i] == freqOrderToUse.charAt(i)) {
                score += 10;
            }
        }
        return score;
    }
}
