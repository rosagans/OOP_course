package graph;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;

/**
 * Реализация графа с помощью списка смежности.
 */
public class GraphAdjList extends AbstractGraph {

    /**
     * Список смежности.
     */
    private final Map<Integer, Set<Integer>> adjList;

    /**
     * Конструктор графа.
     * Создает пустой список смежности
     */
    public GraphAdjList() {
        this.adjList = new HashMap<>();
    }

    @Override
    public void addVertex(int v) {
        if (containsVertex(v)) {
            return;
        }
        adjList.put(v, new HashSet<>());
    }

    @Override
    public void addEdge(int v, int u) {
        if (adjList.get(v)!=null && adjList.get(v).contains(u)) {
            return;
        }
        addVertex(v);
        addVertex(u);
        adjList.get(v).add(u);
    }

    @Override
    public void deleteVertex(int v) {
        if (!containsVertex(v)) {
            throw new NotFoundVertexException("Вершины " + v + " нету в графе.");
        }
        adjList.remove(v);
        for (Set<Integer> neighbours: adjList.values()) {
            neighbours.remove(v);
        }
    }

    @Override
    public void deleteEdge(int v, int u) {
        if (!containsVertex(v)) {
            throw new NotFoundVertexException("Вершины " + v + " нету в графе.");
        }
        if (!containsVertex(u)) {
            throw new NotFoundVertexException("Вершины " + u + " нету в графе.");
        }
        adjList.get(v).remove(u);
    }

    @Override
    public List<Integer> getNeighbours(int v) {
        if (!containsVertex(v)) {
            throw new NotFoundVertexException("Вершины " + v + " нету в графе.");
        }
        return new ArrayList<>(adjList.get(v));
    }

    @Override
    public boolean containsVertex(int v) {
        return adjList.containsKey(v);
    }

    @Override
    public Set<Integer> getVertices() {
        return Collections.unmodifiableSet(adjList.keySet());
    }
}
