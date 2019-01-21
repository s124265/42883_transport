import java.io.FileNotFoundException;


public class BerthSolver {
	Ship[][] V;
	Ship [][] solV;
	double [] avgWeight;
	public BerthSolver(String filename) {
		
		try {
			DataReader inst = new DataReader(filename);
			this.V = inst.getShips();
			this.avgWeight = inst.getWeights(V);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		/*for(int i =0; i<V[0].length;i++) {
				System.out.print(avgWeight[i] + " ");
		}*/
		System.out.println();
		//}
		
	}
	
	public void solve() {
		FCFS sol1 = new FCFS(V,avgWeight);
		solV = sol1.solve(V);
		HC sol2 = new HC(solV,avgWeight);
		
		
	}
	
	public void showSolve() {
		ScheduleViz.drawSchedule(solV);
	}

}
