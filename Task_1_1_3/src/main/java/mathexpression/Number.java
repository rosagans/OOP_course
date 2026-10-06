package mathexpression;

import java.util.Objects;

/**
 * Математическое выражение числовой константы.
 */
public class Number extends Expression {

    /**
     * Числовое значение константы.
     */
    private final int value;

    /**
     * Создает выражение числовой константы.
     *
     * @param value целочисленное значение
     */
    public Number(int value) {
        this.value = value;
    }

    /**
     * Возвращает хранимое числовое значение.
     *
     * @return числовое значение
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Преобразует числовое значение в строку.
     *
     * @return строковое представление числа
     */
    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    /**
     * Возвращает значение константы.
     *
     * @param signification контекст значений переменных (игнорируется)
     * @return значение константы
     */
    @Override
    public int eval(String signification) {
        return this.value;
    }

    /**
     * Вычисляет производную числовой константы (всегда равна 0).
     *
     * @param givenVariable имя переменной дифференцирования (игнорируется)
     * @return новый объект Number со значением 0
     */
    @Override
    public Expression derivative(String givenVariable) {
        return new Number(0);
    }

    /**
     * Проверяет равенство текущего числа другому выражению.
     *
     * @param expr выражение для сравнения
     * @return true, если выражение равны (равны константы)
     */
    @Override
    public boolean equals(Object expr) {
        return expr instanceof Number && this.value == ((Number) expr).getValue();
    }

    /**
     * Считает и возвращает хэш данного числа.
     *
     * @return хэш код объекта
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.value);
    }
}
