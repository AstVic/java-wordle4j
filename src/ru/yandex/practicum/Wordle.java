package ru.yandex.practicum;

import java.io.*;
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

    public static void main(String[] args) throws IOException {
        try (PrintWriter printWriter = new PrintWriter(
                new FileWriter("logFile.txt", false), true)) {
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
                    suggestedWord = suggestedWord.replace("ё", "е");
                    if (suggestedWord.isBlank()) {
                        System.out.println(wordleGame.getRandomWord());
                    } else {
                        try {
                            if (!wordleDictionary.isWordInDictionary(suggestedWord)) {
                                throw new WordNotInDictionaryException();
                            }
                            clue = wordleGame.makeSuggestion(suggestedWord);
                            System.out.println(clue);
                        } catch (WordNotInDictionaryException e) {
                            System.out.println("Такого слова нет в словаре!");
                            printWriter.println("WordNotInDictionaryException: " + e.getMessage());
                        } catch (InvalidWordLengthException e) {
                            System.out.println("Недопустимая длина слова");
                            printWriter.println("InvalidWordLengthException: " + e.getMessage());
                        } catch (EmptyWordException e) {
                            System.out.println("Введена пустая строка");
                            printWriter.println("EmptyWordException: " + e.getMessage());
                        } catch (WordleException e) {
                            System.out.println("Ошибка ввода");
                            printWriter.println("WordleException: " + e.getMessage());
                        }
                    }
                    System.out.println();
                }
                if (!clue.equals("+++++")) {
                    System.out.println("Попытки закончились. Вы не угадали слово.");
                } else {
                    System.out.println("Слово угадано!");
                }
            } catch (Exception e) {
                printWriter.println("Критическая ошибка: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Не удалось создать лог-файл.");
        }
    }
}