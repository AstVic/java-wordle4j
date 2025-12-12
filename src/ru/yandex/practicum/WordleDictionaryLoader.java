package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {
    private File file;
    private PrintWriter printWriter;
    private List<String> words;

    public WordleDictionaryLoader(File file, PrintWriter printWriter) {
        this.file = file;
        this.printWriter = printWriter;
    }

    public WordleDictionary getWordleDictionary() throws IOException {
        try (BufferedReader bufferedReader =
                     new BufferedReader(new InputStreamReader(new FileInputStream(file.getAbsolutePath()),
                             StandardCharsets.UTF_8))) {

            words = new ArrayList<>();
            String word = bufferedReader.readLine();
            while (word != null) {
                word = word.trim();
                word = word.toLowerCase();
                if (word.length() == 5) {
                    words.add(word);
                }
                word = bufferedReader.readLine();
            }

        } catch (Exception e) {
            printWriter.write(Arrays.toString(e.getStackTrace()));
        }
        return new WordleDictionary(words, printWriter);
    }
}