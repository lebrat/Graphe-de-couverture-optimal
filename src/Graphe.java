import java.util.Random;
import grph.Grph;


public class Graphe {
	private static int nb_sommet;
	private static int [][] connectivite;
	//Graphe est la representation du reseau de fil installable dans le satelite
	//La table de connectivité Cij donne le coup de la connexion entre le somment i et j
	//zero signifie que la liaison est impossible
	private int nb_source;
	private Generateur[] generateurs = new Generateur[nb_source];
	//Tableau de données sur la position de la source (masse ou generateur) 
	//Cette classe contient la position de la source ainsi que le nombre
	//et la position de tous ces clients.

	
	public static void init(int nb_s,int borne_sup, float densite){
		nb_sommet = nb_s;
		Random r = new Random();
		connectivite = new int[nb_sommet][nb_sommet];
		for(int i = 0 ; i < nb_sommet ; i++){
			for(int j = i; j < nb_sommet; j++){
				if(r.nextInt(100)<densite){
					connectivite[i][j] = r.nextInt(borne_sup);
					connectivite[j][i] = connectivite[i][j];
				}
				else{
					connectivite[i][j] = 0;
					connectivite[j][i] = 0;
				}
			}
		}		
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
	
	public static void main(String[] args)
    {
	init(20,10,50);
	//print();
    }
}
