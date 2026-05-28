package com.javarush.istovb;

import java.util.ArrayList;
import java.util.List;

public class CaesarCipher
{
    private final char[] alphabet;
    private final int[] charToIndex = new int[65536];
    public CaesarCipher(char[] alphabet) {
        this.alphabet = alphabet;
        java.util.Arrays.fill(this.charToIndex, -1);
        for (int i = 0; i < alphabet.length; i++) {
            this.charToIndex[alphabet[i]] = i;
        }
    }
    public List<String> encrypt(List<String> lines, int key)
    {
        List<String> encryptedLines = new ArrayList<>();
        for (String line : lines)
        {
            StringBuilder result = new StringBuilder();
            for (char symbol :line.toCharArray())
            {
                int index = findIndex(symbol);
                if (index == -1)
                {
                    result.append(symbol);
                    continue;
                }
                int newIndex =(index + key) % alphabet.length;
                result.append( alphabet[newIndex]);
            }
            encryptedLines.add(result.toString());
        }
        return encryptedLines;
    }
    public List<String> decrypt(List<String> lines,int key)
    {
        List<String> decryptedLines = new ArrayList<>();
        for (String line : lines) {StringBuilder result = new StringBuilder();
            for (char symbol : line.toCharArray())
            {
                int index = findIndex(symbol);
                if (index == -1) {result.append(symbol);
                    continue;
                }
                int newIndex = (index - key + alphabet.length)% alphabet.length;
                result.append(alphabet[newIndex]);
            }
            decryptedLines.add(result.toString());
        }
        return decryptedLines;
    }
    private int findIndex(char symbol) {
        return this.charToIndex[symbol];
    }
    public char[] getAlphabet() {
        return this.alphabet;
    }
}
