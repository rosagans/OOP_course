package mathExpression;

/**
 * Абстрактный базовый класс математического выражения.
 */
public abstract class Expression {

    /**
     * Вычисляет числовое значение выражения на основе переданных значений переменных.
     *
     * @param signification строка с контекстом переменных в формате "var1 = val1; var2 = val2;..."
     * @return целочисленный результат вычисления выражения
     * @throws Exception если переменная не найдена или формат строки неверный
     */
    public abstract int eval(String signification) throws Exception;

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
    public abstract boolean equals(Expression expr);

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
     * @throws Exception если строка имеет некорректный синтаксис
     */
    public static Expression makeExpression(String string) throws Exception {
        if (string.isEmpty()) {
            throw new Exception("Cannot parse current expression");
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
                throw new Exception("Cannot parse current expression");
            }

            String expr1 = string.substring(1, indexOfOperation);
            String expr2 = string.substring(indexOfOperation + 1, string.length() - 1);

            char operation = string.charAt(indexOfOperation);
            Expression final_expr = switch (operation) {
                case '-' -> new Sub(makeExpression(expr1), makeExpression(expr2));
                case '+' -> new Add(makeExpression(expr1), makeExpression(expr2));
                case '*' -> new Mul(makeExpression(expr1), makeExpression(expr2));
                case '/' -> new Div(makeExpression(expr1), makeExpression(expr2));
                default -> throw new Exception("Cannot parse current expression");
            };
            return final_expr;

        } else {
            throw new Exception("Cannot parse current expression");
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
            if(Operations.contains(string.charAt(i)) && parenthesisCount == 1) {
                return i;
            }
        }
        return -1;
    }
}
