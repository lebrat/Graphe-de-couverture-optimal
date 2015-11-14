import java.util.Vector;

public class Dijkstra {
	// creation du graphe
    public static final int INFINITE = 1000;//Integer.MAX_VALUE;
    public final static int ALPHA_NOTDEF = -999 ;// on met final psk c'est une constante
    private int x0;
    private int [] Sommets_proches;//ensemble de sommets dont les distances les plus courtes à la source sont connues au départ seulement Source
    private int [] Predecesseurs;//ensemble des prédécesseur des sommets de 0 à N-1;
    private Graphe G;
    private double [] D; //tableau des valeurs du meilleur raccourci pour se rendre à chaque sommet
    // rajout
    private boolean [] noeudsMarqués;
    private static int dimMatrice;//je rajoute ça pour simplifier le code.
    
    public Dijkstra(int x, Graphe G){    
        x0 = x;
        Graphe g = G;
        dimMatrice = Graphe.nb_sommet;
        Sommets_proches = new int [dimMatrice]; //sommets atteints
        D = new double [dimMatrice]; //distances
        noeudsMarqués = new boolean[dimMatrice];
        Predecesseurs = new int [dimMatrice];
        calculePlusCourtChemin();
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
            } //end if
        }//end for
//        for (int i=0; i<dimMatrice;i++){
//            System.out.print(" S[i]"+S[i]);
//        }
//        for (int i=0; i<dimMatrice;i++){
//            System.out.print(" R["+i+"]"+R[i]);
//        }
//        System.out.println();
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
                D[i] =- G.ALPHA_NOTDEF+1;  //Si le point x0 n'a pas de connexion avec i, on fixe la distance à -999
            }
        }
    }
    
    private void majDistMin(int n){
        for (int i = 0; i < dimMatrice; i++){            
                if (!contains(Sommets_proches,i)){
                    if (D[n] + distanceDsGraphe(n,i)<D[i]){
                        D[i]=D[n] + distanceDsGraphe(n,i);                     
                        Predecesseurs[i]=n;
                    }
                }
        }
    }
    private double distanceDsGraphe (int t, int s){
        if (G.existeArc(t, s)){        
            return G.getDistance(t,s);
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
    //fonction à définir min
    private int min (int i, int j){
        if (i<=j)
            return i;
        else return j;
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
        
    	int N = Graphe.ALPHA_NOTDEF ;
        
        int nb_s_int = 5;
		int nb_gen = 1;
		int nb_cli = 2;
		int [] liens_sous_graphes = {2};
		float density = 100;
		double [][] pos_som = { {1,1},{6,5},{5,0},{2,2},{3,4},{1,4},{3,1},{5,3} } ;
		Graphe g = new Graphe(nb_s_int, nb_gen, nb_cli, liens_sous_graphes, pos_som, density);

        
        
    }

}
