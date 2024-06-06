import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Program to find the path with the minimal weight in a weighted graph.
 */
public class MinimalWeightPathFinder {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the path of the graph file: ");
        String filename = scanner.nextLine();

        List<Edge> edges;
        try {
            edges = readGraphFromFile(filename);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
            return;
        }

        System.out.print("Enter start node: ");
        String startNode = scanner.nextLine().trim();
        System.out.print("Enter end node: ");
        String endNode = scanner.nextLine().trim();
        scanner.close();

        Map<String, Object> result = findMinimalWeightPath(edges, startNode, endNode);
        double weight = (double) result.get("weight");
        List<String> path = (List<String>) result.get("path");

        if (weight == Double.MAX_VALUE) {
            System.out.println("No path found.");
        } else {
            System.out.println("Minimal weight path:");
            System.out.println("Total weight: " + weight);
            System.out.println("Path: " + String.join(" -> ", path));
        }
    }

    /**
     * Reads the graph from a file.
     *
     * @param filename The path of the file containing the graph.
     * @return The graph represented as a list of edges.
     * @throws FileNotFoundException If the file is not found.
     */
    static List<Edge> readGraphFromFile(String filename) throws FileNotFoundException {
        List<Edge> edges = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\s+");
                if (parts.length < 3) {
                    throw new IllegalArgumentException("Invalid line format");
                }
                String startNode = parts[0].replaceAll("\"", "");
                double weight = Double.parseDouble(parts[1]);
                String endNode = parts[2].replaceAll("\"", "");
                edges.add(new Edge(startNode, weight, endNode));
            }
        }
        return edges;
    }

    /**
     * Finds the path with minimal weight between two nodes.
     *
     * @param edges      The list of edges representing the graph.
     * @param startNode  The starting node.
     * @param endNode    The ending node.
     * @return The minimal weight and the path as a list of nodes.
     */
    static Map<String, Object> findMinimalWeightPath(List<Edge> edges, String startNode, String endNode) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Double> minWeights = new HashMap<>();
        Map<String, String> previousNodes = new HashMap<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingDouble(e -> e.weight));

        for (Edge edge : edges) {
            minWeights.put(edge.startNode, Double.MAX_VALUE);
            minWeights.put(edge.endNode, Double.MAX_VALUE);
        }

        minWeights.put(startNode, 0.0);
        pq.offer(new Edge(startNode, 0.0, startNode));

        while (!pq.isEmpty()) {
            Edge currentEdge = pq.poll();
            String currentNode = currentEdge.endNode;
            double currentWeight = currentEdge.weight;

            if (currentWeight > minWeights.get(currentNode)) {
                continue;
            }

            for (Edge edge : edges) {
                if (edge.startNode.equals(currentNode)) {
                    double newWeight = currentWeight + edge.weight;
                    if (newWeight < minWeights.get(edge.endNode)) {
                        minWeights.put(edge.endNode, newWeight);
                        previousNodes.put(edge.endNode, currentNode);
                        pq.offer(new Edge(edge.startNode, newWeight, edge.endNode));
                    }
                }
            }
        }

        List<String> path = new ArrayList<>();
        String currentNode = endNode;
        while (!currentNode.equals(startNode)) {
            path.add(currentNode);
            currentNode = previousNodes.get(currentNode);
            if (currentNode == null) {
                // If currentNode is null, it means the start node is unreachable from the end node
                break;
            }
        }
        if (currentNode != null) {
            // If currentNode is not null, it means a path exists
            path.add(startNode);
            Collections.reverse(path);

            result.put("weight", minWeights.get(endNode));
            result.put("path", path);
        } else {
            // If currentNode is null, it means no path exists
            result.put("weight", Double.MAX_VALUE);
            result.put("path", Collections.emptyList());
        }
        return result;
    }

    /**
     * Represents an edge in the graph.
     */
    static class Edge {
        String startNode;
        double weight;
        String endNode;

        Edge(String startNode, double weight, String endNode) {
            this.startNode = startNode;
            this.weight = weight;
            this.endNode = endNode;
        }
    }

}

