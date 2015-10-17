import java.util.Random;

public class DataGenerator {
	private static int _index = 0;

	private final int _nNode;
	private final int _nEquipment;
	private final int[][] _distance;
	private final int[] _equipmentIndex;
 
	public DataGenerator(int _nNode, int _nEquipment) {
		super();
		if (_nEquipment>_nNode){
			throw new IllegalArgumentException();
		}
		this._nNode = _nNode;
		this._nEquipment = _nEquipment;
		_distance = DistanceTabGenerator.generateTab(_nNode, 1, 100);
		FloydWarshallSolver.reduce(_distance);
		_equipmentIndex = new int[_nEquipment];
		int index = 0;
		Random r = new Random(++_index);
		for (int i = 0; i < _nNode; i++) {
			if (r.nextDouble() < (double) (_nEquipment - index) / (double) (_nNode - i)) {
				_equipmentIndex[index] = i;
				index++;
			}
		}
	}

	public int getNNode() {
		return _nNode;
	}

	public int getNEquipment() {
		return _nEquipment;
	}

	public int[][] getDistance() {
		return _distance;
	}

	public int[] getEquipmentIndex() {
		return _equipmentIndex;
	}
	
}

