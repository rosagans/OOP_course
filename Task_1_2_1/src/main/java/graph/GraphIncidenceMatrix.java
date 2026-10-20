package graph;

import java.util.*;

/**
 * Реализация графа с помощью матрицы инцидентности.
 */
public class GraphIncidenceMatrix extends  AbstractGraph{

    /**
     * Матрица инцидентности.
     */
    private final ArrayList<ArrayList<Integer>> incidMatrix;

    /**
     * Отображение вершины в индекс из матрицы инцидентности.
     */
    private final Map<Integer, Integer> vertexToIndex;

    /**
     * Отображение индекса из матрицы инцидентности в вершину
     */
    private final Map<Integer, Integer> indexToVertex;

    /**
     * Связный список индексов строк матрицы инцидентности, таких, что
     * вершины, находящиеся по этим индексам были удалены, т.е
     * их место может занять новая добавленная вершина.
     */
    private final LinkedList<Integer> freeListVertices;

    /**
     * Связный список индексов в строках матрице инцидентности, таких, что
     * ребра, находящиеся по этим индексам были удалены, т.е
     * их место может занять новое добавленное ребро.
     */
    private final LinkedList<Integer> freeListEdges;

    /**
     * Конструктор графа.
     * Инициализирует поля пустыми коллекциями.
     */
    public GraphIncidenceMatrix() {
        this.incidMatrix = new ArrayList<>();
        this.vertexToIndex = new HashMap<>();
        this.indexToVertex = new HashMap<>();
        this.freeListVertices = new LinkedList<>();
        this.freeListEdges = new LinkedList<>();
    }

    @Override
    public void addVertex(int v) {
        if (containsVertex(v)) {
            return;
        }

        int index;
        if (!freeListVertices.isEmpty()) {
            index = freeListVertices.removeFirst();
            assert isRowClear(index) : "freeListVertices выдал индекс, у которого не очищена строка";
        } else {
            index = incidMatrix.size();
            if (incidMatrix.isEmpty()) {
                incidMatrix.add(new ArrayList<>());
            } else {
                ArrayList<Integer> newRow = new ArrayList<>(
                    Collections.nCopies(incidMatrix.getFirst().size(), 0));
                incidMatrix.add(newRow);
            }


        }
        vertexToIndex.put(v, index);
        indexToVertex.put(index, v);
    }

    @Override
    public void addEdge(int v, int u) {
        addVertex(v);
        addVertex(u);
        for (int i = 0; i < incidMatrix.getFirst().size(); i++) {
            if (incidMatrix.get(vertexToIndex.get(v)).get(i) == 1 &&
                incidMatrix.get(vertexToIndex.get(u)).get(i) == -1) {

                return;
            }
        }

        int index;
        if (!freeListEdges.isEmpty()) {
            index = freeListEdges.removeFirst();
            assert isColClear(index);
        } else {
            index = incidMatrix.getFirst().size();
            for (ArrayList<Integer> row : incidMatrix) {
                row.add(0);
            }
        }
        int rowV = vertexToIndex.get(v);
        int rowU = vertexToIndex.get(u);
        if (rowV == rowU) {
            incidMatrix.get(rowV).set(index, 2); // 2 - признак петли
        } else {
            incidMatrix.get(rowV).set(index, 1);
            incidMatrix.get(rowU).set(index, -1);
        }
    }

    @Override
    public void deleteVertex(int v) {
        if (!containsVertex(v)) {
            throw new NotFoundVertexException("Вершины " + v + " нету в графе.");
        }
        int index = vertexToIndex.remove(v);
        for (int j = 0; j < incidMatrix.getFirst().size(); j++) {
            if (incidMatrix.get(index).get(j) != 0) {
                deleteCol(j);
            }
        }
        deleteRow(index);
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
        int index1 = vertexToIndex.get(v);
        int index2 = vertexToIndex.get(u);
        for (int j = 0; j < incidMatrix.getFirst().size(); j++) {
            if (incidMatrix.get(index1).get(j) == 1 && incidMatrix.get(index2).get(j) == -1) {
                deleteCol(j);
                return;
            } else if (index1 == index2 && incidMatrix.get(index1).get(j) == 2) {
                deleteCol(j);
                return;
            }
        }
    }

    @Override
    public List<Integer> getNeighbours(int v) {
        if (!containsVertex(v)) {
            throw new NotFoundVertexException("Вершины " + v + " нету в графе.");
        }
        List<Integer> neighbours = new ArrayList<>();
        int index = vertexToIndex.get(v);
        for (int j = 0; j < incidMatrix.getFirst().size(); j++) {
            if (incidMatrix.get(index).get(j) == 2) {
                neighbours.add(v);
                continue;
            }
            if (incidMatrix.get(index).get(j) == 1) {
                for (int k = 0; k < incidMatrix.size(); k++) {
                    if (incidMatrix.get(k).get(j) == -1) {
                        neighbours.add(indexToVertex.get(k));
                        break;
                    }
                }
            }
        }
        return neighbours;
    }

    @Override
    public Set<Integer> getVertices() {
        return Collections.unmodifiableSet(vertexToIndex.keySet());
    }

    @Override
    public boolean containsVertex(int v) {
        return vertexToIndex.containsKey(v);
    }

    /**
     * Зануляет столбец с индексом index в матрице инцидентности,
     * добавляет его в freeListEdges.
     *
     * @param index индекс столбца
     */
    private void deleteCol(int index) {
        for (ArrayList<Integer> row : incidMatrix) {
            row.set(index, 0);
        }
        freeListEdges.add(index);
    }

    /**
     * Зануляет строку с индексом index в матрице инцидентности,
     * добавляет его в freeListVertices.
     *
     * @param index индекс строки
     */
    private void deleteRow(int index) {
        for (int i = 0; i < incidMatrix.getFirst().size(); i++) {
            incidMatrix.get(index).set(i, 0);
        }
        freeListVertices.add(index);
    }

    /**
     * Проверяет, что в матрице инцидентности index'овая строка состоит из нулей,
     * т.е у вершины, соответствующей данному индексу нет входящих и исходящих ребер.
     *
     * @param index индекс строки
     * @return true, если строка состоит из нулей, false - иначе
     */
    private boolean isRowClear(int index) {
        for (int val: incidMatrix.get(index)) {
            if (val != 0) return false;
        }
        return true;
    }

    /**
     * Проверяет, что в матрице инцидентности index'овый столбец состоит из нулей,
     * т.е ребра, соответсвующего этому индексу нет в графе.
     *
     * @param index индекс столбца
     * @return true, если столбец состоит из нулей, false - иначе
     */
    private boolean isColClear(int index) {
        for (List<Integer> row : incidMatrix) {
            if (row.get(index) != 0) return false;
        }
        return true;
    }

}
