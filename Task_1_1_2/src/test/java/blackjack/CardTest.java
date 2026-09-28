package blackjack;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    @DisplayName("Проверка инициализации стоимости очков для разных карт")
    void testCardValueInitialization() {
        Card ace = new Card(Suit.HEARTS, Rank.ACE);   // Туз Черви -> 11 очков
        Card king = new Card(Suit.HEARTS, Rank.KING);  // Король Черви -> 10 очков
        Card ten = new Card(Suit.CLUBS, Rank.TEN);    // Десятка Черви -> 10 очков
        Card two = new Card(Suit.HEARTS, Rank.TWO);    // Двойка Черви -> 2 очка

        assertEquals(11, ace.getValue());
        assertEquals(10, king.getValue());
        assertEquals(10, ten.getValue());
        assertEquals(2, two.getValue());
    }

    @Test
    @DisplayName("Проверка метода isAce")
    void testIsAce() {
        Card ace = new Card(Suit.HEARTS, Rank.ACE);
        Card notAce = new Card(Suit.HEARTS, Rank.TWO);

        assertTrue(ace.isAce());
        assertFalse(notAce.isAce());
    }

    @Test
    @DisplayName("Проверка изменения значения карты с 11 на 1")
    void testSetValue() {
        Card ace = new Card(Suit.DIAMONDS, Rank.ACE);
        ace.setAceValueOne();
        assertEquals(1, ace.getValue());
    }

    @Test
    @DisplayName("Проверка текстового представления toString")
    void testToString() {
        Card card = new Card(Suit.SPADES, Rank.ACE); // Туз пики (11)
        assertEquals("Туз Пики (11)", card.toString());
    }
}
