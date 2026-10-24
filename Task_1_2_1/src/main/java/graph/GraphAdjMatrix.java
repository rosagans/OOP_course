package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Реализация графа с помощью матрицы смежности.
 */
public class GraphAdjMatrix extends AbstractGraph {

    /**
     * Матрица смежности.
     */
    private final ArrayList<ArrayList<Boolean>> adjMatrix;

    /**
     * Отображение вершины в индекс из матрицы смежности.
     */
    private final Map<Integer, Integer> vertexToIndex;

    /**
     * Отображение индекса из матрицы смежности в вершину.
     */
    private final Map<Integer, Integer> indexToVertex;

    /**
     * Связный список индексов в матрице смежности, таких, что
     * вершины, находящиеся по этим индексам были удалены, т.е
     * их место может занять новая добавленная вершина.
     */
    private final LinkedList<Integer> freeList;

    /**
     * Конструктор графа.
     * Инициализирует поля пустыми коллекциями.
     */
    public GraphAdjMatrix() {
        this.adjMatrix = new ArrayList<>();
        this.vertexToIndex = new HashMap<>();
        this.indexToVertex = new HashMap<>();
        this.freeList = new LinkedList<>();
    }

    @Override
    public void addVertex(int v) {
        if (containsVertex(v)) {
            return;
        }

        int index;
        if (!freeList.isEmpty()) {
            index = freeList.removeFirst();
            assert isRowAndColClear(index) : "free list выдал индекс,"
                + " у которого не очищены строка и столбец";
        } else {
            index = adjMatrix.size();

            for (List<Boolean> row : adjMatrix) {
                row.add(false);
            }

            ArrayList<Boolean> newRow = new ArrayList<>(
                Collections.nCopies(adjMatrix.size() + 1, false));
            adjMatrix.add(newRow);
        }
        vertexToIndex.put(v, index);
        indexToVertex.put(index, v);
    }

    @Override
    public void addEdge(int v, int u) {
        addVertex(v);
        addVertex(u);
        adjMatrix.get(vertexToIndex.get(v)).set(vertexToIndex.get(u), true);
    }

    @Override
    public void deleteVertex(int v) {
        if (!containsVertex(v)) {
            throw new NotFoundVertexException("Вершины " + v + " нету в графе.");
        }
        int index = vertexToIndex.remove(v);
        clearRowAndCol(index);
        freeList.add(index);
        indexToVertex.remove(index);
    }

    @Override
    public void deleteEdge(int v, int u) {
        if (!containsVertex(v)) {
            throw new NotFoundVertexException("Вершины " + v + " нету в графе.");
        }
        if (!containsVertex(u)) {
            throw new NotFoundVertexException("Вершины " + u + " нету в графе.");
        }
        adjMatrix.get(vertexToIndex.get(v)).set(vertexToIndex.get(u), false);
    }

    @Override
    public List<Integer> getNeighbours(int v) {
        if (!containsVertex(v)) {
            throw new NotFoundVertexException("Вершины " + v + " нету в графе.");
        }
        ArrayList<Boolean> connections = adjMatrix.get(vertexToIndex.get(v));
        ArrayList<Integer> neighbours = new ArrayList<>();
        for (int i = 0; i < connections.size(); i++) {
            if (connections.get(i)) {
                neighbours.add(indexToVertex.get(i));
            }
        }
        return neighbours;
    }



    @Override
    public boolean containsVertex(int v) {
        return vertexToIndex.containsKey(v);
    }

    @Override
    public Set<Integer> getVertices() {
        return Collections.unmodifiableSet(vertexToIndex.keySet());
    }


    /**
     * Заполняет строку и столбец под индексом idx в матрице смежности false'ами.
     *
     * @param idx индекс в матрице смежности
     */
    private void clearRowAndCol(int idx) {
        for (int i = 0; i < adjMatrix.size(); i++) {
            adjMatrix.get(idx).set(i, false);
        }
        for (List<Boolean> row : adjMatrix) {
            row.set(idx, false);
        }
    }

    /**
     * Проверяет, что в матрице смежности у строки и столбца под индексом idx все элементы - fals.
     *
     * @param idx индекс проверяемых строки и столбца
     * @return true, если все элементы - false; false - иначе
     */
    private boolean isRowAndColClear(int idx) {
        for (boolean val : adjMatrix.get(idx)) {
            if (val) {
                return false;
            }
        }
        for (List<Boolean> row : adjMatrix) {
            if (row.get(idx)) {
                return false;
            }
        }
        return true;
    }
}
