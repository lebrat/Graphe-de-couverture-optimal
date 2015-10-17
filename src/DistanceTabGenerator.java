import java.util.Random;

public class DistanceTabGenerator {
	
	private static int _nbCall = 0;
	
	public static int[][] generateTab(int nbLigne, int minValue, int maxValue) {
		_nbCall++;
		Random r = new Random(_nbCall);
		int[][] distanceTab = new int[nbLigne][nbLigne];
		for (int i =0 ; i< nbLigne;i++){
			for (int j = i+1 ; j <nbLigne;j++){
				distanceTab [i][j]=r.nextInt(maxValue-minValue+1)+minValue;
				distanceTab [j][i]=distanceTab [i][j];
			}
		}
		
		
		return distanceTab;
	}


}
