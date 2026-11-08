package gradebook;

/**
 * Перечисление оценок.
 */
public enum Grade {
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private final int value;

    private Grade(int value) {
        this.value = value;
    }

    public int toInt() {
        return value;
    }
}
