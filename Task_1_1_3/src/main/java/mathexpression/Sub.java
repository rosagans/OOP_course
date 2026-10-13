package mathexpression;

import java.util.Objects;

/**
 * Математическое выражение операции вычитания.
 */
public class Sub extends Expression {

    /**
     * Уменьшаемое выражение.
     */
    private final Expression minuend;

    /**
     * Вычитаемое выражение.
     */
    private final Expression subtrahend;

    /**
     * Создает выражение вычитания.
     *
     * @param minuend    уменьшаемое
     * @param subtrahend вычитаемое
     */
    public Sub(Expression minuend, Expression subtrahend) {
        this.minuend = minuend;
        this.subtrahend = subtrahend;
    }

    /**
     * Возвращает уменьшаемое выражение.
     *
     * @return объект Expression, представляющий вычитаемое
     */
    public Expression getMinuend() {
        return this.minuend;
    }

    /**
     * Возвращает вычитаемое выражение.
     *
     * @return объект Expression, представляющий вычитаемое
     */
    public Expression getSubtrahend() {
        return this.subtrahend;
    }

    /**
     * Возвращает строковое представление вычитания: "(minuend-subtrahend)".
     *
     * @return строковое представление выражения
     */
    @Override
    public String toString() {
        return '(' + this.minuend.toString() + "-" + this.subtrahend.toString() + ")";
    }

    /**
     * Вычисляет разность результатов вычисления операндов.
     *
     * @param signification контекст значений переменных
     * @return разность операндов
     * @throws WrongSignificationException если произошла ошибка при вычислении операндов
     */
    @Override
    public int eval(String signification) throws WrongSignificationException {
        return this.minuend.eval(signification) - this.subtrahend.eval(signification);
    }

    /**
     * Вычисляет производную разности по правилу: (f - g)' = f' - g'.
     *
     * @param givenVariable имя переменной дифференцирования
     * @return новое выражение разности производных
     */
    @Override
    public Expression derivative(String givenVariable) {
        return new Sub(this.minuend.derivative(givenVariable),
            this.subtrahend.derivative(givenVariable));
    }

    /**
     * Проверяет равенство данного выражения другому выражению. Без означивания
     *
     * @param expr выражение для сравнения
     * @return true, если выражение эквивалентны, false иначе
     */
    @Override
    public boolean equals(Object expr) {
        return expr instanceof Sub
            && this.minuend.equals(((Sub) expr).getMinuend())
            && this.subtrahend.equals(((Sub) expr).getSubtrahend());
    }

    /**
     * Считает и возвращает хэш данного выражения.
     *
     * @return хэш код объекта
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.minuend, this.subtrahend, '-');
    }
}
