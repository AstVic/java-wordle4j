package ru.yandex.practicum;

public class InvalidClueFormatException extends WordleRuntimeException {
    public InvalidClueFormatException() {
        super("Некорректный формат подсказки");
    }
}
