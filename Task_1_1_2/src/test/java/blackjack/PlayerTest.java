package blackjack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Тестовый Игрок");
    }

    @Test
    @DisplayName("Проверка начального состояния игрока")
    void testInitialPlayerState() {
        assertEquals("Тестовый Игрок", player.getName());
        assertEquals(0, player.getRoundsWon());
        assertEquals(0, player.sumUpCards());
        assertTrue(player.getCards().isEmpty());
    }

    @Test
    @DisplayName("Подсчет суммы карт и фиксирование перебора")
    void testSumUpCardsAndBusted() {
        player.addCard(new Card(8)); // Десятка (10)
        player.addCard(new Card(9)); // Валет (10)

        assertFalse(player.isBusted());
        assertEquals(20, player.sumUpCards());

        player.addCard(new Card(0)); // Двойка (2) -> Итого 22
        assertTrue(player.isBusted());
    }

    @Test
    @DisplayName("Динамический пересчет Туза с 11 на 1 при превышении 21 очка")
    void testAceRecalculationOnBust() {
        player.addCard(new Card(8));  // Десятка (10)
        player.addCard(new Card(12)); // Туз (11) -> Сумма 21
        assertEquals(21, player.sumUpCards());

        // Добавляем 5-ку. Сумма становится 26, но Туз пересчитывается в 1 -> Сумма становится 16
        player.addCard(new Card(3));  // Пятерка (5)
        assertEquals(16, player.sumUpCards());
        assertFalse(player.isBusted());
    }

    @Test
    @DisplayName("Очистка руки игрока")
    void testClearUpHand() {
        player.addCard(new Card(0));
        player.addCard(new Card(1));

        player.clearUpHand();
        assertTrue(player.getCards().isEmpty());
        assertEquals(0, player.sumUpCards());
    }

    @Test
    @DisplayName("Увеличение счетчика побед")
    void testAddWin() {
        player.addWin();
        player.addWin();
        assertEquals(2, player.getRoundsWon());
    }
}