import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import book.set.Iterator;
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
	
	public static <T> Set<Set<T>> powerSet(Set<T> originalSet) {
	    Set<Set<T>> sets = new HashSet<Set<T>>();
	    if (originalSet.isEmpty()) {
	    	sets.add(new HashSet<T>());
	    	return sets;
	    }
	    List<T> list = new ArrayList<T>(originalSet);
	    T head = list.get(0);
	    Set<T> rest = new HashSet<T>(list.subList(1, list.size())); 
	    for (Set<T> set : powerSet(rest)) {
	    	Set<T> newSet = new HashSet<T>();
	    	newSet.add(head);
	    	newSet.addAll(set);
	    	sets.add(newSet);
	    	sets.add(set);
	    }		
	    return sets;
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
	public static void main(String[] args) {       
        test_connexity();
        test_areConnected();
        Set<Integer> bigset = new HashSet<Integer>();
        bigset.add(0);
        bigset.add(1);
        bigset.add(2);
        bigset.add(3);
        bigset.add(4);
        bigset.add(5);
        bigset.add(6);
        bigset.add(7);
        
        Set<Integer> genset = new HashSet<Integer>();
        genset.add(0);
        genset.add(1);
        genset.add(2);
        
        ArrayList<Set<Integer>> L = new ArrayList<Set<Integer>>();
        for (Set<Integer> si : powerSet(bigset))
        	L.add(si);
        
    }
	
}
