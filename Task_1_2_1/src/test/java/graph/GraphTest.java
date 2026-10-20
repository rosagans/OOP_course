package graph;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    /**
     * Провайдер реализаций графов для параметризованных тестов.
     */
    static Stream<Graph> graphProvider() {
        return Stream.of(
            new GraphAdjList(),
            new GraphAdjMatrix(),
            new GraphIncidenceMatrix()
        );
    }

    @ParameterizedTest
    @MethodSource("graphProvider")
    void testVertexOperations(Graph g) {
        g.addVertex(1);
        g.addVertex(1); // Дубликат

        assertTrue(g.containsVertex(1));
        assertFalse(g.containsVertex(2));
        assertEquals(1, g.getVertices().size());

        g.deleteVertex(1);
        assertFalse(g.containsVertex(1));
        assertTrue(g.getVertices().isEmpty());
    }

    @ParameterizedTest
    @MethodSource("graphProvider")
    void testEdgeOperationsAndNeighbours(Graph g) {
        g.addEdge(1, 2); // Вершины должны создаться автоматически
        g.addEdge(1, 2); // Дубликат
        g.addEdge(1, 3);

        assertTrue(g.containsVertex(1) && g.containsVertex(2) && g.containsVertex(3));

        List<Integer> neighbours = g.getNeighbours(1);
        assertEquals(2, neighbours.size());
        assertTrue(neighbours.containsAll(List.of(2, 3)));

        g.deleteEdge(1, 2);
        assertEquals(List.of(3), g.getNeighbours(1));
    }

    @ParameterizedTest
    @MethodSource("graphProvider")
    void testSelfLoop(Graph g) {
        g.addEdge(1, 1); // Петля
        assertEquals(List.of(1), g.getNeighbours(1), "Вершина должна быть своим соседом при петле");

        g.deleteEdge(1, 1);
        assertTrue(g.getNeighbours(1).isEmpty(), "Соседи должны быть пусты после удаления петли");
    }

    @ParameterizedTest
    @MethodSource("graphProvider")
    void testDeleteVertexRemovesEdges(Graph g) {
        g.addEdge(1, 2);
        g.addEdge(2, 3);

        g.deleteVertex(2);

        assertFalse(g.containsVertex(2));
        assertTrue(g.getNeighbours(1).isEmpty(), "Исходящие ребра в удаленную вершину должны исчезнуть");
    }

    @ParameterizedTest
    @MethodSource("graphProvider")
    void testNotFoundExceptions(Graph g) {
        assertThrows(NotFoundVertexException.class, () -> g.deleteVertex(999));
        assertThrows(NotFoundVertexException.class, () -> g.getNeighbours(999));

        g.addVertex(1);
        assertThrows(NotFoundVertexException.class, () -> g.deleteEdge(1, 999));
        assertThrows(NotFoundVertexException.class, () -> g.deleteEdge(999, 1));
    }

    @ParameterizedTest
    @MethodSource("graphProvider")
    void testUnmodifiableVertices(Graph g) {
        g.addVertex(1);
        assertThrows(UnsupportedOperationException.class, () -> g.getVertices().add(2));
        assertThrows(UnsupportedOperationException.class, () -> g.getVertices().clear());
    }

    @ParameterizedTest
    @MethodSource("graphProvider")
    void testTopSortValid(Graph g) {
        // 1 -> 2 -> 3
        // 1 -> 4
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(1, 4);

        List<Integer> order = ((AbstractGraph) g).topSort();

        assertEquals(4, order.size());
        assertTrue(order.indexOf(1) < order.indexOf(2));
        assertTrue(order.indexOf(2) < order.indexOf(3));
        assertTrue(order.indexOf(1) < order.indexOf(4));
    }

    @ParameterizedTest
    @MethodSource("graphProvider")
    void testTopSortWithCycle(Graph g) {
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(3, 1); // Цикл 1-2-3-1

        assertThrows(GraphHasCycleException.class, () -> ((AbstractGraph) g).topSort());
    }

    @Test
    void testEqualsAndHashCode() {
        Graph g1 = new GraphAdjList();
        Graph g2 = new GraphAdjMatrix();
        Graph g3 = new GraphIncidenceMatrix();

        // Наполняем одинаковыми данными
        for (Graph g : List.of(g1, g2, g3)) {
            g.addEdge(1, 2);
            g.addEdge(1, 3);
            g.addVertex(4); // Изолированная вершина
        }

        // Проверяем транзитивность и симметричность equals для разных реализаций
        assertEquals(g1, g2);
        assertEquals(g2, g3);
        assertEquals(g1, g3);

        // Хэш-коды также должны совпадать
        assertEquals(g1.hashCode(), g2.hashCode());
        assertEquals(g2.hashCode(), g3.hashCode());
    }

    @Test
    void testLoadFromFile(@TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("graph.txt");
        Files.writeString(file, "1 2\n2 3\n1 3");

        AbstractGraph g = new GraphAdjList();
        g.loadFromFile(file.toString());

        assertTrue(g.containsVertex(1) && g.containsVertex(2) && g.containsVertex(3));
        assertEquals(2, g.getNeighbours(1).size());
    }

    @Test
    void testLoadFromFileInvalidFormat(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("bad_graph.txt");
        Files.writeString(file, "1 2 3\n"); // Лишнее число

        AbstractGraph g = new GraphAdjList();
        assertThrows(GraphFileFormatException.class, () -> g.loadFromFile(file.toString()));
    }

    @ParameterizedTest
    @MethodSource("graphProvider")
    void testFreeListVerticesReuse(Graph g) {
        for (int i = 0; i < 100; i++) {
            g.addVertex(i);
        }
        assertEquals(100, g.getVertices().size());

        for (int i = 0; i < 100; i += 2) {
            g.deleteVertex(i);
        }
        assertEquals(50, g.getVertices().size());

        for (int i = 1000; i < 1050; i++) {
            g.addVertex(i);
        }
        assertEquals(100, g.getVertices().size());

        g.addEdge(1000, 1);
        g.addEdge(1, 1001);
        assertEquals(List.of(1), g.getNeighbours(1000));
        assertEquals(List.of(1001), g.getNeighbours(1));
    }

    @ParameterizedTest
    @MethodSource("graphProvider")
    void testFreeListEdgesReuse(Graph g) {
        g.addVertex(1);
        g.addVertex(2);

        for (int i = 10; i < 110; i++) {
            g.addEdge(1, i);
        }
        for (int i = 10; i < 110; i++) {
            g.deleteEdge(1, i);
        }

        g.addEdge(1, 2);
        assertEquals(List.of(2), g.getNeighbours(1));

        g.deleteVertex(2);
        assertTrue(g.getNeighbours(1).isEmpty());
        
        g.addEdge(1, 3);
        assertEquals(List.of(3), g.getNeighbours(1));
    }
}
