import java.util.Arrays;

class FCFS {
	
	
	public FCFS(Ship [][] V) {
		int Obj = 0;
		Ship [][] order = new Ship [V.length][V[0].length];
		
		order = solve(V);
	}
	
	public Ship [][]solve (Ship[][] V) {
		Ship [][] order = new Ship [V.length][V[0].length];
		int [][] berthTimes = new int [V.length][V[0].length];
		for(int i =0; i<V.length;i++) {
			for (int j = 0; j<V[0].length; j++) {
				berthTimes[i][j] = V[i][j].getStartBerth();
			}
		}
		order = getShippingPlan(V,berthTimes);
		return order;
		
		
		
		
	}
		
		
	
	
	public Ship[][] getShippingPlan(Ship[][] V, int [][] berthTimes){
		boolean [][] ShipService = new boolean[V.length][V[0].length];
		int [] idorder = new int [V[0].length];
		int [] berthorder = new int [V[0].length];
		for(int i =0; i< V[0].length; i++) {
			Ship Vi = getArrival(V,ShipService);
			idorder[i] = Vi.getId();
			Vi = ServiceTime(Vi,V,i,idorder,berthorder,berthTimes);
			berthorder[i] = Vi.getBerth();
			ShipService[Vi.getBerth()][Vi.getId()] = true;
			/*System.out.print(Vi.getArrivalTime()+ " ");
			System.out.print(Vi.getWaitingTime()+ " ");
			System.out.print(Vi.getHandlingTime() + " ");
			System.out.print(Vi.getDuration()+ " ");
			System.out.print(Vi.getStartTime()+ " ");
			System.out.print(Vi.getEndTime() + " ");
			System.out.print("Berth: " + Vi.getBerth());
			System.out.println();*/
			V[Vi.getBerth()][Vi.getId()] = Vi;
			//System.out.print(Vi.getArrivalTime()+ " ");
		}
		for(int i =0; i<V.length;i++) {
			for (int j = 0; j<V[0].length; j++) {
				//System.out.print(order[i][j].getDuration() + " ");
				//System.out.print(order[i][j].getArrivalTime() + " ");
				//System.out.print(order[i][j].getHandlingTime() + " ");
				//System.out.print(order[i][j].getStartBerth()+ " ");
				//System.out.print(order[i][j].getId()+ " ");
				System.out.print(berthTimes[i][j] + " ");
			}
			System.out.println();
		}
		
		
		for (int j = 0; j<V.length; j++) {
			System.out.println(Arrays.toString(ShipService[j]));
		}
		return V;
		
	}

	public Ship ServiceTime(Ship Vtemp, Ship [][] V,int i,int [] idorder, int[] berthorder,int[][] berthTimes) {
		int duration = 0;
		Ship Vi = new Ship ();
		int minH = 1000000;
		if(i == 0 && Vtemp.getArrivalTime() < Vtemp.getStartBerth()) {
			for(int j = 0;j<V.length;j++) {
				if (minH > V[j][Vtemp.getId()].getHandlingTime() && V[j][Vtemp.getId()].getHandlingTime() != 0) {
					minH = V[j][Vtemp.getId()].getHandlingTime();
					Vi = V[j][Vtemp.getId()];
					}	
				}
			duration = Vi.getStartBerth() -  Vi.getArrivalTime()  + Vi.getHandlingTime();
			Vi.setDuration(duration);
			Vi.setStartTime(Vi.getStartBerth());	
			Vi.setWaitingTime(Vi.getStartTime()-Vi.getArrivalTime());
			Vi.setEndTime(Vi.getStartTime()+Vi.getDuration());
			berthTimes[Vi.getBerth()][i] = Vi.getEndTime();
			return Vi;
		}
		int prevEnd = 0;
		int tempStart = 100000;
		for(int j =0; j<V.length; j ++) {
				prevEnd = berthTimes[j][i-1];
				//System.out.println(prevEnd);
				if(prevEnd <tempStart && V[j][Vtemp.getId()].getHandlingTime()!= 0) {
					tempStart = prevEnd;
					Vi = V[j][Vtemp.getId()];	
			}
		}
		if(tempStart < Vi.getArrivalTime()) {
			tempStart = Vi.getArrivalTime();
		}
		
		
		/*for(int j = 0; j< V.length; j++) {
			if(j!= berthorder[i-1] && (Vi.getArrivalTime() > Vi.getBerth())) {
				tempStart = Vi.getStartBerth();
				tempWaitingTime = tempStart - Vi.getArrivalTime();
				tempDuration = tempWaitingTime + Vi.getHandlingTime();
				Vi.setBerth(j);
			}
			if(tempStart > prevEnd && prevEnd > 0) {
				tempStart = prevEnd;
				tempWaitingTime = tempStart - Vi.getArrivalTime();
				tempDuration = tempWaitingTime + Vi.getHandlingTime();
				Vi.setBerth(j);
			}
			
		}*/
		Vi.setStartTime(tempStart);
		if(tempStart - Vi.getArrivalTime() < 0) {
			Vi.setWaitingTime(0);
		}else {
			Vi.setWaitingTime(tempStart - Vi.getArrivalTime());
		}
		Vi.setDuration(Vi.getWaitingTime() + Vi.getHandlingTime());
		Vi.setEndTime(Vi.getStartTime()+Vi.getHandlingTime());
		
		berthTimes[Vi.getBerth()][i] = Vi.getEndTime();
		for(int j= 0; j<V.length;j++) {
			if (j != Vi.getBerth()) {
				berthTimes[j][i] = berthTimes[j][i-1];
			}
		}
		
		return Vi;
		
		
		//return duration;
	}
	
	public Ship getArrival(Ship[][] V,boolean[][] ShipService) {
		Ship Vtemp = new Ship();
		int minA = 1000000;
		
		for(int i =0; i<V[0].length;i++) {
			boolean visited = false;
			for (int j = 0; j<V.length; j++) {
				if(ShipService[j][i]== true) {
					visited = true;
				}
			}
			if (minA > V[0][i].getArrivalTime() && visited == false) {
				minA = V[0][i].getArrivalTime();
				Vtemp =  V[0][i];					
			}
		}
		return Vtemp;
	}

}
