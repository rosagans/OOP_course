package task_1_1_2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    private Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck();
    }

    @Test
    @DisplayName("Новая колода содержит ровно 52 карты")
    void testInitialDeckSize() {
        assertEquals(52, deck.getSize());
    }

    @Test
    @DisplayName("Метод draw уменьшает размер колоды на 1 и возвращает карту")
    void testDrawCard() {
        Card card = deck.draw();
        assertNotNull(card);
        assertEquals(51, deck.getSize());
    }

    @Test
    @DisplayName("Попытка взять карту из пустой колоды вызывает IllegalStateException")
    void testDrawFromEmptyDeckThrowsException() {
        for (int i = 0; i < 52; i++) {
            deck.draw();
        }

        assertEquals(0, deck.getSize());
        assertThrows(IllegalStateException.class, () -> deck.draw());
    }

    @Test
    @DisplayName("Проверка условия исчерпания колоды isLowOnCards")
    void testIsLowOnCards() {
        assertFalse(deck.isLowOnCards());

        // Достаем 27 карт, остается 25 (< 26)
        for (int i = 0; i < 27; i++) {
            deck.draw();
        }

        assertTrue(deck.isLowOnCards());
    }

    @Test
    @DisplayName("Перезаполнение колоды refillCards сбрасывает размер до 52")
    void testRefillCards() {
        for (int i = 0; i < 20; i++) {
            deck.draw();
        }
        deck.refillCards();
        assertEquals(52, deck.getSize());
    }
}
