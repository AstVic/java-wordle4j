package ru.yandex.practicum;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {

    public static void main(String[] args) {
        try (PrintWriter printWriter = new PrintWriter("logFile.txt")) {
            try {
                Scanner scanner = new Scanner(System.in);

                WordleDictionaryLoader wordleDictionaryLoader =
                        new WordleDictionaryLoader(new File("words_ru.txt"), printWriter);
                WordleDictionary wordleDictionary = wordleDictionaryLoader.getWordleDictionary();
                WordleGame wordleGame = new WordleGame(wordleDictionary, printWriter);

                System.out.println("Слово загадано!");
                String clue = "";
                while (wordleGame.isStepsCountEnough() && !clue.equals("+++++")) {
                    System.out.println("Оставшихся попыток: " + wordleGame.getStepsLeft());
                    System.out.println("Введите слово (для вывода слова-подсказки из словаря введите Enter): ");
                    String suggestedWord = scanner.nextLine();
                    suggestedWord = suggestedWord.trim();
                    suggestedWord = suggestedWord.toLowerCase();
                    if (suggestedWord.isBlank() | suggestedWord.isEmpty()) {
                        System.out.println(wordleGame.getRandomWord());
                    } else if (!wordleDictionary.isWordInDictionary(suggestedWord)) {
                        System.out.println("Такого слова нет в словаре!");
                    } else {
                        clue = wordleGame.makeSuggestion(suggestedWord);
                        System.out.println(clue);
                    }
                    System.out.println();
                }
                if (!clue.equals("+++++")) {
                    System.out.println("Попытки закончились. Вы не угадали слово.");
                } else {
                    System.out.println("Слово угадано!");
                }
            } catch (Exception e) {
                printWriter.write(Arrays.toString(e.getStackTrace()));
            }
        } catch (IOException e) {
            System.out.println("Не удалось создать лог-файл.");
        }
    }
}
