package blackjack;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {

    @Test
    @DisplayName("Игрок решает остановиться (0) и побеждает дилера по очкам")
    void testPlayerStandsAndWins() {

        List<Card> customCards = new ArrayList<>(List.of(
                new Card(Suit.HEARTS, Rank.SIX),   // Добор дилера (6) -> 17
                new Card(Suit.SPADES, Rank.ACE),    // 2-я карта игрока (11) -> 21
                new Card(Suit.CLUBS, Rank.NINE),    // 2-я карта дилера (9) -> 11
                new Card(Suit.DIAMONDS, Rank.KING), // 1-я карта игрока (10) -> 10
                new Card(Suit.HEARTS, Rank.TWO)     // 1-я карта дилера (2) -> 2
        ));

        Deck customDeck = new Deck(customCards);
        Scanner scanner = new Scanner("0\n");

        Game game = new Game(scanner, customDeck);
        game.playSingleRound();

        assertEquals(21, game.getPlayer().sumUpCards());
        assertEquals(17, game.getDealer().sumUpCards());
        assertEquals(1, game.getPlayer().getRoundsWon());
    }

    @Test
    @DisplayName("Игрок берет карту (1), затем останавливается (0) и побеждает")
    void testPlayerHitsThenStands() {

        List<Card> customCards = new ArrayList<>(List.of(
                new Card(Suit.SPADES, Rank.THREE),  // 3-я карта дилера (3) -> 18
                new Card(Suit.HEARTS, Rank.JACK),   // 3-я карта игрока (10) -> 20
                new Card(Suit.CLUBS, Rank.FIVE),    // 2-я карта игрока (5) -> 10
                new Card(Suit.SPADES, Rank.TEN),    // 2-я карта дилера (10) -> 15
                new Card(Suit.DIAMONDS, Rank.FIVE),  // 1-я карта игрока (5) -> 5
                new Card(Suit.HEARTS, Rank.FIVE)    // 1-я карта дилера (5) -> 5
        ));

        Deck customDeck = new Deck(customCards);
        Scanner scanner = new Scanner("1\n0\n");

        Game game = new Game(scanner, customDeck);
        game.playSingleRound();

        assertEquals(20, game.getPlayer().sumUpCards());
        assertEquals(18, game.getDealer().sumUpCards());
        assertEquals(1, game.getPlayer().getRoundsWon());
    }

    @Test
    @DisplayName("Игрок перебирает (Bust) при доборе карты")
    void testPlayerBusts() {

        List<Card> customCards = new ArrayList<>(List.of(
                new Card(Suit.SPADES, Rank.FIVE),   // 3-я карта игрока (5) -> 25
                new Card(Suit.CLUBS, Rank.QUEEN),   // 2-я карта игрока (10) -> 20
                new Card(Suit.SPADES, Rank.SEVEN),  // 2-я карта дилера (7) -> 17
                new Card(Suit.DIAMONDS, Rank.KING), // 1-я карта игрока (10) -> 10
                new Card(Suit.HEARTS, Rank.TEN)     // 1-я карта дилера (10) -> 10
        ));

        Deck customDeck = new Deck(customCards);
        Scanner scanner = new Scanner("1\n");

        Game game = new Game(scanner, customDeck);
        game.playSingleRound();

        assertEquals(25, game.getPlayer().sumUpCards());
        assertEquals(17, game.getDealer().sumUpCards());
        assertEquals(1, game.getDealer().getRoundsWon());
    }

    @Test
    @DisplayName("Ничья при равном количестве очков")
    void testTie() {

        List<Card> customCards = new ArrayList<>(List.of(
                new Card(Suit.SPADES, Rank.TEN),   // 2-я карта игрока (10) -> 20
                new Card(Suit.CLUBS, Rank.TEN),    // 2-я карта дилера (10) -> 20
                new Card(Suit.DIAMONDS, Rank.TEN), // 1-я карта игрока (10) -> 10
                new Card(Suit.HEARTS, Rank.TEN)    // 1-я карта дилера (10) -> 10
        ));

        Deck customDeck = new Deck(customCards);
        Scanner scanner = new Scanner("0\n");

        Game game = new Game(scanner, customDeck);
        game.playSingleRound();

        assertEquals(20, game.getPlayer().sumUpCards());
        assertEquals(20, game.getDealer().sumUpCards());
        assertEquals(1, game.getPlayer().getRoundsWon());
        assertEquals(1, game.getDealer().getRoundsWon());
    }
}