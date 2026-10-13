package mathexpression;

import java.util.Objects;

/**
 * Математическое выражение операции сложения.
 */
public class Add extends Expression {

    /**
     * Левое слагаемое выражения.
     */
    private final Expression leftAddend;

    /**
     * Правое слагаемое выражения.
     */
    private final Expression rightAddend;

    /**
     * Создает новое выражение сложения с указанными слагаемыми.
     *
     * @param leftAddend  левое слагаемое
     * @param rightAddend правое слагаемое
     */
    public Add(Expression leftAddend, Expression rightAddend) {
        this.leftAddend = leftAddend;
        this.rightAddend = rightAddend;
    }

    /**
     * Возвращает левое слагаемое.
     *
     * @return объект Expression, являющийся левым слагаемым
     */
    public Expression getLeftAddend() {
        return this.leftAddend;
    }

    /**
     * Возвращает правое слагаемое.
     *
     * @return объект Expression, являющийся правым слагаемым
     */
    public Expression getRightAddend() {
        return this.rightAddend;
    }

    /**
     * Формирует строковое представление сложения в скобках: "(left+right)".
     *
     * @return строковое представление операции
     */
    @Override
    public String toString() {
        return "(" + this.leftAddend.toString() + "+" + this.rightAddend.toString() + ")";
    }

    /**
     * Вычисляет сумму результатов вычисления левого и правого операндов.
     *
     * @param signification контекст значений переменных
     * @return сумма операндов
     * @throws WrongSignificationException если возникла ошибка при вычислении операндов
     */
    @Override
    public int eval(String signification) throws WrongSignificationException {
        return this.leftAddend.eval(signification) + this.rightAddend.eval(signification);
    }

    /**
     * Вычисляет производную суммы по правилу: (f + g)' = f' + g'.
     *
     * @param givenVariable имя переменной дифференцирования
     * @return новое выражение сложения производных
     */
    @Override
    public Expression derivative(String givenVariable) {
        return new Add(this.leftAddend.derivative(givenVariable),
            this.rightAddend.derivative(givenVariable));
    }

    /**
     * Проверяет равенство данного выражения сложения другому выражению. Некоммутативно и без
     * означивания.
     *
     * @param expr выражение для сравнения
     * @return true, если выражение эквивалентны, false иначе
     */
    @Override
    public boolean equals(Object expr) {
        return expr instanceof Add
            && this.leftAddend.equals(((Add) expr).getLeftAddend())
            && this.rightAddend.equals(((Add) expr).getRightAddend());
    }

    /**
     * Считает и возвращает хэш данного выражения.
     *
     * @return хэш код объекта
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.leftAddend, this.rightAddend, '+');
    }
}
