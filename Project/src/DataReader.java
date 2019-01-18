import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class DataReader {
	private Ship[][] V;
	
	
	public  DataReader(String filename) throws FileNotFoundException{
		File data = new File(filename);
		Scanner in = new Scanner(data);
		int n = in.nextInt(); // Number of ships
		int m = in.nextInt(); // Number of berths
		
		V = new Ship[m][n];
		//System.out.println(n + " " + m);
		int [][] h = new int[m][n];
		for (int j = 0; j<m; j++) {
			for(int i =0; i<n; i++){
				V[j][i] = new Ship(j, i , in.nextInt());
				h[j][i] = V[j][i].getHandlingTime();
			}
		}
		int [] s = new int [m];
		int [] e = new int [m];
		int [] se = new int[m];
		for(int j = 0; j < m; j++) {
			s[j] = in.nextInt();
			e[j] = in.nextInt();
			se[j] = in.nextInt();
			for (int i =0; i<n; i++) {
				V[j][i].setStartBerth(s[j]);
				V[j][i].setEndBerth(e[j]);
			}
		}
		int [] a = new int [n];
		for(int i =0; i<n ; i++) {
			a[i] = in.nextInt();
			for(int j =0; j<m;j++) {
				V[j][i].setArrivalTime(a[i]);
			}
		}
		
		//for (int j = 0; j<m; j++) {
			//for(int i =0; i<n; i++){
			//	System.out.print(V[j][i].getStartBerth() + " ");
			//}
			//System.out.println();
		//}
		
		
		
		//for (int j = 0; j<n; j++) {
		//	for(int i =0; i<m; i++){
		//		System.out.print(newh[j][i]+ " ");
		//	}
		//	System.out.println();
		//}
		
		
		in.close();
		
	}
	
	public Ship[][] getShips(){ return V;}
	
	
}
