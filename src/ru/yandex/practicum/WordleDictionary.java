package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private List<String> originalWords;
    private List<String> words;
    private PrintWriter printWriter;

    public WordleDictionary(List<String> newWords, PrintWriter printWriter) {
        this.printWriter = printWriter;
        originalWords = newWords;
        words = new ArrayList<>();
        words.addAll(newWords);
    }

    public String getRandomWord(boolean flag) {
        String result = null;
        try {
            Random random = new Random();
            try {
                if (words.size() == 0) {
                    result = "Слов в словаре не осталось!";
                } else {
                    result = words.get(random.nextInt(words.size()));
                }
            } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                printWriter.write(Arrays.toString(e.getStackTrace()));
            }
        } catch (Exception e) {
            printWriter.write(Arrays.toString(e.getStackTrace()));
        }
        if (flag) {
            words.remove(result);
        }
        return result;
    }

    public void updateDependingOnClues(String suggestedWord, String clue) {
        try {
            List<String> toDelete = new ArrayList<>();
            for (String word : words) {
                for (int i = 0; i < 5; i++) {
                    if (clue.charAt(i) == '+') {
                        if (suggestedWord.charAt(i) != word.charAt(i)) {
                            toDelete.add(word);
                        }
                    } else if (clue.charAt(i) == '^') {
                        if (!word.contains(String.valueOf(suggestedWord.charAt(i))) ||
                                word.charAt(i) == suggestedWord.charAt(i)) {
                            toDelete.add(word);
                        }
                    } else { // '-'
                        if (word.contains(String.valueOf(suggestedWord.charAt(i)))) {
                            toDelete.add(word);
                        }
                    }
                }
            }
            for (String word: toDelete) {
                words.remove(word);
            }
        } catch (Exception e) {
            printWriter.write(Arrays.toString(e.getStackTrace()));
        }
    }

    public boolean isWordInDictionary(String word) {
        boolean result = false;
        try {
            result = originalWords.contains(word);
        } catch (Exception e) {
            printWriter.write(Arrays.toString(e.getStackTrace()));
        }
        return result;
    }
}
