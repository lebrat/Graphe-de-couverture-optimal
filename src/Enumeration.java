import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.SpringLayout.Constraints;

import book.set.Iterator;
import grph.algo.msp.KruskalAlgorithm;
import java4unix.impl.Test_IO_Speed_For_File;

public class Enumeration {
	// Creation of a function which given a connectivity matrix return a vector indicating connectivy
	// where a connex subset is define by the lower # of vertex contained in that subset
	public static int[] connexity (double [][] connectivity){
		int s = connectivity.length;
		int [] v = new int[s];
		for (int i = 0; i < s; i ++){
			v[i] = i; // we initialize the connexity table
		}
		for (int i = 0; i < s -1 ; i ++){
			for (int j = i +1; j < s; j ++){
				if(connectivity[i][j] != 0.0){
					for(int x = 0; x <s; x ++){
						if(v[x]==v[j])
							v[x] = v[i];					
					}
				}
			}
		}
		return v;
	}
	
	public static boolean areConnected(double [][] connectivity,int ... gen){
		//TODO error raise if gen are out of bound
		boolean connex=true;
		int [] v = connexity(connectivity);
		int prec = v[gen[0]];
		for(int g : gen){
			connex = (prec == v[g])&&(connex);
			prec = v[g];
		}
		return connex;
	}
	
	public static Set<Set<Integer>> powerSet(Set<Integer> originalSet) {
	    Set<Set<Integer>> sets = new HashSet<Set<Integer>>();
	    if (originalSet.isEmpty()) {
	    	sets.add(new HashSet<Integer>());
	    	return sets;
	    }
	    List<Integer> list = new ArrayList<Integer>(originalSet);
	    Integer head = list.get(0);
	    Set<Integer> rest = new HashSet<Integer>(list.subList(1, list.size())); 
	    for (Set<Integer> set : powerSet(rest)) {
	    	Set<Integer> newSet = new HashSet<Integer>();
	    	newSet.add(head);
	    	newSet.addAll(set);
	    	sets.add(newSet);
	    	sets.add(set);
	    }		
	    return sets;
	}
	
	public static List<ArrayList<Integer>> possibleGraph(Set<Set<Integer>> s, int ... gen){
		List<ArrayList<Integer>> L = new ArrayList<ArrayList<Integer>>();
		int compt;
		for (Set<Integer> si : s){			
			compt = 0;
			for( int g : gen){
				if(si.contains(g)){
					compt++;
				}		
			}
			if(compt==gen.length){
				ArrayList<Integer> list = new ArrayList<Integer>(si);
				L.add(list);
			}
		}
		return L;
	}
	public static void ennumerateGraph (){
		int nVertices = 18;
		ArrayList Best = new ArrayList<Integer>();
		double BestCF = Double.MAX_VALUE;
		double[][] C = generateTab(nVertices, 0, 100);
		printTab(C);
		Set<Integer> bigset = new HashSet<Integer>();
        for(int i = 0; i < nVertices; i ++)
        	bigset.add(i);
        Set<Set<Integer>> s = powerSet(bigset);
        List<ArrayList<Integer>> L = possibleGraph(s, 0,1,2);
        ArrayList<Integer> Test = new ArrayList<Integer>();
        
        for(int k = 0; k < L.size(); k++){        	
        	Test = L.get(k);
        	double [][] Creduced = reduceConnectivity(C, Test);    		
    		double [][] CreducedExp = unpadArray(Creduced);
    		if(areConnected(Creduced,0,1,2)){
    			Kruskal kruskalAlgorithm = new Kruskal(CreducedExp.length-1);
        		double [][] MST = kruskalAlgorithm.kruskalAlgorithm(CreducedExp);
        		if(Graphe.costFunction(MST)<BestCF){
        			System.out.println(BestCF);
        			BestCF = Graphe.costFunction(MST);
        			Best = Test;
        			System.out.println(Best.toString());
        		}
    		}	
        }        
        
	}
	
	public static double [][] reduceConnectivity( double [][] C, ArrayList<Integer> v ){
		double [][] Creduced = new double[v.size()][v.size()];
		for(int i =0; i < v.size(); i++){
			for(int j=i+1; j<v.size();j++){
				Creduced[i][j] = C[v.get(i)][v.get(j)];
				Creduced[j][i] = Creduced[i][j]; 
			}
		}
		return Creduced;
	}
	
