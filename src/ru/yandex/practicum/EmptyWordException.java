package ru.yandex.practicum;

public class EmptyWordException extends WordleException {
    public EmptyWordException() {
        super("Введена пустая строка");
    }
}
