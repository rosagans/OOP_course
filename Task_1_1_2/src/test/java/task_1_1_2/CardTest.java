package task_1_1_2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    @DisplayName("Проверка инициализации стоимости очков для разных карт")
    void testCardValueInitialization() {
        Card ace = new Card(12);   // Туз Черви -> 11 очков
        Card king = new Card(11);  // Король Черви -> 10 очков
        Card ten = new Card(8);    // Десятка Черви -> 10 очков
        Card two = new Card(0);    // Двойка Черви -> 2 очка

        assertEquals(11, ace.getValue());
        assertEquals(10, king.getValue());
        assertEquals(10, ten.getValue());
        assertEquals(2, two.getValue());
    }

    @Test
    @DisplayName("Проверка метода isAce")
    void testIsAce() {
        Card ace = new Card(12);
        Card notAce = new Card(0);

        assertTrue(ace.isAce());
        assertFalse(notAce.isAce());
    }

    @Test
    @DisplayName("Проверка изменения значения карты с 11 на 1")
    void testSetValue() {
        Card ace = new Card(12);
        ace.setValue(1);
        assertEquals(1, ace.getValue());
    }

    @Test
    @DisplayName("Проверка текстового представления toString")
    void testToString() {
        Card card = new Card(12); // Туз Черви (11)
        assertEquals("Туз Черви (11)", card.toString());
    }
}
