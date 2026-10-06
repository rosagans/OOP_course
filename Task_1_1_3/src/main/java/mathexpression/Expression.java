package mathexpression;

/**
 * Абстрактный базовый класс математического выражения.
 */
public abstract class Expression {

    /**
     * Вычисляет числовое значение выражения на основе переданных значений переменных.
     *
     * @param signification строка с контекстом переменных в формате "var1 = val1; var2 = val2;..."
     * @return целочисленный результат вычисления выражения
     * @throws WrongSignificationException если вычисления невозможно (переменная не найдена, формат
     *                                     строки неверный, деление на ноль)
     *
     */
    public abstract int eval(String signification) throws WrongSignificationException;

    /**
     * Возвращает строковое представление математического выражения.
     *
     * @return строковое представление выражения
     */
    @Override
    public abstract String toString();

    /**
     * Вычисляет символьную производную выражения по заданной переменной.
     *
     * @param variable имя переменной, по которой берется производная
     * @return новое математическое выражение, представляющее производную
     */
    public abstract Expression derivative(String variable);

    /**
     * Проверяет эквивалентность текущего выражения другому выражению.
     *
     * @param expr выражение для сравнения
     * @return true если выражения структурно равны; false в противном случае
     */
    @Override
    public abstract boolean equals(Object expr);

    /**
     * Считает и возвращает хэш данного выражения.
     *
     * @return хэш код объекта
     */
    @Override
    public abstract int hashCode();

    /**
     * Выводит строковое представление выражения в стандартный поток вывода.
     */
    public void print() {
        System.out.println(toString());
    }

    /**
     * Разбирает строковое представление выражения, создает по нему объект класса Expression.
     *
     * @param string строка с математическим выражением
     * @return объект Expression, представляющий распарсенное выражение
     * @throws IncorrectStringExpressionException если строка имеет некорректный синтаксис
     */
    public static Expression makeExpression(String string)
        throws IncorrectStringExpressionException {
        if (string.isEmpty()) {
            throw new IncorrectStringExpressionException("Cannot parse current expression:"
                + "whether given empty string or incorrect string.");
        }

        int indexOfOperation = findFirstOperation(string);
        if (indexOfOperation == -1) {
            if (string.matches("[1-9][0-9]*")) {
                return new Number(Integer.parseInt(string));
            } else if (string.matches("[a-zA-Z]+")) {
                return new Variable(string);
            }
        }

        if (string.startsWith("(") && string.endsWith(")")) {
            if (indexOfOperation == -1) {
                throw new IncorrectStringExpressionException("Cannot parse current expression:"
                    + "missing operation in '" + string + "'.");
            }

            String expr1 = string.substring(1, indexOfOperation);
            String expr2 = string.substring(indexOfOperation + 1, string.length() - 1);

            char operation = string.charAt(indexOfOperation);
            Expression finalExpr = switch (operation) {
                case '-' -> new Sub(makeExpression(expr1), makeExpression(expr2));
                case '+' -> new Add(makeExpression(expr1), makeExpression(expr2));
                case '*' -> new Mul(makeExpression(expr1), makeExpression(expr2));
                case '/' -> new Div(makeExpression(expr1), makeExpression(expr2));
                default -> throw new IncorrectStringExpressionException(
                    "Cannot parse current expression:"
                        + "operation '" + operation + "' is not supported.");
            };
            return finalExpr;

        } else {
            throw new IncorrectStringExpressionException("Cannot parse current expression:"
                + "string '" + string + "' should start and end with parenthesis.");
        }
    }

    /**
     * Находит индекс бинарной операции, находящейся на первом уровне вложенности скобок.
     *
     * @param string анализируемая строка выражения
     * @return индекс символа операции в строке или -1, если операция верхнего уровня не найдена
     */
    private static int findFirstOperation(String string) {
        int parenthesisCount = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '(') {
                parenthesisCount++;
            } else if (string.charAt(i) == ')') {
                parenthesisCount--;
            }
            if (Expression.isOperator(string.charAt(i)) && parenthesisCount == 1) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Проверяет, является ли данный char операцией.
     *
     * @param c анализируемый char
     * @return true, если является операцией, false иначе
     */
    private static boolean isOperator(char c) {
        return switch (c) {
            case '+', '-', '*', '/' -> true;
            default -> false;
        };
    }
}
