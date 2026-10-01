package mathexpression;

/**
 * Математическое выражение переменной.
 */
public class Variable extends Expression {

    /**
     * Имя переменной.
     */
    private final String label;

    /**
     * Создает выражение переменной с заданным именем.
     *
     * @param label имя переменной
     */
    public Variable(String label) {
        this.label = label;
    }

    /**
     * Возвращает имя переменной.
     *
     * @return имя переменной
     */
    public String getLabel() {
        return label;
    }

    /**
     * Возвращает имя переменной.
     *
     * @return имя переменной
     */
    @Override
    public String toString() {
        return this.label;
    }

    /**
     * Извлекает значение переменной из контекстной строки и возвращает его.
     *
     * @param signification строка с парами "переменная = значение", разделенными ';'
     * @return целочисленное значение переменной
     * @throws Exception если переменная с таким именем отсутствует в контексте
     */
    @Override
    public int eval(String signification) throws Exception {
        String[] significations = signification.split(";");
        for (int i = 0; i < significations.length; i++) {
            String curVariableSignification = significations[i].strip();
            String[] parts = curVariableSignification.split(" ");
            if (parts[0].equals(this.label)) {
                return Integer.parseInt(parts[2]);
            }
        }
        throw new Exception("Wrong signification! variable ("
                + this.label + ") didnt get any signification.");
    }

    /**
     * Вычисляет производную переменной по заданной переменной дифференцирования.
     *
     * @param givenVariable имя переменной дифференцирования
     * @return новый объект Number со значением 1, если имена совпадают;
     * новый объект Number со значением 0, иначе
     */
    @Override
    public Expression derivative(String givenVariable) {
        if (givenVariable.equals(this.label)) {
            return new Number(1);
        } else {
            return new Number(0);
        }
    }

    /**
     * Проверяет равенство текущей переменной другому выражению.
     *
     * @param expr выражение для сравнения
     * @return true, если выражение имеют идентичные имена переменных
     */
    @Override
    public boolean equals(Expression expr) {
        return expr instanceof Variable && this.label.equals(((Variable) expr).getLabel());
    }
}
