package mathexpression;

/**
 * Перечисление поддерживаемых бинарных математических операций.
 */
public enum Operations {

    PLUS('+'),
    MINUS('-'),
    MULTIPLY('*'),
    DIVIDE('/');

    /**
     * Символьный ярлык операции.
     */
    private final char label;

    /**
     * Конструктор элемента перечисления.
     *
     * @param label символ математической операции
     */
    private Operations(char label) {
        this.label = label;
    }

    /**
     * Проверяет, содержится ли переданный символ среди известных операций.
     *
     * @param c проверяемый символ
     * @return true, если символ соответствует одной из операций enum; false в противном случае
     */
    public static boolean contains(char c) {
        for (Operations operation : Operations.values()) {
            if (c == operation.label) {
                return true;
            }
        }
        return false;
    }
}
