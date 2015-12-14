import java.text.DecimalFormat;
import java.util.Stack;

public class EulerCircuit {
	 private int[][] adjacencyMatrix;
	    private int numberOfNodes;
	    private Stack<Integer> stack;
	    private static boolean found = false;
	 
	    public EulerCircuit (int numberOfNodes, int[][] adjacencyMatrix)
	    {	    
	    	found = false;
	        this.numberOfNodes = numberOfNodes;
	        this.adjacencyMatrix = new int[numberOfNodes + 1] [numberOfNodes + 1];
	        for (int sourceVertex = 1; sourceVertex <= numberOfNodes; sourceVertex++)
	        {
	        	
	            for (int destinationVertex = 1; destinationVertex <= numberOfNodes; destinationVertex++)
	            {	            
	                this.adjacencyMatrix[sourceVertex][destinationVertex]
	                   = adjacencyMatrix[sourceVertex-1][destinationVertex-1];
	            }
	        }
//	        DecimalFormat numberFormat = new DecimalFormat("#.00");
//	        for (int i = 1; i <= numberOfNodes; i++)
//                System.out.print("\t" + i);
//            System.out.println();
//            for (int source = 1; source <= numberOfNodes; source++)
//            {
//                System.out.print(source + "\t");
//                for (int destination = 1; destination <= numberOfNodes; destination++)
//                {
//                    System.out.print(numberFormat.format(this.adjacencyMatrix[source][destination]) + "\t");
//                }
//                System.out.println();
//            }
	    }
	 
	    public int degree (int vertex)
	    {
	        int degree = 0;
	        for (int destinationvertex = 1; destinationvertex <= numberOfNodes ; destinationvertex++)
	        {
	            if (adjacencyMatrix[vertex][destinationvertex] == 1 
	                   || adjacencyMatrix[destinationvertex][vertex] == 1)
	            {
	                degree++;
	            }
	        }
	        return degree;
	    }
	 
	    public int oddDegreeVertex ()
	    {
	        int vertex = -1;
	        for (int node = 1; node <= numberOfNodes; node++) 
	        {
	            if ((degree(node) % 2) != 0)
	            {
	                vertex = node;
	                break;
	            }
	        }
	        return vertex;
	    }
	 
	    public void getEulerTourUtil (int vertex)
	    {
	        for (int destination = 1; destination <= numberOfNodes; destination++)
	        {
	            if(adjacencyMatrix[vertex][destination] == 1 && isVaildNextEdge(vertex, destination))
	            {
	                //System.out.println(" source : " + vertex + " destination " + destination);	                
	                if (!stack.contains(destination)){
	                	stack.push(destination);
	                }
	                else{
	                	found = true;
	                }	                	
	                
	                removeEdge(vertex, destination);
	                getEulerTourUtil(destination);
	            }	
	        }
	    }
	 
	    public boolean getEulerTour ()
	    {
	        int vertex = 1;
	        if (oddDegreeVertex() != -1)
	        {
	            vertex = oddDegreeVertex();
	        }
	        found = false;
	        stack = new Stack<Integer>();
	        stack.push(vertex);
	        getEulerTourUtil(vertex);
	        //System.out.println(found);
	        //System.out.println(" ");	       
	        return found;
	    }
	 
	    public boolean isVaildNextEdge (int source, int destination)
	    {
	        int count = 0;
	        for (int vertex = 1; vertex <= numberOfNodes; vertex++)
	        {
	            if (adjacencyMatrix[source][vertex] == 1)
	            {
	                count++;
	            }
	        }
	 
	        if (count == 1 )
	        {   
	            return true;
	        }
	 
	        int visited[] = new int[numberOfNodes + 1];		
	        int count1 = DFSCount(source, visited);
	 
	        removeEdge(source, destination);
	        for (int vertex = 1; vertex <= numberOfNodes; vertex++)
	        {
	            visited[vertex] = 0;
	        }
	 
	       int count2 = DFSCount(source, visited);
	       addEdge(source, destination);
	 
	       return (count1 > count2 ) ? false : true;
	    } 
	 
	    public int DFSCount (int source, int visited[])
	    {
	        visited[source] = 1;
	        int count = 1;
	        int destination = 1;
	 
	        while (destination <= numberOfNodes)
	        {
	            if(adjacencyMatrix[source][destination] == 1 && visited[destination] == 0)
	            {
	                count += DFSCount(destination, visited);
	            }
	            destination++;
	        }
	        return count;
	    }
	 
	    public void removeEdge (int source, int destination)
	    {
	        adjacencyMatrix[source][destination]  = 0;
	        adjacencyMatrix[destination][source] = 0;
	    }
	 
	    public void addEdge (int source, int destination)
	    {
	        adjacencyMatrix[source][destination] = 1;
	        adjacencyMatrix[destination][source] = 1;
	    }
	    
	    public static void main (String... arg)
	    {
	        int number_of_nodes;
            number_of_nodes = 4;
 
            // read the adjacency matrix
            //int[][] adjacency_matrix =  {{0,0,0,1},{0,0,0,1},{0,0,0,1},{1,1,1,0}};
            //int[][] adjacency_matrix =  {{0,1,0,1},{1,0,1,0},{0,1,0,1},{1,0,1,0}};
            int[][] adjacency_matrix =  {{0,0,1,1},{0,0,0,0},{1,0,0,1},{1,0,1,0}};
            System.out.println("the euler path or euler circuit is ");
            // print euler path or circuit
            EulerCircuit circuit = new EulerCircuit(number_of_nodes, adjacency_matrix);
            boolean a = circuit.getEulerTour();
            System.out.println(a);
 	    }
}