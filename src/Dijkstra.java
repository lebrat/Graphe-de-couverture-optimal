import java.util.Vector;

public class Dijkstra {
	// creation du graphe
    public static final int INFINITE = 1000;//Integer.MAX_VALUE;
    public final static int ALPHA_NOTDEF = -999 ;// on met final c'est une constante
    private int x0;
    private int [] Sommets_proches;//ensemble de sommets dont les distances les plus courtes à la source sont connues au départ seulement Source
    private int [] Predecesseurs;//ensemble des prédécesseur des sommets de 0 à N-1;
    private Graphe G;
    private double [] D; //tableau des valeurs du meilleur raccourci pour se rendre à chaque sommet
    // rajout
    private boolean [] noeudsMarqués;
    private static int dimMatrice;//je rajoute ça pour simplifier le code.
    public static double [][] sous_connectivite;
    public static int [][][] chemin;
    
    public Dijkstra(Graphe G, int ... points){    
    	if (points.length == 0) {
    	      throw new IllegalArgumentException("No values supplied.");
    	}
    	else{
    		dimMatrice = Graphe.nb_sommet;
    		sous_connectivite = new double [points.length][points.length];
    		chemin = new int [points.length][points.length][dimMatrice];
    		int iter = 0;
    		for (int x : points) {
    			System.gc(); // a chaque iteration nous realouons les matrices 
    			x0 = x;    			
    			Sommets_proches = new int [dimMatrice]; //sommets atteints
    			D = new double [dimMatrice]; //distances
    			noeudsMarqués = new boolean[dimMatrice];
    			Predecesseurs = new int [dimMatrice];
    			calculePlusCourtChemin();
    			int sscon = 0;
    			for(int p : points){
    				sous_connectivite[iter][sscon]= D[p];
    				int source = x0;
    		        int antécédent = p;
    		        Vector <Integer> lesNoeudsIntermediaires = new Vector<Integer>();;    		 
    		        while (antécédent!=source){
    		            lesNoeudsIntermediaires.add(antécédent);
    		            antécédent = Predecesseurs[antécédent];
    		            
    		        }
    		        lesNoeudsIntermediaires.add(source);
    		        int buffer = 0;
    		        for (int j= lesNoeudsIntermediaires.size()-1; j>=0;j--){    		           
    		            chemin[x][sscon][buffer]=lesNoeudsIntermediaires.get(j);
    		        }
    		        sscon ++;
    			}
    			
    			iter ++; //on remplit la matrice de sous connectivité
    		}
    	}
    	
    }
    
    private void calculePlusCourtChemin(){ 
        int n =0;
        for (int a = 0; a < dimMatrice; a++){
            noeudsMarqués[a] =false;
            Sommets_proches[a]=-1; //en supposant qu'on numérote les sommets de 0 ou de 1 à n.
            Predecesseurs[a]=-1; // pour les noeuds qui n'ont pas de prédecésseur
        }
        
        Sommets_proches[n]=x0;
        D[x0]=0;
        Predecesseurs[x0]=x0;
        initDistMin();
        for (int i = 0; i< dimMatrice ;i++){    
            if (!contains(Sommets_proches,i)){// à revoir
                int t = choixSommet();
                noeudsMarqués[t] = true;
                n++;
                Sommets_proches[n]=t;
                majDistMin(t);
            }
        }
    }
    
    //initDistMin permet de regarder si il y a un chemin entre le point actuel x0 et tous les autres
    private void initDistMin(){
        noeudsMarqués[x0]=true;
        for (int i=0; i<dimMatrice;i++){
            if(Graphe.existeArc(x0,i)){
                D[i] = Graphe.getDistance(x0,i);
                Predecesseurs[i] = x0;      // Si x0 a une connexion avec i, on fixe la distance et on défini xo predecesseur de i
            }
            else {
                if (x0 != i)
                D[i] =- Graphe.ALPHA_NOTDEF+1;  //Si le point x0 n'a pas de connexion avec i, on fixe la distance à -999
            }
        }
    }
    
    private void majDistMin(int n){
        for (int i = 0; i < dimMatrice; i++){            
                if (!contains(Sommets_proches,i)){
                    if (D[n] + distanceDsGraphe(n,i)<D[i]){
                        D[i]=D[n] + distanceDsGraphe(n,i);                     
                        Predecesseurs[i]=n;
                        // TODO
                        // ici la méthode a effectuer serrait d'arreter le dijkstra
                        // on fixe la distance maximale des vertexes que l'on veut atteindre
                        // tous les chemins qui ont une taille supérieur à cette distance
                        // sont abortés
                    }
                }
        }
    }
    private double distanceDsGraphe (int t, int s){
        if (Graphe.existeArc(t,s)){        
            return Graphe.getDistance(t,s);
        }
        else {
            return INFINITE;
        }
    }

    public int choixSommet(){
        double valeurMin = INFINITE;
        int min = x0;
        for (int i=0; i<dimMatrice ;i++){
            if (!noeudsMarqués[i])
                if (D[i]<=valeurMin){
                    min = i;
                    valeurMin = D[i];
                }
        }
        return min;
    }
    
    
    //fonction à définir :S.contains(i)
    private boolean contains(int[] S, int i){
        return noeudsMarqués[i];
    }
    
    public double longueurChemin (int i){
        return D[i];
    }

    public void afficheChemin(int i){
        int source = x0;
        int antécédent = i;
        Vector <Integer> lesNoeudsIntermediaires = new Vector<Integer>();;
        System.out.println("Chemin de "+x0+" à "+ i+":");
        while (antécédent!=source){
            lesNoeudsIntermediaires.add(antécédent);
            antécédent = Predecesseurs[antécédent];
            
        }
        lesNoeudsIntermediaires.add(source);
        for (int j= lesNoeudsIntermediaires.size()-1; j>0;j--){
            System.out.print("-->"+lesNoeudsIntermediaires.get(j));
        }
        System.out.println();
    }
    
    public static void main(String[] args) {       
        
        
    }

}
