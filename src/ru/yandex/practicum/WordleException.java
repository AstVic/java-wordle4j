package ru.yandex.practicum;

public class WordleException extends Exception {
    public WordleException(String message) {
        super(message);
    }
}

class InvalidWordLengthException extends WordleException {
    public InvalidWordLengthException(int expectedLength) {
        super("Недопустимая длина слова. Ожидается " + expectedLength + " букв");
    }
}

class WordNotInDictionaryException extends WordleException {
    public WordNotInDictionaryException() {
        super("Такого слова нет в словаре");
    }
}

class EmptyWordException extends WordleException {
    public EmptyWordException() {
        super("Введена пустая строка");
    }
}

class WordleRuntimeException extends RuntimeException {
    public WordleRuntimeException(String message) {
        super(message);
    }
}

class GameStateException extends WordleRuntimeException {
    public GameStateException(String message) {
        super(message);
    }
}

class DictionaryException extends WordleRuntimeException {
    public DictionaryException(String message) {
        super(message);
    }
}

class InvalidClueFormatException extends WordleRuntimeException {
    public InvalidClueFormatException() {
        super("Некорректный формат подсказки");
    }
}