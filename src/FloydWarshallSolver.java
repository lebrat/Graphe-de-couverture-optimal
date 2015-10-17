
public class FloydWarshallSolver {
	 public static void reduce(int[][] distanceR) {
	       int lastIndexChange = 0;
	       int start = 0;
	       int nbNode = distanceR.length;
	       do {
	           for (int end = 0; end < nbNode; end++) {
	               for (int intermediate = 0; intermediate < nbNode; intermediate++) {
	                   int newValue = distanceR[start][intermediate] + distanceR[intermediate][end];
	                   if (distanceR[start][end] > newValue) {
	                       distanceR[start][end] = newValue;
	                       distanceR[end][start] = newValue;
	                       lastIndexChange=start;
	                   }
	               }
	           }
	           start++;
	           if (start == nbNode) {
	               start = 0;
	           }
	       } while (lastIndexChange != start);
	   }
}
