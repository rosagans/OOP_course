package blackjack;


/** Enum для мастей карт. */
public enum Suit {
    HEARTS("Черви"),
    DIAMONDS("Буби"),
    SPADES("Пики"),
    CLUBS("Крести");

    private final String label;

    private Suit(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
