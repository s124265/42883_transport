import java.io.FileNotFoundException;


public class BerthSolver {
	Ship[][] V;
	public BerthSolver(String filename) {
		
		try {
			DataReader inst = new DataReader(filename);
			this.V = inst.getShips();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		Ship [][] newV = new Ship [V.length][V[0].length];
		//for (int j = 0; j<V.length;j++) {
		//	for(int i =0; i<V[0].length;i++) {
		//		System.out.print(V[j][i].getHandlingTime() + " ");
		//	}
		//	System.out.println();
		//}
		
		solve(V);
	}
	
	public void solve(Ship[][] V) {
		FCFS sol1 = new FCFS(V);
		
	}

}
