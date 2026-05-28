package com.javarush.istovb;
import com.javarush.istovb.util.Alphabet;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Validator {
    public static boolean validateInputFile(String path)
        {
        if (path == null || path.isBlank())
        {
            System.out.println("Путь к файлу пуст");
            return false;
        }
        if (!Files.exists(Path.of(path)))
        {
            System.out.println("Файл не существует");
            return false;
        }
        if (!Files.isRegularFile(Path.of(path)))
        {
            System.out.println("Это путь, а не файл");
            return false;
        }
        return true;
    }
    public static boolean isFileNotEmpty(String filePath)
    {
        if (!validateInputFile(filePath)){return false;}
        try {
            Path path = Path.of(filePath);
            if (Files.size(path) == 0)
            {
                System.out.println("Ошибка: Файл "+filePath+ " пуст. Нечего обрабатывать.");
                return false;
            }
        } catch (IOException e)
        {
            System.out.println("Ошибка при проверке файла: " +filePath+ e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean isPathSafe(String pathStr) {
        if (pathStr == null) {
            return false;
        }
        String path = pathStr.toLowerCase();
        List<String> windowsSystemDirs = Arrays.asList(
                "windows", "winnt", "program files", "program files (x86)",
                "system32", "drivers", "etc", "boot","ivan"// ivan для проверки-)
        );
        for (String dir : windowsSystemDirs) {
            // Проверяем наличие папки в пути (например, .../windows/...)
            if (path.contains("\\" + dir + "\\") || path.contains("/" + dir + "/")) {
                System.out.println("❌ Ошибка: Путь содержит системную директорию '" + dir + "'.");
                return false;
            }
        }
        try {
            Path pathObj = Path.of(path).normalize();
            if (!pathObj.isAbsolute() || pathObj.toString().contains("..")) {
                System.out.println("❌ Ошибка: Некорректный или небезопасный путь.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("❌ Ошибка: Некорректный путь к файлу.");
            return false;
        }

        return true;
    }
    public static boolean validateKey(int key, char[] alphabet) {
        if (key < 0 || key >= alphabet.length) {
            System.out.println("Неправильный ключ. Должен быть от 0 до " + (alphabet.length - 1));
            return false;
        }
        return true;
    }
    public static Integer validateMenuChoice(String value)
    {
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e)
        {
            System.out.println("Пожалуйста, введите одно из предложенных чисел ");
            return null;
        }
    }
    public static boolean isOutputFileValid(String inputPath, String outputPath)
    {
        if (inputPath == null || outputPath == null)
        {
            return false;
        }
        try {
            Path inputAbs = Path.of(inputPath).toAbsolutePath().normalize();
            Path outputAbs = Path.of(outputPath).toAbsolutePath().normalize();
            if (inputAbs.toString().equals(outputAbs.toString()))
            {
                System.out.println("❌ Ошибка: Входной и выходной файлы совпадают. Это приведет к потере данных.");
                return false;
            }
        } catch (InvalidPathException e)
        {
            System.out.println("❌ Некорректный путь к файлу: " + e.getMessage());
            return false;
        }
        return true;
    }
    public static boolean isTextRussian(List<String> lines)
    {
        if (lines == null || lines.isEmpty()) return false;
        char[] russianAlphabet = Alphabet.ALPHABET_RU;
        java.util.Set<Character> russianSet = new java.util.HashSet<>();//для быстрого поиска
        for (char c : russianAlphabet) {russianSet.add(c);}
        for (String line : lines) {
            for (char symbol : line.toLowerCase().toCharArray())
            {
                if (!russianSet.contains(symbol))
                {
                    return false;
                }
            }
        }
        String fullText = String.join(" ", lines).toLowerCase();
        return fullText.contains("о") || fullText.contains("е") || fullText.contains("а");
    }
    public static boolean isLanguageMatch(List<String> textLines, String userLangChoice) {
        if (textLines == null || textLines.isEmpty()) {
            return true;
        }
        boolean isRussian = isTextRussian(textLines);
        if ("1".equals(userLangChoice) && isRussian)
        {
            return true;
        }
        if ("2".equals(userLangChoice) && !isRussian)
        {
            return true;
        }
        return false;
    }
}
