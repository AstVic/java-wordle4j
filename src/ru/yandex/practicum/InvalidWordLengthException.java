package ru.yandex.practicum;

public class InvalidWordLengthException extends WordleException {
    public InvalidWordLengthException(int expectedLength) {
        super("Недопустимая длина слова. Ожидается " + expectedLength + " букв");
    }
}
