package ru.yandex.practicum;

public class WordNotInDictionaryException extends WordleException {
    public WordNotInDictionaryException() {
        super("Такого слова нет в словаре");
    }
}
