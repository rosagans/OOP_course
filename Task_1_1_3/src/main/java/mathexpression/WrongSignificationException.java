package mathexpression;

/**
 * Ошибка неправильного означивания переменных при вызове функции eval().
 */
public class WrongSignificationException extends Exception {

    /**
     * Ошибка неправильного означивания переменных при вызове функции eval().
     *
     * @param message Сообщение ошибки
     */
    public WrongSignificationException(String message) {
        super(message);
    }
}
