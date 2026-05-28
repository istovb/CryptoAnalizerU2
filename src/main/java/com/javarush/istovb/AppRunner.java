package com.javarush.istovb;



import com.javarush.istovb.exception.FileProcessingException;
import com.javarush.istovb.util.Alphabet;

import java.util.List;
import java.util.Scanner;

public class AppRunner {
    private final Scanner scanner = new Scanner(System.in);
    private CaesarCipher cipher;
    private final FileManager fileManager = new FileManager();
    public String langChoice;
    private char[] selectLanguage() {
        System.out.println("\n--- ВЫБОР ЯЗЫКА ---");
        System.out.println("1 - Русский");
        System.out.println("2 - Английский");
        System.out.println("0 - Выход");
        System.out.print("Ваш выбор: ");

        this.langChoice = scanner.nextLine();

        if ("1".equals(langChoice)) {
            System.out.println("Выбран русский алфавит.");
            return Alphabet.ALPHABET_RU;
        } else if ("2".equals(langChoice)) {
            System.out.println("Выбран английский алфавит.");
            return Alphabet.ALPHABET_EN;
        } else if ("0".equals(langChoice)) {
            return null;
        } else {
            System.out.println("Неправильный выбор. По умолчанию установлен русский язык.");
            return Alphabet.ALPHABET_RU;
        }
    }
    private void displayMenu() {
        System.out.println("\n--- ВЫБОР РЕЖИМА ---");
        System.out.println("1 - ШИФРОВАНИЕ");
        System.out.println("2 - РАСШИФРОВКА");
        System.out.println("3 - РАСШИФРОВКА (ПЕРЕБОР ВСЕХ ВАРИАНТОВ)");
        System.out.println("4 - РАСШИФРОВКА (СТАТИСТИЧЕСКИЙ АНАЛИЗ)");
        System.out.println("0 - СМЕНИТЬ ЯЗЫК / ВЫХОД");
        System.out.print("Выберете режим: ");
    }
    public void run() {
        while (true) {
            char[] selectedAlphabet = selectLanguage();
            if (selectedAlphabet == null) {
                System.out.println("Завершение работы...");
                break;
            }
            this.cipher = new CaesarCipher(selectedAlphabet);
            while (true) {
                displayMenu();
                Integer choice = Validator.validateMenuChoice(scanner.nextLine());
                if (choice == null) {
                    continue;
                }
                try {
                    boolean operationSuccess = handleOperation(choice);
                    if (!operationSuccess) {
                        break;
                    }
                    if (choice == 0) {
                        return;
                    }
                } catch (FileProcessingException ex) {
                    System.out.println("Ошибка работы с файлом: " + ex.getMessage());
                }
            }
        }
    }
    private boolean handleOperation(int choice) throws FileProcessingException {
        switch (choice) {
            case 1:
                return processEncryption();
            case 2:
                return processDecryption();
            case 3:
                return processBruteForce();
            case 4:
                return processStatistics();
            case 0:
                 return false;
            default:
                System.out.println("Неправильный выбор");
                return true;
        }
    }
    private boolean processEncryption() throws FileProcessingException {
        System.out.print("Выберете файл: ");
        String inputEncrypt = scanner.nextLine();
        if (!Validator.validateInputFile(inputEncrypt)) { return true; }
        if (!Validator.isFileNotEmpty(inputEncrypt)) { return true; }
        if (!Validator.isPathSafe(inputEncrypt)) { return true; }
        List<String> sampleLinesForEncrypt = fileManager.readFile(inputEncrypt);
        boolean isMatchForEncrypt = Validator.isLanguageMatch(sampleLinesForEncrypt, langChoice);
        if (!isMatchForEncrypt) {
            System.out.println("❌ Ошибка: Язык текста в файле не соответствует выбранному языку.");
            System.out.println("Пожалуйста, выберите правильный язык и попробуйте снова.");
            return false;
        }
        System.out.print("Выходной файл: ");
        String outputEncrypt = scanner.nextLine();
        if (!Validator.isOutputFileValid(inputEncrypt, outputEncrypt)) { return true; }
        System.out.print("Ключ: ");
        Integer encryptKey = Validator.validateMenuChoice(scanner.nextLine());
        if (encryptKey == null || !Validator.validateKey(encryptKey, cipher.getAlphabet())) { return true; }
        List<String> encryptedText = cipher.encrypt(sampleLinesForEncrypt, encryptKey);
        fileManager.writeFile(outputEncrypt, fileManager.convertToText(encryptedText));
        System.out.println("✅ ШИФРОВАНИЕ ЗАВЕРШЕНО");
        return true;
    }
    private boolean processDecryption() throws FileProcessingException {
        System.out.print("Выберете файл: ");
        String inputDecrypt = scanner.nextLine();
        if (!Validator.validateInputFile(inputDecrypt)) { return true; }
        if (!Validator.isFileNotEmpty(inputDecrypt)) { return true; }
        if (!Validator.isPathSafe(inputDecrypt)) { return true; }
        List<String> sampleLinesForDecrypt = fileManager.readFile(inputDecrypt);
        boolean isMatchForDecrypt = Validator.isLanguageMatch(sampleLinesForDecrypt, langChoice);
        if (!isMatchForDecrypt) {
            System.out.println("❌ Ошибка: Язык текста в файле не соответствует выбранному языку.");
            System.out.println("Пожалуйста, выберите правильный язык и попробуйте снова.");
            return false;
        }
        System.out.print("Выходной файл: ");
        String outputDecrypt = scanner.nextLine();
        if (!Validator.isOutputFileValid(inputDecrypt, outputDecrypt)) {return true;}
        System.out.print("Ключ: ");
        Integer decryptKey = Validator.validateMenuChoice(scanner.nextLine());
        if (decryptKey == null || !Validator.validateKey(decryptKey, cipher.getAlphabet())) { return true; }
        List<String> decryptedText = cipher.decrypt(sampleLinesForDecrypt, decryptKey);
        fileManager.writeFile(outputDecrypt, fileManager.convertToText(decryptedText));
        System.out.println("✅ РАСШИФРОВКА ЗАВЕРШЕНА");
        return true;
    }
    private boolean processBruteForce() throws FileProcessingException {
        System.out.print("Выберете файл: ");
        String inputBruteForce =scanner.nextLine();
        if (!Validator.validateInputFile(inputBruteForce)){return true;}
        if (!Validator.isFileNotEmpty(inputBruteForce)){return true;}
        if (!Validator.isPathSafe(inputBruteForce)) { return true; }
        List<String> sampleLinesForBruteForce = fileManager.readFile(inputBruteForce);

        boolean isMatchForBruteForce = Validator.isLanguageMatch(sampleLinesForBruteForce, langChoice);
        if (!isMatchForBruteForce) {
            System.out.println("❌ Ошибка: Язык текста в файле не соответствует выбранному языку.");
            System.out.println("Перебор невозможен. Пожалуйста, выберите правильный язык и попробуйте снова.");
            return false;
        }
        BruteForceDecoder bruteForce =new BruteForceDecoder(cipher);
        bruteForce.bruteForce(sampleLinesForBruteForce);
        return true;
    }
    private boolean processStatistics() throws FileProcessingException {
        System.out.print("Выберете файл: ");
        String inputStatistics = scanner.nextLine();
        if (!Validator.validateInputFile(inputStatistics)) { return true; }
        if (!Validator.isFileNotEmpty(inputStatistics)) { return true; }
        if (!Validator.isPathSafe(inputStatistics)) { return true; }
        List<String> sampleLinesForStatistics = fileManager.readFile(inputStatistics);
        StatisticalAnalyzer analyzer = new StatisticalAnalyzer(cipher);
        List<String> statisticsResult = analyzer.decryptByStatistics(sampleLinesForStatistics);

        System.out.print("Выходной файл: ");
        String outputStatistics = scanner.nextLine();

        if (!Validator.isOutputFileValid(inputStatistics, outputStatistics)) {
            return true;
        }
        fileManager.writeFile(outputStatistics, fileManager.convertToText(statisticsResult));

        System.out.println("✅ РАСШИФРОВКА С ПОМОЩЬЮ СТАТИСТИЧЕСКОГО АНАЛИЗА ТЕКСТА ЗАВЕРШЕНА");
        return true;//
    }
}


