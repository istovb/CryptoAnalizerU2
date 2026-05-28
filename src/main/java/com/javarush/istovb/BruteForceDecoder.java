package com.javarush.istovb;

import java.util.ArrayList;
import java.util.List;

public class BruteForceDecoder {
    private final CaesarCipher cipher;
    public BruteForceDecoder(CaesarCipher cipher) {this.cipher = cipher;}
    public List<String> bruteForce(List<String> encryptedText) {
        List<String> allResults = new ArrayList<>();
        char[] alphabet = cipher.getAlphabet();
        int alphabetLength = alphabet.length;
        for (int key = 0; key < alphabetLength; key++) {
            List<String> decryptedLines = cipher.decrypt(encryptedText, key);
            String decrypted = "КЛЮЧ " + key + ":\n" + String.join("\n", decryptedLines) + "\n";
            allResults.add(decrypted);
            System.out.println(decrypted);
        }
        return allResults;
    }
}
