package graph;

/**
 * Неправильный формат файла для создания из него графа.
 */
public class GraphFileFormatException extends Exception {

    /**
     * Неправильный формат файла для создания из него графа.
     *
     * @param message Сообщение ошибки
     */
    public GraphFileFormatException(String message) {
        super(message);
    }
}
