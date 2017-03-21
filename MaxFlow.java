package maxflow;

import java.io.*;
import java.util.*;

/**
 * Created by cx on 2017-03-20.
 */
public class MaxFlow {
    int[][] graph; // graph[i][j] === Integer(flow(i,j))
    int nodeCount;
    int edgeCount;
    boolean visitedVertices[];
    // kapacitet för residualgrafen: K_f(u,v) = kapaciteten i Edge av flödesgrafen, minus flödet som går genom den edge
    // k_res(u,v) = k(u,v) - f(u,v)

    public MaxFlow(File f) throws FileNotFoundException {
        try(Scanner scan = new Scanner(f)) {
            nodeCount = scan.nextInt(); // n
            edgeCount = scan.nextInt(); // m
            graph = new int[nodeCount][nodeCount];
            for(int[] v : graph) Arrays.fill(v,0); // make sure it's all 0's
            while(scan.hasNextLine()) {
                int val;
                graph[scan.nextInt()][scan.nextInt()] = ((val = scan.nextInt()) == -1) ? Integer.MAX_VALUE : val;
            }
            visitedVertices = new boolean[nodeCount];
            Arrays.fill(visitedVertices, false);
        }
        catch(FileNotFoundException e) {
            System.out.print(e.getMessage());
            System.exit(1);
        }
    }

    public int maxFlow()
    {
        int maxflow = 0;
        int flow = 0;
        // create residual flow network, initialize to the same values as the graph
        // done here with <code>makeResidual()</code> for easier viewing
        int[][] residualGraph = new int[graph.length][graph.length];
        for(int i = 0; i < graph.length; i++){
            for(int j = 0; j < graph.length; j++) {
                // if(graph[i][j] == -1) residualGraph[i][j] = Integer.MAX_VALUE;
                residualGraph[i][j] = graph[i][j];
            }
        }
            do maxflow += flow = BF(residualGraph);
            while(flow > 0);
        return maxflow;
    }

    public int maxFlowDFS() // depth first strategy
    {
        int maxflow = 0, flow = 0;
        // create residual flow network, initialize to the same values as the graph
        // done here with <code>makeResidual()</code> for easier viewing
        int[][] residualGraph = new int[graph.length][graph.length]; for(int v[] : residualGraph) Arrays.fill(v,0);
        // keep doing until flow is max, source -> sink
        do maxflow += flow = depthFirst(residualGraph);
        while(flow > 0);
        return maxflow;
    }

    private int depthFirst(int[][] residGraph) {
        return depthFirst(0, nodeCount-1, Integer.MAX_VALUE, residGraph);
    }

    private int depthFirst(int source, int sink, int minflow, int res[][]) {
        visitedVertices[source] = true;
        // base case, if we have reached terminal node sink
        if(source == sink) {
            System.out.println("min" + minflow);
            return minflow;
        }
        for(int i = 1; i < nodeCount; i++) {
            int remainingCapacity = (graph[source][i] == -1 ? Integer.MAX_VALUE : graph[source][i]) - res[source][i];
            if(!visitedVertices[i] && remainingCapacity > 0) {
                int currflow;
                //(int currflow = depthFirst(i, sink, Math.min(minflow, remainingCapacity), vis, res));
                if((currflow = depthFirst(i, sink, Math.min(minflow, remainingCapacity), res)) > 0) {
                    res[source][i] += currflow;
                    res[i][source] -= currflow;
                    return currflow;
                }
            }
        }
        return 0;
    }

    public int BF(int[][] residGraph) {
        // create a parent array
        int parents[] = new int[nodeCount]; Arrays.fill(parents, 0);
        visitedVertices[0] = true;
        Queue<Integer> nodePQ = new LinkedList<>();
        nodePQ.add(0);
        while(!nodePQ.isEmpty()) {
            // take u from queue, and begin visiting u
            int u = nodePQ.remove();
            for(int v = 0; v < nodeCount; v++) {
                if(!visitedVertices[v] &&  residGraph[u][v] > 0){
                    visitedVertices[v] = true;
                    nodePQ.add(v);
                    parents[v] = u;
                }
            }
        }
        if(parents[nodeCount-1] == -1) return 0; // we have no found a way from source to sink
        int flow = Integer.MAX_VALUE;
        for(int v = nodeCount-1; v != 0; v = parents[v]) { // parentList[i] holds the parent to i, parentsList[i-1] holds grand parent of i
            int u = parents[v];
            flow = Math.min(flow, residGraph[u][v]);
        }
        // init v (to), to the last node of graph, nodeCount - t, init u to the parent of v
        // for the parents of v,
        for(int v = nodeCount-1, u = parents[v]; u > 0; v = u, u = parents[v]) { // parentList[i] holds the parent to i, parentsList[i-1] holds grand parent
            residGraph[v][u] += flow; // flipped 'flow'
            residGraph[u][v] -= flow; // decrease cap
        }
        return flow;
    }
}
