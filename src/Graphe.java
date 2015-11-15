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
	 
	 
	
	public static void main(String[] args)
    {
		int nb_s_int = 5;
		int nb_gen = 1;
		int nb_cli = 2;
		int [] liens_sous_graphes = {2};
		float density = 60;
		double [][] pos_som = { {1,1},{6,5},{5,0},{2,2},{3,4},{1,4},{3,1},{5,3} } ;
	    Graphe g = new Graphe(nb_s_int, nb_gen, nb_cli, liens_sous_graphes, pos_som, density);
	    Dijkstra Cheminement = new Dijkstra(g,0,1,2,3,4);
	    DecimalFormat numberFormat = new DecimalFormat("#.00");
	    for( int i = 0; i < 5; i++){
	    	for(int j = 0; j < 5; j++){
	    		System.out.print(numberFormat.format(Dijkstra.sous_connectivite[i][j])+" ");
	    	}
	    	System.out.print("\n");
	    }
	    
	    Kruskal kruskalAlgorithm = new Kruskal(4);
	    kruskalAlgorithm.kruskalAlgorithm(Dijkstra.sous_connectivite);
	    
	    
	    //Cheminement.afficheChemin(1);
	    //Cheminement.afficheChemin(2);
	    //Cheminement.afficheChemin(3);
	    //Cheminement.afficheChemin(4);
	    //Cheminement.afficheChemin(5);
	    //Cheminement.afficheChemin(6);
	    //Cheminement.afficheChemin(7);
	    
    }
}
