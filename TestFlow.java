import maxflow.MaxFlow;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by cx on 2017-03-21.
 */
public class TestFlow {

    public static void main(String[] args) {
        int maxFlow = 0;
        File f = new File("rail.txt");
        System.out.println(f.canRead());
        try {
            MaxFlow maxflow = new MaxFlow(f);
            maxFlow = maxflow.maxFlow();
            int t = maxflow.maxFlowDFS();
            System.out.println("Max flow is: " + maxFlow + " " + t);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
