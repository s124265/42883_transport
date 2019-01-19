import java.io.FileNotFoundException;


public class BerthSolver {
	Ship[][] V;
	Ship [][] solV;
	public BerthSolver(String filename) {
		
		try {
			DataReader inst = new DataReader(filename);
			this.V = inst.getShips();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		//for (int j = 0; j<V.length;j++) {
		//	for(int i =0; i<V[0].length;i++) {
		//		System.out.print(V[j][i].getHandlingTime() + " ");
		//	}
		//	System.out.println();
		//}
		
	}
	
	public void solve() {
		FCFS sol1 = new FCFS(V);
		solV = sol1.solve(V);
		HC sol2 = new HC(solV);
		
		
	}
	
	public void showSolve() {
		ScheduleViz.drawSchedule(solV);
	}

}
