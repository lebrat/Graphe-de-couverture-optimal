import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Random;
import grph.Grph;



public class Graphe {
	private static double inf = Double.POSITIVE_INFINITY;	
	public final static int ALPHA_NOTDEF = -999 ;
	
	public static int nb_sommet_int; //nb de sommets intermédiaires
	public static int nb_generateurs; //nb de generateurs
	public static int nb_clients; //nb de clients
	public static int nb_sommet; //nb total de sommets
	public static double [][] positions_sommet = new double[nb_sommet][2]; //positions des sommets 
	public static int [] sous_graphes; //permet de savoir quels sont les graphes generateurs-clients
	public static double [][] connectivite; //tableau symétrique des distances entre les points du graphes
	public static float densite; //densité de la connectivité
	
	//Graphe est la representation du reseau de fil installable dans le satelite
	//La table de connectivité Cij donne le coup de la connexion entre le somment i et j
	//zero signifie que la liaison est impossible



		public Graphe(int nb_s_int, int nb_gen, int nb_cli, int [] liens_sous_graphes, double [][] pos_som, float density){
		
		int nb_sources;	
	
		
		//On définit les nombres de sommets dans chaque catégories
		nb_sommet_int = nb_s_int;
		nb_generateurs = nb_gen;
		nb_clients = nb_cli;
		nb_sources = nb_clients + nb_generateurs;
		nb_sommet = nb_sommet_int + nb_sources;
		
				
		//On définit les positions de tous les sommets
		positions_sommet = pos_som;
		
		//Le booleen sources nous permet de savoir qui est une source ou pas
		sous_graphes = liens_sous_graphes;
		
				
		connectivite = new double[nb_sommet][nb_sommet];
		Random r = new Random();
				
		densite = density;
		
		//Première boucle qui remplit le carré haut-gauche de la connectivité de infinity
		//On décide de ne lier aucunes des sources ensemble
		
		for(int i = 0 ; i < nb_sources ; i++){
			
			for(int j = i; j < nb_sources; j++){
				connectivite[i][j] = inf;
				connectivite[j][i] = connectivite[i][j];
			}		
			
			for(int j = nb_sources; j < nb_sommet; j++){
				if(r.nextInt(100)<densite){
					connectivite[i][j] = distance(positions_sommet[i][0],positions_sommet[j][0],positions_sommet[i][1],positions_sommet[j][1]);
					connectivite[j][i] = connectivite[i][j];
				}
				else{
					connectivite[i][j] = inf;
					connectivite[j][i] = inf;
				}
			}		
		}
				

		//La densité nous permet de définir le pourcentage de connexions entre les sommets
		
	
		
		for(int i = nb_sources ; i < nb_sommet ; i++){
			for(int j = i; j < nb_sommet; j++){
				if(r.nextInt(100)<densite){
						connectivite[i][j] = distance(positions_sommet[i][0],positions_sommet[j][0],positions_sommet[i][1],positions_sommet[j][1]);
						connectivite[j][i] = connectivite[i][j];
					}
					else{
						connectivite[i][j] = inf;
						connectivite[j][i] = inf;
				}
			}		
		}
		
		
		
	}
	
		
	
