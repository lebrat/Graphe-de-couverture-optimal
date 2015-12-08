import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Kruskal {
	private List<Edge> edges;
    private int numberOfVertices;
    public static final double MAX_VALUE = 9999.0;
    private int visited[];
    private double spanning_tree[][];
 
    public Kruskal(int numberOfVertices)
    {
        this.numberOfVertices = numberOfVertices;
        edges = new LinkedList<Edge>();
        visited = new int[this.numberOfVertices + 1];
        spanning_tree = new double[numberOfVertices + 1][numberOfVertices + 1];
    }
 
    public double[][] kruskalAlgorithm(double[][] sous_connectivite)
    {	
    	
        boolean finished = false;
        for (int source = 1; source <= numberOfVertices ; source++)
        {
            for (int destination = 1; destination <= numberOfVertices ; destination++)
            {
                if (sous_connectivite[source][destination] != MAX_VALUE && source != destination)
                {
                    Edge edge = new Edge();
                    edge.sourcevertex = source;
                    edge.destinationvertex = destination;
                    edge.weight = sous_connectivite[source][destination];
                    sous_connectivite[destination][source] = MAX_VALUE;
                    edges.add(edge);
                }
            }
        }
        
        Collections.sort(edges, new EdgeComparator());
        int compteArcs = 1;
    
        for (Edge edge : edges)
        {
        	if(compteArcs != numberOfVertices){ 
        	spanning_tree[edge.sourcevertex][edge.destinationvertex] = edge.weight;
            spanning_tree[edge.destinationvertex][edge.sourcevertex] = edge.weight;
            int [][] connectivity = new int[numberOfVertices][numberOfVertices];
            for (int i = 1; i < numberOfVertices+1; i++) {
            	for (int j=1; j < numberOfVertices+1; j++){
            			if (spanning_tree[i][j] != 0.0)
            				connectivity[i-1][j-1] = 1;
            	}
            }            
            //double[][] connectivity =  {{0,1,0,1},{1,0,1,0},{0,1,0,1},{1,0,1,0}};
            EulerCircuit circuit = new EulerCircuit(numberOfVertices, connectivity);           

        	//DecimalFormat numberFormat = new DecimalFormat("#.00");
        	//System.out.println("print spanning tree");
            //for (int i = 1; i <= numberOfVertices; i++)
            //    System.out.print("\t" + i);
            //System.out.println();
            //for (int source = 1; source <= numberOfVertices; source++)
            //{
            //    System.out.print(source + "\t");
            //    for (int destination = 1; destination <= numberOfVertices; destination++)
            //    {
            //        System.out.print(numberFormat.format(spanning_tree[source][destination]) + "\t");
            //    }
            //    System.out.println();
            //}
            //System.out.println("source");
            //System.out.println(edge.sourcevertex);
            //System.out.println(checkCycle.checkCycle(spanning_tree, edge.sourcevertex));            
                        
            if (circuit.getEulerTour())
            {
            	//System.out.println(edge.sourcevertex);
            	//System.out.println(edge.destinationvertex);
                spanning_tree[edge.sourcevertex][edge.destinationvertex] = 0;
                spanning_tree[edge.destinationvertex][edge.sourcevertex] = 0;
                edge.weight = -1;
                continue;
            }
            else{
            	compteArcs ++;
            }
            
            visited[edge.sourcevertex] = 1;
            visited[edge.destinationvertex] = 1;
            for (int i = 0; i < visited.length; i++)
            {
                if (visited[i] == 0)
                {
                    finished = false;
                    break;
                } else
                {
                    finished = true;
                }
            }
            if (finished)
                break;
        }
        }
        DecimalFormat numberFormat = new DecimalFormat("#.00");
//        System.out.println("The spanning tree is ");
        for (int i = 1; i <= numberOfVertices; i++)
            System.out.print("\t" + i);
        System.out.println();
        for (int source = 1; source <= numberOfVertices; source++)
        {
            System.out.print(source + "\t");
            for (int destination = 1; destination <= numberOfVertices; destination++)
            {
                System.out.print(numberFormat.format(spanning_tree[source][destination]) + "\t");
            }
            System.out.println();
        }
        double [][] MST = new double[numberOfVertices][numberOfVertices];
        for (int source = 1; source <= numberOfVertices; source++)
        {
            for (int destination = 1; destination <= numberOfVertices; destination++)
            {
                MST[source-1][destination-1] = spanning_tree[source][destination];
            }
        }
        return MST;
    }
    
}
 
class Edge
{
    int sourcevertex;
    int destinationvertex;
    double weight;
}
 
class EdgeComparator implements Comparator<Edge>
{
    @Override
    public int compare(Edge edge1, Edge edge2)
    {
        if (edge1.weight < edge2.weight)
            return -1;
        if (edge1.weight > edge2.weight)
            return 1;
        return 0;
    }
}
//useless now cause we use Eulerian circuit detection
//class CheckCycle
//{
//    private Stack<Integer> stack;
//    private double adjacencyMatrix[][];
// 
//    public CheckCycle()
//    {
//        stack = new Stack<Integer>();
//    }
// 
//    public boolean checkCycle(double[][] spanning_tree, int source)
//    {
//        boolean cyclepresent = false;
//        int number_of_nodes = spanning_tree[source].length - 1;
// 
//        adjacencyMatrix = new double[number_of_nodes + 1][number_of_nodes + 1];
//        for (int sourcevertex = 1; sourcevertex <= number_of_nodes; sourcevertex++)
//        {
//            for (int destinationvertex = 1; destinationvertex <= number_of_nodes; destinationvertex++)
//            {
//                adjacencyMatrix[sourcevertex][destinationvertex] = spanning_tree[sourcevertex][destinationvertex];
//            }
//         }
// 
//         int visited[] = new int[number_of_nodes + 1];
//         int element = source;
//         int i = source;
//         visited[source] = 1;
//         stack.push(source);
// 
//         while (!stack.isEmpty())
//         {
//             element = stack.peek();         
//             i = element;
//             while (i <= number_of_nodes)
//             {
//                 if (adjacencyMatrix[element][i] >= 1 && visited[i] == 1)
//                 {
//                     if (stack.contains(i))
//                     {
//                         cyclepresent = true;
//                         return cyclepresent;
//                     }
//                 }
//                 if (adjacencyMatrix[element][i] >= 1 && visited[i] == 0)
//                 {
//                     stack.push(i);
//                     visited[i] = 1;
//                     adjacencyMatrix[element][i] = 0;// mark as labelled;
//                     adjacencyMatrix[i][element] = 0;
//                     element = i;
//                     i = 1;
//                     continue;
//                  }
//                  i++;
//             }
//             stack.pop();
//        }
//        return cyclepresent;
//    }
//
//}
