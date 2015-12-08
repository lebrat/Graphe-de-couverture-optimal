import java.text.DecimalFormat;
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
        this.numberOfVertices = numberOfVertices;  										//Nombre de sommets concernés
        edges = new LinkedList<Edge>();     											//Liste des arcs
        visited = new int[this.numberOfVertices + 1];									//Marque si le sommet est visité ou non
        spanning_tree = new double[numberOfVertices + 1][numberOfVertices + 1];			//Spanning tree final
    }
 
    public void kruskalAlgorithm(double[][] sous_connectivite)
    {
        boolean finished = false;
        for (int source = 1; source <= numberOfVertices; source++)
        {
            for (int destination = 1; destination <= numberOfVertices; destination++)
            {
                if (sous_connectivite[source][destination] != MAX_VALUE && source != destination)
                {
                	// Construction de tous les arcs à partir de la sous-connectivité
                    Edge edge = new Edge();
                    edge.sourcevertex = source;
                    edge.destinationvertex = destination;
                    edge.weight = sous_connectivite[source][destination];
                    sous_connectivite[destination][source] = MAX_VALUE;
                    edges.add(edge);
                }
            }
        }
        

        // On classe les arcs par longueur croissante
        Collections.sort(edges, new EdgeComparator());

        // Utilise la propriété n-1 arcs pour le spanning tree pour l'arret 
        int compteArcs = 1;
        
        CheckCycle checkCycle = new CheckCycle();
        
        // Cherche si on peut ajouter des arcs ou non
        for (Edge edge : edges)
        {
        
        if (compteArcs!=numberOfVertices) {
            spanning_tree[edge.sourcevertex][edge.destinationvertex] = edge.weight;
            spanning_tree[edge.destinationvertex][edge.sourcevertex] = edge.weight;
   	     
         
           
            if (checkCycle.checkCycle(spanning_tree, edge.sourcevertex)){
            	spanning_tree[edge.sourcevertex][edge.destinationvertex] = 0;
                spanning_tree[edge.destinationvertex][edge.sourcevertex] = 0;
                edge.weight = -1;
                continue;
             }
            else
            	compteArcs = compteArcs +1;
            
            visited[edge.sourcevertex] = 1;
            visited[edge.destinationvertex] = 1;
            for (int i = 0; i < visited.length; i++)
            {
                if (visited[i] == 1)
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
        System.out.println("The spanning tree is ");
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
        
        
    }

    
    public void afficheliste(List<Edge> edges) {
        for (int i = 0; i<=edges.size()-1; i++){
        	System.out.print("Arc " + i +" ");
        	afficheelement(edges.get(i));
        }
     }
    
    public void afficheelement(Edge edge){
    	System.out.print(edge.sourcevertex + "-");
    	System.out.print(edge.destinationvertex + " ");
    	System.out.print(edge.weight + " ");
    	System.out.print("\n");
    }
    
    public static int somme_vecteur(int[] vecteur){
    	int somme = 0;
    	for (int i=0; i<vecteur.length; i++)
    		somme = somme + vecteur[i];
    	return somme;
    }
    
 
    public static void main(String... arg)
    {
        double adjacency_matrix[][];
        int number_of_vertices;
 
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the number of vertices");
        number_of_vertices = scan.nextInt();
        adjacency_matrix = new double[number_of_vertices + 1][number_of_vertices + 1];
 
        System.out.println("Enter the Weighted Matrix for the graph");
        for (int i = 1; i <= number_of_vertices; i++)
        {
            for (int j = 1; j <= number_of_vertices; j++)
            {
                adjacency_matrix[i][j] = scan.nextInt();
                if (i == j)
                {
                    adjacency_matrix[i][j] = 0;
                    continue;
                }
                if (adjacency_matrix[i][j] == 0)
                {
                    adjacency_matrix[i][j] = MAX_VALUE;
                }
            }
        }
        
        scan.close();
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
 
class CheckCycle
{
    private Stack<Integer> stack;
    private double adjacencyMatrix[][];
 
    public CheckCycle()
    {
        stack = new Stack<Integer>();
    }
 
    public boolean checkCycle(double[][] spanning_tree, int source)
    {
        boolean cyclepresent = false;
        int number_of_nodes = spanning_tree[source].length - 1;
 
        adjacencyMatrix = new double[number_of_nodes + 1][number_of_nodes + 1];
        for (int sourcevertex = 1; sourcevertex <= number_of_nodes; sourcevertex++)
        {
            for (int destinationvertex = 1; destinationvertex <= number_of_nodes; destinationvertex++)
            {
                adjacencyMatrix[sourcevertex][destinationvertex] = spanning_tree[sourcevertex][destinationvertex];
            }
         }
 
         int visited[] = new int[number_of_nodes + 1];
         int element = source;
         int i = source;
         visited[source] = 1;
         stack.push(source);
 
         while (!stack.isEmpty())
         {
             element = stack.peek();
             i = element;
             while (i <= number_of_nodes)
             {
                 if (adjacencyMatrix[element][i] >= 0.1 && visited[i] == 1)
                 {
                     if (stack.contains(i))
                     {
                         cyclepresent = true;
                         if(cyclepresent)
                         	System.out.print("cycle trouvé");
                         else
                         	System.out.print("pas de cycle");
                         System.out.println();
                         return cyclepresent;
                     }
                 }
                 if (adjacencyMatrix[element][i] >= 0.1 && visited[i] == 0)
                 {
                     stack.push(i);
                     visited[i] = 1;
                     adjacencyMatrix[element][i] = 0;// mark as labelled;
                     adjacencyMatrix[i][element] = 0;
                     element = i;
                     i = 1;
                     continue;
                  }
                  i++;
             }
             stack.pop();
        }
//         if(cyclepresent)
//        	System.out.print("cycle trouvé");
//         else
//         	System.out.print("pas de cycle");
//         System.out.println();
        return cyclepresent;
       }   
    
}
