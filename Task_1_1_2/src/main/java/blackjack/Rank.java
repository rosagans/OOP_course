package blackjack;

public enum Rank {
    TWO("Двойка", 2),
    THREE("Тройки", 3),
    FOUR("Четверка", 4),
    FIVE("Пятерка", 5),
    SIX("Шестерка", 6),
    SEVEN("Семерка", 7),
    EIGHT("Восьмерка", 8),
    NINE("Девятка", 9),
    TEN("Десятка", 10),
    JACK("Валет", 10),
    QUEEN("Дама", 10),
    KING("Король", 10),
    ACE("Туз", 11);

    private final String label;

    private final int baseValue;

    private Rank(String label, int baseValue) {
        this.label = label;
        this.baseValue = baseValue;
    }

    public String getLabel() {
        return this.label;
    }

    public int getBaseValue() {
        return this.baseValue;
    }
}
