package blackjack;

/**
 * Класс, представляющий игровую карту в блэкджеке.
 * Хранит информацию о масти, достоинстве и текущей стоимости карты в очках.
 */
public class Card {

    /** Масть карты. */
    private final Suit suit;

    /** Ранг карты */
    private final Rank rank;

    /** Текущая стоимость карты по правилам блэкджека. */
    private int currentValue;

    /**
     * Создает карту по её масти и рангу.
     *
     * @param suit масть карты.
     * @param rank ранг карты.
     */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        this.currentValue = rank.getBaseValue();
    }

    /**
     * Возвращает текстовое представление карты в формате "Ранг Масть (Очки)".
     *
     * @return строковое представление карты
     */
    @Override
    public String toString() {
        return String.format("%s %s (%d)", this.rank.getLabel(), this.suit.getLabel(), this.currentValue);
    }

    /**
     * Проверяет, является ли карта Тузом.
     *
     * @return true, если карта является Тузом, иначе false
     */
    public boolean isAce() {
        return this.rank == Rank.ACE;
    }

    /**
     * Возвращает текущее количество очков, которое дает карта.
     *
     * @return стоимость карты в очках
     */
    public int getValue() {
        return this.currentValue;
    }

    /** Устанавливает currentValue = 1 у туза.
     * @throws IllegalStateException при попытке поменять значение не у туза.
     *
     */
    public void setAceValueOne() {
        if (!isAce()) {
            throw new IllegalStateException("Изменить стоимость на 1 можно только у Туза!");
        }
        this.currentValue = 1;
    }
}