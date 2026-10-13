package mathexpression;

/**
 * Ошибка некорректного строкового задания выражения.
 */
public class IncorrectStringExpressionException extends Exception {

    /**
     * Ошибка некорректного строкового задания выражения.
     *
     * @param message Сообщение ошибки
     */
    public IncorrectStringExpressionException(String message) {
        super(message);
    }
}
