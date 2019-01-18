import java.io.FileNotFoundException;
// "25x5-01.txt"
public class ProgramRun {
	public static void main (String[] args) {
		BerthSolver solver = new BerthSolver("25x5-01");
		solver.solve();
		solver.showSolve();
		
	}

}
