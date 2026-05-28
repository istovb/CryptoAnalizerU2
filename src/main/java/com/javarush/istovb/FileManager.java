package com.javarush.istovb;



import com.javarush.istovb.exception.FileProcessingException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileManager
{
   private static final StandardOpenOption[] FILE_WRITE_OPTIONS =
            {
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING
            };

    public List<String> readFile(String filename) {
        try {
            Path path = Path.of(filename);
            // явно указываем КОДИРОВКУ utf-8, так как были проблемы с проверкой языка если, например, в файле только слово- hello
            return Files.readAllLines(path, java.nio.charset.StandardCharsets.UTF_8);
        } catch (IOException | InvalidPathException ex)
        {
            throw new FileProcessingException("Ошибка чтения файла: " + ex.getMessage(), ex);
        }
    }
    public void writeFile(String filename, String content) {
        try {
            Path filePath = Path.of(filename);
            Files.writeString(filePath, content, FILE_WRITE_OPTIONS);
        } catch (IOException | InvalidPathException ex)
        {
            throw new FileProcessingException(ex.getMessage(), ex);
        }
    }
    public String convertToText(List<String> lines)
    {
        return String.join(System.lineSeparator(),lines);
    }
}

