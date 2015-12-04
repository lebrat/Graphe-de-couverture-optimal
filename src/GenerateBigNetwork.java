
public class GenerateBigNetwork {
	private int nb_points;
	private double up_bound;
	public double [][] pos_sommet;
	
	public GenerateBigNetwork(int nbOfNodes, double upb){
		this.nb_points = nbOfNodes;
		this.up_bound = upb;
		pos_sommet = new double [nb_points][2];
		for (int i = 0; i < nb_points; i ++){			
			pos_sommet[i][0] =Math.random()*up_bound;
			pos_sommet[i][1] =Math.random()*up_bound;		
		}
		
	}
	

}
