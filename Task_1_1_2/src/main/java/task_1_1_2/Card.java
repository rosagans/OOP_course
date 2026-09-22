package task_1_1_2;

/**
 * Класс, представляющий игровую карту в блэкджеке.
 * Хранит информацию о масти, достоинстве и текущей стоимости карты в очках.
 */
public class Card {

    /** Массив строковых названий мастей. */
    private static final String[] SUITS = {"Черви", "Буби", "Пики", "Крести"};

    /** Массив строковых названий рангов (достоинств) карт. */
    private static final String[] RANKS = {
            "Двойка", "Тройка", "Четверка", "Пятерка", "Шестерка", "Семерка",
            "Восьмерка", "Девятка", "Десятка", "Валет", "Дама", "Король", "Туз"
    };

    /** Уникальный индекс карты в колоде (от 0 до 51). */
    private final int number;

    /** Текущая стоимость карты в очках блэкджека. */
    private int value;

    /**
     * Создает карту по её уникальному индексу.
     *
     * @param number индекс карты от 0 до 51
     */
    public Card(int number) {
        this.number = number;
        this.value = initValue();
    }

    /**
     * Возвращает текстовое представление карты в формате "Ранг Масть (Очки)".
     *
     * @return строковое представление карты
     */
    @Override
    public String toString() {
        String rankName = RANKS[this.number % 13];
        String suitName = SUITS[this.number / 13];
        return String.format("%s %s (%d)", rankName, suitName, this.value);
    }

    /**
     * Вычисляет начальное количество очков для карты по правилам блэкджека.
     *
     * @return 11 для Туза, 10 для картинок и 10-к, достоинство + 2 для остальных
     */
    private int initValue() {
        if (this.number % 13 == 12) return 11;
        if (7 < this.number % 13 && this.number % 13 < 12) return 10;
        return this.number % 13 + 2;
    }

    /**
     * Проверяет, является ли карта Тузом.
     *
     * @return true, если карта является Тузом, иначе false
     */
    public boolean isAce() {
        return this.number % 13 == 12;
    }

    /**
     * Возвращает текущее количество очков, которое дает карта.
     *
     * @return стоимость карты в очках
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Изменяет количество очков карты (например, снижает с 11 до 1 при переборе).
     *
     * @param newValue новое значение очков карты
     */
    public void setValue(int newValue) {
        this.value = newValue;
    }
}