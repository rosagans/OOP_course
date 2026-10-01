package mathexpression;

/**
 * Математическое выражение операции деления.
 */
public class Div extends Expression {

    /**
     * Делимое выражение.
     */
    private final Expression dividend;

    /**
     * Делитель выражение.
     */
    private final Expression divisor;

    /**
     * Создает выражение деления.
     *
     * @param dividend делимое
     * @param divisor делитель
     */
    public Div(Expression dividend, Expression divisor) {
        this.dividend = dividend;
        this.divisor = divisor;
    }

    /**
     * Возвращает делимое выражение.
     *
     * @return делимое
     */
    public Expression getDividend() {
        return this.dividend;
    }

    /**
     * Возвращает делитель выражение.
     *
     * @return делитель
     */
    public Expression getDivisor() {
        return this.divisor;
    }

    /**
     * Возвращает строковое представление деления: "(dividend/divisor)".
     *
     * @return строковое представление
     */
    @Override
    public String toString() {
        return "(" + this.dividend.toString() + "/" + this.divisor.toString() + ")";
    }

    /**
     * Вычисляет целочисленное частное от деления операндов.
     *
     * @param signification контекст значений переменных
     * @return результат деления
     * @throws ArithmeticException при делении на ноль
     * @throws Exception при ошибке вычисления операндов
     */
    @Override
    public int eval(String signification) throws Exception {
        if (this.divisor.eval(signification) == 0) {
            throw new ArithmeticException("You cant divide by zero!");
        }
        return this.dividend.eval(signification) / this.divisor.eval(signification);
    }

    /**
     * Вычисляет производную частного по правилу: (f / g)' = (f' * g - f * g') / (g * g)}.
     *
     * @param givenVariable имя переменной дифференцирования
     * @return новое выражение производной деления
     */
    @Override
    public Expression derivative(String givenVariable) {
        return new Div(
                new Sub(
                        new Mul(this.dividend.derivative(givenVariable), this.divisor),
                        new Mul(this.dividend, this.divisor.derivative(givenVariable))
                ),
                new Mul(this.divisor, this.divisor));
    }

    /**
     * Проверяет равенство данного выражения деления другому выражению.
     * Без означивания
     *
     * @param expr выражение для сравнения
     * @return true, если операнды деления равны
     */
    @Override
    public boolean equals(Expression expr) {
        return expr instanceof Div
                && this.dividend.equals(((Div) expr).getDividend())
                && this.divisor.equals(((Div) expr).getDivisor());
    }
}