	public static double[][] generateTab(int nbLigne, int minValue, int maxValue) {
		Random r = new Random();
		double[][] distanceTab = new double[nbLigne][nbLigne];
		for (int i =0 ; i< nbLigne;i++){
			for (int j = i+1 ; j <nbLigne;j++){
				distanceTab [i][j]=r.nextInt(maxValue-minValue+1)+minValue;
				distanceTab [j][i]=distanceTab [i][j];
			}
		}
		
		
		return distanceTab;
	}
	public static double[][] unpadArray(double[][] A){
		double [][] Areturn =  new double[A.length+1][A.length+1];
		for(int i = 0; i < A.length; i++){
			for(int j = 0; j < A.length; j ++){
				Areturn[i+1][j+1] = A[i][j];
			}
		}
		return Areturn;
	}
	public static void printTab(double[][] T){
		DecimalFormat numberFormat = new DecimalFormat("#.00");
      for (int i = 0; i < T.length; i++)
          System.out.print("\t" + i);
      System.out.println();
      for (int source = 0; source < T.length; source++)
      {
          System.out.print(source + "\t");
          for (int destination = 0; destination < T.length; destination++)
          {
              System.out.print(numberFormat.format(T[source][destination]) + "\t");
          }
          System.out.println();
      }
	}
	public static void test_connexity(){
		//toy matrix
		//double [][] C = {{0,1,0,0,0,0,0,0},{1,0,0,1,0,0,0,0},{0,0,0,0,1,0,0,0},{0,1,0,0,0,0,0,1},{0,0,1,0,0,1,0,0},{0,0,0,0,1,0,1,0},{0,0,0,0,0,1,0,0},{0,0,0,1,0,0,0,0}};
		double [][] C = {{0,1,0,0,0,0,0,0},{1,0,0,0,0,0,0,1},{0,0,0,0,1,0,0,0},{0,0,0,0,0,0,0,0},{0,0,1,0,0,1,0,0},{0,0,0,0,1,0,1,0},{0,0,0,0,0,1,0,0},{0,1,0,0,0,0,0,0}};
		int [] v = connexity(C);
		//display the result
		for( int i = 0; i < v.length; i ++)
			System.out.print(v[i]+" ");
	}
	public static void test_areConnected(){
		double [][] C = {{0,1,0,0,0,0,0,0},{1,0,0,0,0,0,0,1},{0,0,0,0,1,0,0,0},{0,0,0,0,0,0,0,0},{0,0,1,0,0,1,0,0},{0,0,0,0,1,0,1,0},{0,0,0,0,0,1,0,0},{0,1,0,0,0,0,0,0}};
		boolean b1 = areConnected(C,0,1);
		boolean b2 = areConnected(C,3,1);
		boolean b3 = areConnected(C,5,6);
		boolean b4 = areConnected(C,0,6);
		System.out.println("TEST areConnected");
		System.out.println("Result expected : true    result given : "+b1);
		System.out.println("Result expected : false   result given : "+b2);
		System.out.println("Result expected : true    result given : "+b3);
		System.out.println("Result expected : false   result given : "+b4);
	}
	
	public static void testPossibleGraph(){
		Set<Integer> bigset = new HashSet<Integer>();
        bigset.add(0);
        bigset.add(1);
        bigset.add(2);
        bigset.add(3);
        bigset.add(4);
        bigset.add(5);
        bigset.add(6);
        bigset.add(7);
        Set<Set<Integer>> s = powerSet(bigset);
        List<ArrayList<Integer>> L = possibleGraph(s, 0,1,2);
        System.out.println(L.toString());
	}
	
	public static void testReducedConnectivity(){
		double[][] C = generateTab(7, 0, 100);
		printTab(C);
		ArrayList<Integer> L = new ArrayList<Integer>();
		L.add(1);
		L.add(3);
		L.add(4);
		L.add(5);
		L.add(6);
		double [][] Creduced = reduceConnectivity(C, L);
		printTab(Creduced);
		double [][] CreducedExp = unpadArray(Creduced);
		Kruskal kruskalAlgorithm = new Kruskal(CreducedExp.length-1);
		double [][] MST = kruskalAlgorithm.kruskalAlgorithm(CreducedExp);
		printTab(MST);
		Graphe.dotgraph(MST);
	}
	
	public static void main(String[] args) {       
        //test_connexity();
        //test_areConnected();
        //testPossibleGraph();
		//testReducedConnectivity();
		ennumerateGraph();
		
        
        
    }
	
}
