package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
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
            if (words.isEmpty()) {
                throw new DictionaryException("Слов в словаре не осталось!");
            } else {
                result = words.get(random.nextInt(words.size()));
            }
        } catch (DictionaryException e) {
            printWriter.println(e.getMessage());
            return e.getMessage();
        } catch (Exception e) {
            printWriter.println(e.getMessage());
        }
        if (flag && result != null) {
            words.remove(result);
        }
        return result;
    }

    public void updateDependingOnClues(String suggestedWord, String clue) {
        try {
            if (clue.length() != 5) {
                throw new InvalidClueFormatException();
            }
            Set<String> toDelete = new HashSet<>();
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
                    } else if (clue.charAt(i) == '-') {
                        if (word.contains(String.valueOf(suggestedWord.charAt(i)))) {
                            toDelete.add(word);
                        }
                    } else {
                        throw new InvalidClueFormatException();
                    }
                }
            }
            for (String word : toDelete) {
                words.remove(word);
            }
        } catch (Exception e) {
            printWriter.println(e.getMessage());
        }
    }

    public boolean isWordInDictionary(String word) {
        boolean result = false;
        try {
            result = originalWords.contains(word);
        } catch (Exception e) {
            printWriter.println(e.getMessage());
        }
        return result;
    }
}