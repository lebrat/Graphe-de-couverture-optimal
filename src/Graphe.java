import grph.Grph;

public class Graphe {
	private int nb_sommet;
	private int nb_generateur;
	private int[] generateur = new int[nb_generateur];
	private int[] position = new int[nb_generateur];
	
	public static void main(String[] args)
    {
	Grph g = new grph.in_memory.InMemoryGrph();
	g.display();
	g.grid(10, 10);
	g.getDiameter();
	g.removeVertex(11);
	g.removeVertex(21);
	/*p = g.getShortestPath(0, 54)
	p.setColor(g, 5)*/
    }
}
