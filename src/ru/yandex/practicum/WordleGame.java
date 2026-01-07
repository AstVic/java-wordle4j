package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {
    private String answer;
    private int steps;
    private WordleDictionary dictionary;
    private final PrintWriter printWriter;
    private Set<Character> restrictedLetters;
    private Map<Character, Set<Integer>> rightLetters;
    private final int maxStepCount = 6;
    private final int wordsLength = 5;

    public WordleGame(WordleDictionary dictionary, PrintWriter printWriter) {
        this.dictionary = dictionary;
        this.steps = maxStepCount;
        this.printWriter = printWriter;
        answer = dictionary.getRandomWord(false);
        rightLetters = new HashMap<>();
        restrictedLetters = new HashSet<>();
    }

    public String makeSuggestion(String suggestionWord) throws WordleException {
        StringBuilder clue = new StringBuilder();
        try {
            if (suggestionWord == null || suggestionWord.trim().isEmpty()) {
                throw new EmptyWordException();
            }

            if (suggestionWord.length() != wordsLength) {
                throw new InvalidWordLengthException(wordsLength);
            }

            if (!dictionary.isWordInDictionary(suggestionWord)) {
                throw new WordNotInDictionaryException();
            }

            steps--;
            if (answer.equals(suggestionWord)) {
                return "+++++";
            } else {
                for (int i = 0; i < wordsLength; i++) {
                    if (answer.charAt(i) == suggestionWord.charAt(i)) {
                        clue.append('+');
                        if (!rightLetters.containsKey(answer.charAt(i))) {
                            rightLetters.put(answer.charAt(i), new HashSet<>());
                        }
                        rightLetters.get(answer.charAt(i)).add(i);
                    } else if (answer.contains(String.valueOf(suggestionWord.charAt(i)))) {
                        clue.append('^');
                        if (!rightLetters.containsKey(answer.charAt(i))) {
                            rightLetters.put(answer.charAt(i), new HashSet<>());
                        }
                    } else {
                        clue.append('-');
                        restrictedLetters.add(answer.charAt(i));
                    }
                }
                dictionary.updateDependingOnClues(suggestionWord, String.valueOf(clue));
            }
        } catch (WordleException e) {
            printWriter.println(e.getMessage());
            throw e;
        } catch (Exception e) {
            printWriter.println(e.getMessage());
        }
        return String.valueOf(clue);
    }

    public boolean isStepsCountEnough() {
        return steps != 0;
    }

    public int getStepsLeft() {
        return steps;
    }

    public String getRandomWord() {
        return dictionary.getRandomWord(true);
    }
}