package mathexpression;

import java.util.Objects;

/**
 * Математическое выражение операции умножения.
 */
public class Mul extends Expression {

    /**
     * Левый множитель.
     */
    private final Expression leftMultiplier;

    /**
     * Правый множитель.
     */
    private final Expression rightMultiplier;

    /**
     * Создает новое выражение умножения.
     *
     * @param leftMultiplier  левый множитель
     * @param rightMultiplier правый множитель
     */
    public Mul(Expression leftMultiplier, Expression rightMultiplier) {
        this.leftMultiplier = leftMultiplier;
        this.rightMultiplier = rightMultiplier;
    }

    /**
     * Возвращает левый множитель.
     *
     * @return левый множитель
     */
    public Expression getLeftMultiplier() {
        return this.leftMultiplier;
    }

    /**
     * Возвращает правый множитель.
     *
     * @return правый множитель
     */
    public Expression getRightMultiplier() {
        return this.rightMultiplier;
    }

    /**
     * Возвращает строковое представление умножения: "(left*right)".
     *
     * @return строковое представление
     */
    @Override
    public String toString() {
        return "(" + this.leftMultiplier.toString() + "*" + this.rightMultiplier.toString() + ")";
    }

    /**
     * Вычисляет произведение операндов.
     *
     * @param signification контекст значений переменных
     * @return результат умножения
     * @throws WrongSignificationException при ошибке вычисления операндов
     */
    @Override
    public int eval(String signification) throws WrongSignificationException {
        return this.leftMultiplier.eval(signification) * this.rightMultiplier.eval(signification);
    }

    /**
     * Вычисляет производную произведения по правилу: (f * g)' = f' * g + f * g'.
     *
     * @param givenVariable имя переменной дифференцирования
     * @return новое выражение производной произведения
     */
    @Override
    public Expression derivative(String givenVariable) {
        return new Add(new Mul(this.leftMultiplier.derivative(givenVariable), this.rightMultiplier),
            new Mul(this.leftMultiplier, this.rightMultiplier.derivative(givenVariable)));
    }

    /**
     * Проверяет равенство выражения умножения другому выражению. Некоммутативно и без означивания.
     *
     * @param expr выражение для сравнения
     * @return true, если выражения эквивалентны
     */
    @Override
    public boolean equals(Object expr) {
        return expr instanceof Mul
            && this.leftMultiplier.equals(((Mul) expr).getLeftMultiplier())
            && this.rightMultiplier.equals(((Mul) expr).getRightMultiplier());
    }

    /**
     * Считает и возвращает хэш данного выражения.
     *
     * @return хэш код объекта
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.leftMultiplier, this.rightMultiplier, '*');
    }
}
