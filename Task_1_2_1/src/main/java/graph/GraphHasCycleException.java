package graph;

/**
 * Наход цикла в графе.
 */
public class GraphHasCycleException extends RuntimeException {

    /**
     * Наход цикла в графе.
     *
     * @param message Сообщение ошибки
     */
    public GraphHasCycleException(String message) {
        super(message);
    }
}
