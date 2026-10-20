package graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.List;
import java.util.Set;

/**
 * Базовый абстрактный класс для графов, содержащий общую логику
 * equals, hashCode, loadFromFile и toString.
 */
public abstract class AbstractGraph implements Graph {

    /**
     * Создает ориентированный граф по информации из файла. Принимает путь к текстовому файлу.
     * Каждая строка файла должна иметь вид: from to, где (from, to) - ребро в графе
     *
     * @param path путь к файлу
     * @throws FileNotFoundException    если путь к файлу невалидный
     * @throws GraphFileFormatException если нарушен формат файла
     */
    public void loadFromFile(String path)
        throws FileNotFoundException, GraphFileFormatException {
        File file = new File(path);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+");
                if (parts.length != 2 || !parts[0].matches("\\d+") || !parts[1].matches("\\d+")) {
                    throw new GraphFileFormatException("Формат файла нарушен!");
                }
                int v = Integer.parseInt(parts[0]);
                int u = Integer.parseInt(parts[1]);
                addVertex(v);
                addVertex(u);
                addEdge(v, u);
            }
        }
    }

    /**
     * Производит топологическую сортировку ориентированного графа.
     *
     *
     * @return Упорядоченный набор вершин, полученный с помощью сортировки
     * @throws GraphHasCycleException если в графе есть цикл
     */
    public List<Integer> topSort(){
        List<Integer> result = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        Set<Integer> inStack = new HashSet<>();

        class DfsClass {
            void dfs(int v) throws GraphHasCycleException {
                visited.add(v);
                inStack.add(v);

                for (int neighbour : getNeighbours(v)) {
                    if (inStack.contains(neighbour)) {
                        throw new GraphHasCycleException("У графа есть цикл");
                    }
                    if (!visited.contains(neighbour)) {
                        dfs(neighbour);
                    }
                }
                inStack.remove(v);
                result.add(v);
            }
        }

        DfsClass dfsHelper = new DfsClass();
        for (int v : getVertices()) {
            if (!visited.contains(v)) {
                dfsHelper.dfs(v);
            }
        }
        Collections.reverse(result);
        return result;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Graph other)) {
            return false;
        }
        if (!this.getVertices().equals(other.getVertices())) {
            return false;
        }
        try {
            for (int v : this.getVertices()) {
                Set<Integer> set1 = new HashSet<>(this.getNeighbours(v));
                Set<Integer> set2 = new HashSet<>(other.getNeighbours(v));
                if (!set1.equals(set2)) {
                    return false;
                }
            }

        } catch (NotFoundVertexException e) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int totalHash = 0;
        try {
            for (int v : getVertices()) {
                Set<Integer> neighbours = new HashSet<>(getNeighbours(v));
                int vertexHash = 31 * Integer.hashCode(v) + neighbours.hashCode();
                totalHash += vertexHash;
            }
        } catch (NotFoundVertexException ignored) {
            // Исключение не случится, так как берём вершины из getVertices()
        }
        return totalHash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append(" {\n");
        try {
            for (int v : getVertices()) {
                sb.append("  ").append(v).append(" -> ").append(getNeighbours(v)).append("\n");
            }
        } catch (NotFoundVertexException e) {
            sb.append("  Error reading neighbors");
        }
        sb.append("}");
        return sb.toString();
    }
}
