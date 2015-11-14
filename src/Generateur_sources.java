
public class Generateur_sources {
	private static int nb_sources;
	private static double [][] positions_sources = new double[nb_sources][2];
	
	public static void init(int nb_s, double [][] pos_s){
		nb_sources = nb_s;
		positions_sources = pos_s;		
	}
	/*public static void set_client(int no, int [] pos ){
		nb_client = no;
		client = pos;
		//TODO error raise 
	}
	public int [] get_client_pos(){
		return client;
	}*/

}
