import java.io.FileNotFoundException;
// "25x5-01.txt"
public class ProgramRun {
	public static void main (String[] args) {
		BerthSolver solver = new BerthSolver("25x5-01");
		StopWatch watch = new StopWatch();
		watch.start();
		solver.solve();
		System.out.println(watch.stop());
		solver.showSolve();
		
	}

}
