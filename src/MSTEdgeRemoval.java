import java.util.*;

public class MSTEdgeRemoval {
    static class Edge implements Comparable<Edge> {
        int from, to, weight;

        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return this.weight - other.weight;
        }
        @Override
        public String toString() {
            return String.format("(%d - %d, weight: %d)", from, to, weight);
        }
    }
    static class UnionFind {
        int[] parent, rank;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }
        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
        boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX == rootY) return false;

            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootX] = rootY;
                rank[rootY]++;
            }
            return true;
        }
    }
    static List<Edge> buildMST(List<Edge> edges, int vertices) {
        Collections.sort(edges);
        UnionFind uf = new UnionFind(vertices);
        List<Edge> mst = new ArrayList<>();

        for (Edge edge : edges) {
            if (uf.union(edge.from, edge.to)) {
                mst.add(edge);
                if (mst.size() == vertices - 1) break;
            }
        }
        return mst;
    }
    static List<Set<Integer>> findComponents(int vertices, List<Edge> mstEdges) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            graph.add(new ArrayList<>());
        }
        for (Edge edge : mstEdges) {
            graph.get(edge.from).add(edge.to);
            graph.get(edge.to).add(edge.from);
        }

        boolean[] visited = new boolean[vertices];
        List<Set<Integer>> components = new ArrayList<>();

        for(int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                Set<Integer> component = new HashSet<>();
                dfs(i,graph, visited, component);
                components.add(component);
            }
        }
        return components;
    }
    static void dfs(int node, List<List<Integer>> graph, boolean[] visited, Set<Integer> component) {
        visited[node] = true;
        component.add(node);

        for(int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                dfs(neighbor, graph, visited, component);
            }
        }
    }
    static Edge findReplacementEdge(List<Edge> allEdges, List<Edge> mstEdges, Set<Integer> comp1, Set<Integer> comp2) {
        Edge replacement = null;

        for (Edge edge : allEdges) {
            boolean inMST = mstEdges.stream().anyMatch(e -> (e.from == edge.from && e.to == edge.to) ||
                    (e.from == edge.to && e.to == edge.from));

            if (!inMST) {
                boolean connects = (comp1.contains(edge.from) && comp2.contains(edge.to)) ||
                        (comp1.contains(edge.to) && comp2.contains(edge.from));

                if (connects) {
                    if (replacement == null || edge.weight < replacement.weight) {
                        replacement = edge;
                    }
                }
            }
        }
        return replacement;
    }
    public static void main(String[] args) {
        int vertices = 6;
        List<Edge> allEdges = new ArrayList<>();

        // Building MST
        allEdges.add(new Edge(0, 1, 4));
        allEdges.add(new Edge(0, 2, 3));
        allEdges.add(new Edge(1, 2, 1));
        allEdges.add(new Edge(1, 3, 2));
        allEdges.add(new Edge(2, 3, 4));
        allEdges.add(new Edge(3, 4, 2));
        allEdges.add(new Edge(3, 5, 6));
        allEdges.add(new Edge(4, 5, 3));

        System.out.println("    ----Construction of Minimum Spanning Tree----   ");

        List<Edge> mst = buildMST(allEdges, vertices);

        System.out.println("MST vertices:");
        int totalWeight = 0;
        for (Edge edge : mst) {
            totalWeight += edge.weight;

        }
        System.out.println("Total weight: " + totalWeight);

        // Remove 1 Edge from MST
        System.out.println("   ----Deleting Edge----  ");
        Edge removedEdge = mst.get(2);
        List<Edge> mstAfterRemoval = new ArrayList<>(mst);
        mstAfterRemoval.remove(removedEdge);

        System.out.println("Edge Deleted: " + removedEdge);

        // Find connected components
        System.out.println("   ----Connected Components----  ");
        List<Set<Integer>> components = findComponents(vertices, mstAfterRemoval);

        System.out.println("Components: " + components.size());
        for (int i = 0; i < components.size(); i++) {
            System.out.println("Component " + (i + 1) + ": " + components.get(i));
        }

        //Find replacements edge
        System.out.println("   ----Deleting Edge----  ");

        if (components.size() == 2) {
            Edge replacement = findReplacementEdge(allEdges, mst, components.get(0), components.get(1));

            if (replacement != null) {
                System.out.println("Replacement edge found: " + replacement);

                List<Edge> newMST = new ArrayList<>(mstAfterRemoval);
                newMST.remove(replacement);

                System.out.println("New MST");
                System.out.println("Edges in the new MST:");
                int newTotalWeight = 0;
                for (Edge edge : newMST) {
                    System.out.println("    " + edge);
                    newTotalWeight += edge.weight;
                }
                System.out.println("Total weight: " + newTotalWeight);
            } else {
                System.out.println("No replacement found");
            }
        } else {
            System.out.println("Error: got" + components.size() + " components instead of 2");
        }
    }
}
