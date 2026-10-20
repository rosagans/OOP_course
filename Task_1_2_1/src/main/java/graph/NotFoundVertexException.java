package graph;
 /**
 * Ненаход вершины в графе.
 */
public class NotFoundVertexException extends RuntimeException {

    /**
     * Ненаход вершины в графе.
     *
     * @param message Сообщение ошибки
     */
    public NotFoundVertexException(String message) {
        super(message);
    }
}
