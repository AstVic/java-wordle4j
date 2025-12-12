package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import java.io.*;
import java.nio.charset.StandardCharsets;gi
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WordleTest {
    private static PrintWriter printWriter;
    private WordleDictionary wordleDictionary;
    private WordleDictionaryLoader wordleDictionaryLoader;
    private WordleGame wordleGame;
    private static final File testFile = new File("test_words.txt");

    @BeforeAll
    static void setUpAll() throws IOException {
        printWriter = new PrintWriter(System.out);

        try (PrintWriter writer = new PrintWriter(testFile, StandardCharsets.UTF_8)) {
            writer.println("apPle ");
            writer.println("TABLE");
            writer.println("   chaIr");
            writer.println("    cloud");
            writer.println("light\n");
            writer.println("applY");
            writer.println("");
            writer.println("WaTer");
            writer.println("abracadabra");
        }
    }

    @BeforeEach
    void setUp() throws IOException {
        wordleDictionaryLoader = new WordleDictionaryLoader(testFile, printWriter);
        wordleDictionary = wordleDictionaryLoader.getWordleDictionary();
        wordleGame = new WordleGame(wordleDictionary, printWriter);
    }

    @Test
    void testWordInDictionary() {
        assertTrue(wordleDictionary.isWordInDictionary("apple"));
        assertTrue(wordleDictionary.isWordInDictionary("table"));
        assertFalse(wordleDictionary.isWordInDictionary("abcdef"));
        assertFalse(wordleDictionary.isWordInDictionary(""));
        assertFalse(wordleDictionary.isWordInDictionary("abracadabra"));
    }

    @Test
    void testGetRandomWord() {
        String word = wordleDictionary.getRandomWord(false);
        assertNotNull(word);
        assertTrue(wordleDictionary.isWordInDictionary(word));

        for (int i = 0; i < 10; i++) {
            assertNotNull(wordleDictionary.getRandomWord(false));
        }
    }

    @Test
    void testGetRandomWordWithRemoval() {
        String word = wordleDictionary.getRandomWord(true);
        assertNotNull(word);
        assertTrue(wordleDictionary.isWordInDictionary(word));
    }

    @Test
    void testGameInitialization() {
        assertNotNull(wordleGame);
        assertTrue(wordleGame.isStepsCountEnough());
        assertEquals(6, wordleGame.getStepsLeft());
    }


    @Test
    void testStepsDecrementOnGuess() {
        int initialSteps = wordleGame.getStepsLeft();
        try {
            wordleGame.makeSuggestion("table");
        } catch (Exception e) {
            fail("Не должно было быть исключения для слова table");
        }
        assertEquals(initialSteps - 1, wordleGame.getStepsLeft());
        try {
            wordleGame.makeSuggestion("chair");
        } catch (Exception e) {
            fail("Не должно было быть исключения для слова chair");
        }
        assertEquals(initialSteps - 2, wordleGame.getStepsLeft());
    }

    @Test
    void testGameEndsAfterSixAttempts() {
        assertEquals(6, wordleGame.getStepsLeft());
        assertTrue(wordleGame.isStepsCountEnough());

        for (int i = 0; i < 6; i++) {
            try {
                wordleGame.makeSuggestion("water");
            } catch (Exception e) {
                fail("Не должно было быть исключения для слова water");
            }
        }

        assertEquals(0, wordleGame.getStepsLeft());
        assertFalse(wordleGame.isStepsCountEnough());
    }

    @Test
    void testClueFormat() {
        String clue = "";
        try {
            clue = wordleGame.makeSuggestion("apple");
        } catch (Exception e) {
            fail("Не должно было быть исключения для слова apple");
        }
        assertEquals(5, clue.length());
        assertTrue(clue.matches("[+^\\-]+"));
    }

    @Test
    void testGetRandomWordFromGame() {
        String randomWord = wordleGame.getRandomWord();
        assertNotNull(randomWord);
        assertEquals(5, randomWord.length());
    }

    @Test
    void testFullGameFlowWithWin() {
        assertTrue(wordleGame.isStepsCountEnough());
        String clue1 = "";
        try {
            clue1 = wordleGame.makeSuggestion("table");
        } catch (Exception e) {
            fail("Не должно было быть исключения для слова table");
        }
        assertNotNull(clue1);
        String clue2 = "";
        try {
            clue2 = wordleGame.makeSuggestion("chair");
        } catch (Exception e) {
            fail("Не должно было быть исключения для слова chair");
        }
        assertNotNull(clue2);
        assertEquals(4, wordleGame.getStepsLeft());
    }

    @Test
    void testMultipleGamesIndependence() {
        WordleGame game1 = new WordleGame(wordleDictionary, printWriter);
        WordleGame game2 = new WordleGame(wordleDictionary, printWriter);

        try {
            game1.makeSuggestion("table");
            game2.makeSuggestion("chair");
        } catch (Exception e) {
            fail("Не должно было быть исключения");
        }

        assertEquals(5, game1.getStepsLeft());
        assertEquals(5, game2.getStepsLeft());
    }

    @Test
    void testDictionaryConsistencyAcrossGames() {
        WordleGame game1 = new WordleGame(wordleDictionary, printWriter);
        WordleGame game2 = new WordleGame(wordleDictionary, printWriter);
        assertTrue(wordleDictionary.isWordInDictionary("apple"));
        assertTrue(wordleDictionary.isWordInDictionary("table"));
    }

    @Test
    void testEdgeCases() {
        WordleGame newGame = new WordleGame(wordleDictionary, printWriter);
        assertTrue(newGame.isStepsCountEnough());
        assertEquals(6, newGame.getStepsLeft());
        for (int i = 6; i > 0; i--) {
            assertEquals(i, newGame.getStepsLeft());
            try {
                newGame.makeSuggestion("water");
            } catch (Exception e) {
                fail("Не должно было быть исключения для слова water");
            }
        }
        assertEquals(0, newGame.getStepsLeft());
        assertFalse(newGame.isStepsCountEnough());
    }

    @Test
    void testErrorHandling() {
        WordleDictionary emptyDictionary = new WordleDictionary(new ArrayList<>(), printWriter);
        WordleGame emptyGame = new WordleGame(emptyDictionary, printWriter);
        assertNotNull(emptyGame.getRandomWord());
    }
}