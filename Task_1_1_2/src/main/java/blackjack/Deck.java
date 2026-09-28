package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс, представляющий колоду карт в блэкджек.
 * Достает произвольную карту, перезаполняется при необходимости.
 */
public class Deck {

    /** Список карт, находящихся в игре. Т.е те, которые можно вытащить. */
    private List<Card> cards;

    /**Конструирует колоду карт, сразу ее тасуя. */
    public Deck() {
        refillCards();
    }

    /** Конструктор для создания кастомной колоды. */
    public Deck(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    /** Перезаполняет карты в колоде и тасует их. */
    public void refillCards() {
        this.cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                Card card = new Card(suit, rank);
                this.cards.add(card);
            }
        }
        Collections.shuffle(this.cards);
    }

    /**
     * Достает произвольную карту из колоды.
     *
     * @return произвольная карта из колоды.
     * @throws IllegalStateException при попытке достать карту из пустой колоды.
     */
    public Card draw() {
        if (this.cards.isEmpty()) {
            throw new IllegalStateException("Колода пуста! Нельзя взять карту.");
        }
        return this.cards.removeLast();
    }

    /**
     * Проверяет насколько мало карт осталось в колоде.
     *
     * @return true, если карт в колоде меньше половины; false иначе
     */
    public boolean isLowOnCards() {
        return this.cards.size() < 26;
    }

    /**
     * Возвращает количество карт в колоде в настоящий момент.
     *
     * @return количество карт в колоде в настоящий момент
     */
    public int getSize() {
        return this.cards.size();
    }
}