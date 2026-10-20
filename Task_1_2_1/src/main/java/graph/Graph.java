package graph;

import java.util.List;
import java.util.Set;


/**
 * Интерфейс ориентированного графа.
 */
public interface Graph {

    /**
     * Добавляет вершину с номером v. Если такая уже есть - ничего не происходит
     *
     * @param v номер вершины
     */
    public void addVertex(int v);

    /**
     * Удаляет вершину с номером v. Выдает ошибку, если вершины нет в графе
     *
     * @param v номер вершины
     * @throws NotFoundVertexException Если вершины нет в графе
     */
    public void deleteVertex(int v);

    /**
     * Добавляет ребро из вершины v в вершину u.
     * Если такое уже есть - ничего не происходит.
     * Так же при отсутствии данных вершин - создает их.
     *
     * @param v номер первой вершины
     * @param u номер второй вершины
     */
    public void addEdge(int v, int u);

    /**
     * Удаляет ребро из вершины v в вершину u. Если ребра не было - ничего не происходит.
     * Выдает ошибку если хотя бы одной из вершин нет в графе.
     *
     * @param v номер первой вершины
     * @param u номер второй вершины
     * @throws NotFoundVertexException если хотя бы одной из вершин нет в графе
     */
    public void deleteEdge(int v, int u);

    /**
     * Возвращает список всех вершин, к которым есть ребро из данной.
     * Выдает ошибку если ребра нет в графе
     *
     * @param v номер вершины
     * @return List из "соседних" вершин
     * @throws NotFoundVertexException Если вершины нет в графе
     */
    public List<Integer> getNeighbours(int v);

    /**
     * Проверяет есть ли вершина в графе.
     *
     * @param v вершина
     * @return true, если вершина есть; false - иначе
     */
    public boolean containsVertex(int v);

    /**
     * Возвращает множество всех вершин графа.
     *
     * @return неизменяемое множество вершин
     */
    public Set<Integer> getVertices();
}
