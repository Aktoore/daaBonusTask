# daaBonusTask
This program demonstrates how to handle the removal of an edge from a Minimum Spanning Tree (MST) and efficiently find a replacement edge that reconnects the graph while keeping it minimal.
It is based on Kruskal’s algorithm and shows all major steps — from building the MST to verifying the new tree after reconnection.

Features

The program performs the following:
Builds the MST from a given graph using Kruskal’s algorithm.
Displays the MST edges and total weight.
Removes one edge from the MST.
Detects and prints the resulting connected components.
Finds a replacement edge to reconnect the graph.
Displays and verifies the new MST, including weight comparison.

How to Run
Requirements

Java 8 or higher

Git (optional, for cloning the repository)

Run from Command Line
Clone the repository
git clone <repository-url>
cd <project-folder>

Compile
javac MSTEdgeRemoval.java

Run
java MSTEdgeRemoval

Run from an IDE

Open the project in IntelliJ IDEA, Eclipse, or VS Code.
Add MSTEdgeRemoval.java and run the main() method directly.

    ----Construction of Minimum Spanning Tree----   

MST vertices:

Total weight: 11

   ----Deleting Edge----  
   
Edge Deleted: (3 - 4, weight: 2)

   ----Connected Components---- 
   
Components: 2

Component 1: [0, 1, 2, 3]

Component 2: [4, 5]

   ----Deleting Edge----  
   
Replacement edge found: (3 - 5, weight: 6)

New MST

Edges in the new MST:

    (1 - 2, weight: 1)
    
    (1 - 3, weight: 2)
    
    (0 - 2, weight: 3)
    
    (4 - 5, weight: 3)
    
    (3 - 5, weight: 6)
    
Total weight: 15

   ----Verification----  
   
Original MST weight:11

Removed Edge: (3 - 4, weight: 2) (weight: 2)

Added Edge: (3 - 5, weight: 6) (weight: 6)

New MST weight: 15

Weight difference: +4


This output shows that the program correctly detects disconnected components after removing an edge and finds a valid replacement edge to reconnect the tree.
The new MST has a total weight of 15, which is 4 units higher than the original — exactly matching the expected difference.

Main Classes
Edge – represents a graph edge with fields from, to, and weight. Implements Comparable for sorting by weight.
UnionFind – disjoint set union (DSU) data structure used for cycle detection and merging sets in Kruskal’s algorithm.

Algorithm Overview

Build MST (Kruskal’s Algorithm) – sort all edges by weight and use Union-Find to prevent cycles.
Remove an Edge – delete one MST edge to split the tree into two components.
Detect Components – perform DFS to find which vertices belong to each component.
Find Replacement Edge – select the smallest edge (not in MST) that reconnects both components.
Rebuild and Verify – create a new MST, calculate total weight, and show the weight difference.

Complexity

Time Complexity: O(E log E)
Building MST: O(E log E)
Finding components: O(V + E)
Finding replacement edge: O(E)

Space Complexity: O(V + E)

Graph storage: O(E)
Union-Find structure: O(V)
DFS recursion stack: O(V)