	public static double distance(double x1, double x2, double y1, double y2){
		return (double) ( Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2)) );		
	}
	
	public static boolean existeArc(int sommet1, int sommet2){
		if(connectivite[sommet1][sommet2]!=inf)
			return true;
		else
			return false;
	}
	
	
	public static void print(){
		Grph g = new grph.in_memory.InMemoryGrph();
		g.addNVertices(nb_sommet);
		for(int i = 0; i < nb_sommet-1; i++){
			for(int j = i+1; j < nb_sommet; j++){
				if (connectivite[i][j] > 0){
					g.addUndirectedSimpleEdge(i, j);
				}
			}
		}
		g.display();
	}
	
	 public static double getDistance(int x0, int i) {
	        return connectivite[x0][i];
	    }
	 
	 
	 public static double costFunction(double [][] MST){
			double sum = 0.0;
			for (int i = 0; i < MST.length; i ++){
				for( int j=i+1;j < MST.length; j ++ ){
					sum += MST[i][j];
				}			
			}
			return sum;
		}
		
		public static void dotgraph(double[][] MST){
			PrintWriter writer = null;
			try {
				writer = new PrintWriter("graphe.dot", "UTF-8");
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			writer.println("graph G {");		
			for (int i = 0; i < MST.length; i ++){
				for( int j=i+1;j < MST.length; j ++ ){
					if( MST[i][j] != 0.0){
						writer.println(String.valueOf(i)+" -- "+String.valueOf(j)+";");
					}
				}			
			}
			writer.println("}");
			writer.close();
		}
		public static void doReduction (double [][] MST, int nbOfTerminalNode){
			//With our graph representation the terminal nodes are ordered thus they re occupied the index of our matrix
			boolean notReduced = true;
			int nbModification;
			int nnzRow;
			while(notReduced){
				nbModification = 0;						
				for(int i = nbOfTerminalNode; i < MST.length; i ++){
					nnzRow = 0;			
					for(int j = 0; j < MST.length; j ++){
						if(MST[i][j] != 0.0){
							nnzRow ++;
							
						}
					}
					if(nnzRow == 1){					
						// the row has to be deleted
						for(int j = 0; j < MST.length; j++){
							if(MST[i][j] != 0.0){
								nbModification ++;
								MST[i][j] = 0.0;
								MST[j][i] = 0.0;					
							}
						}
					}
				}
				if (nbModification == 0){
					//by now every node is terminal thus we stop improving the MST.
					notReduced = false;
				}
			}
			DecimalFormat numberFormat = new DecimalFormat("#.00");
//	      System.out.println("The spanning tree is ");
	      for (int source = 0; source < MST.length; source++)
	      {
	          System.out.print(source + "\t");
	          for (int destination = 0; destination < MST.length; destination++)
	          {
	              System.out.print(numberFormat.format(MST[source][destination]) + "\t");
	          }
	          System.out.println();
	      }
		}

		
	    public static void echangePositionsDansTableau (int indice1, int indice2){
	    	// Cette fonction nous permet d'échanger deux éléments dans le tableau de connectivité, de manière à pouvoir appliquer le Dijkstra avec le barycentre
	    	double tampon;
	    	for (int i=0; i<nb_sommet; i++){
	    		if (i != indice1 && i!= indice2){
	    		tampon = connectivite[i][indice1];
	    		connectivite[i][indice1] = connectivite[i][indice2];
	    		connectivite[i][indice2] = tampon;
	    		connectivite[indice1][i] = connectivite[i][indice1];
	    		connectivite[indice2][i] = connectivite[i][indice2];
	    		}
	    	}
	    	
	    }
	    
	    
	    public static void afficheConnectivite(){
	    	DecimalFormat numberFormat = new DecimalFormat("#.00");
	    	for (int i=0; i<nb_sommet; i++){
	    		for (int j=0; j<nb_sommet; j++){
	    			System.out.print(numberFormat.format(connectivite[i][j])+" ");
	    		}
	    		System.out.println();
	    	}	    	
	    }
	 
	
	public static void main(String[] args)
    {
		int nbOfNodes = 100;
		int nbOfGen = 1;
		int nbOfCli = 4;
		double upb = 100;
		int nbInt = nbOfNodes - nbOfGen - nbOfCli; 
		int [] liens_sous_graphes = {2};
		float density = 10;
	
		double valeurTotale = 0;
		//double [][] pos_som = { {1,1},{6,5},{5,0},{2,2},{3,4},{1,4},{3,1},{5,3} } ;
		GenerateBigNetwork Network = new GenerateBigNetwork(nbOfNodes, upb);
	    Graphe g = new Graphe(nbInt, nbOfGen, nbOfCli, liens_sous_graphes, Network.pos_sommet, density);
	    Dijkstra Cheminement = new Dijkstra(g,false,0,1,2,3,4);	    
	    echangePositionsDansTableau(Cheminement.indice_pointcentral, 5);
	    //afficheConnectivite();
	    
	    //Cheminement.afficheSousConnectivite();
	    Dijkstra Cheminementbis = new Dijkstra(g,false,0,1,2,3,4,5);	
	    //Cheminementbis.afficheSousConnectivite();
	    
	    
	    Kruskal kruskalAlgorithm = new Kruskal(6);
	    //kruskalAlgorithm.kruskalAlgorithm(Cheminementbis.sous_connectivite);
	    
	    
	    for (int i=0; i<6; i++){
	    	 for (int j=i; j<6; j++){
	    		 if(i!=j){
	 	    		Cheminementbis.afficheChemin(i,j);
	    		 }
	    	 }
	    }
	    
	    
	    
	    //Cheminement.afficheChemin(1);
	    //Cheminement.afficheChemin(2);
	    //Cheminement.afficheChemin(3);
	    //Cheminement.afficheChemin(4);
	    //Cheminement.afficheChemin(5);
	    //Cheminement.afficheChemin(6);
	    //Cheminement.afficheChemin(7);
	    
    }
}