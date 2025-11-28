package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
    private List<String> allSuggestedWords;

    public WordleGame(WordleDictionary dictionary, PrintWriter printWriter) {
        this.dictionary = dictionary;
        this.steps = 6;
        this.printWriter = printWriter;
        allSuggestedWords = new ArrayList<>();
        answer = dictionary.getRandomWord(false);
    }

    public String makeSuggestion(String suggestionWord) {
        StringBuilder clue = new StringBuilder();
        steps--;
        try {
            allSuggestedWords.add(suggestionWord);
            if (answer.equals(suggestionWord)) {
                return "+++++";
            } else {
                for (int i = 0; i < 5; i++) {
                    if (answer.charAt(i) == suggestionWord.charAt(i)) {
                        clue.insert(i, '+');
                    } else if (answer.contains(String.valueOf(suggestionWord.charAt(i)))) {
                        clue.insert(i, '^');
                    } else {
                        clue.insert(i, '-');
                    }
                }
                dictionary.updateDependingOnClues(suggestionWord, String.valueOf(clue));
            }
        } catch (Exception e) {
            printWriter.write(e.getMessage());
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
