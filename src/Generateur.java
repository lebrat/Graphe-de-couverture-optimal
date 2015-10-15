
public class Generateur {
	private static int position;
	private static int nb_client;
	private static int [] client = new int[nb_client];
	
	public static void set_position(int p){
		position = p;
	}
	public static void set_client(int no, int [] pos ){
		nb_client = no;
		client = pos;
		//TODO error raise 
	}
	public int [] get_client_pos(){
		return client;
	}

}
