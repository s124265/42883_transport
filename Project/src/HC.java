import java.util.*;


/*
	Still to do:
	
	Make more random iterations
	Check for mistakes, and that wait time etc. is correctly calculated (like the order of the arrivaltime)
	Make new berth calculations correctly (still need to calculate another berth)
	Make it run over time and stuff
	
 */


class HC {
	boolean [][] ShipService;
	
	
	public HC(Ship [][] V,double [] avgWeight) {
		boolean [][] InitService = new boolean [V.length][V[0].length];
		for(int j=0; j<V.length;j++) {
			for(int i=0;i<V[0].length;i++) {
				if(V[j][i].getDuration()>0) {
					InitService[j][i] = true;
				}
			}
		}
		double Obj = solve(V,InitService,avgWeight);
	}
		
	public double solve(Ship[][] V,boolean [][] ShipService, double [] avgWeight) {
		double Obj = 0;
		double newcost = 0;
		Ship [][] S = new Ship [V.length][V[0].length];
		S = Step(V,avgWeight);
		if(costObj(V,avgWeight) < costObj(S,avgWeight)) {
			V = S;
			newcost = costObj(S,avgWeight);
		}
		
		Obj = newcost;
			
		return Obj;
	}
	public double costObj(Ship[][] V, double [] avgWeight) {
		double sol = 0;
		double weight = 0;
		for(int i =0; i<V[0].length;i++) {
			int serviceTime = 0;
			for(int j=0; j<V.length;j++) {
				serviceTime += V[j][i].getDuration();
			}
			weight += serviceTime*avgWeight[i];
		}
		
		sol = weight;
		return sol;
		
	}
	
	public boolean terminate (boolean improved) {
		if (improved) {
			return false;
		} else {
			return true;
		}
	}
	
	public Ship[][] Step (Ship[][] V, double [] avgWeight){
		Ship [][] best = new Ship[V.length][V[0].length];
		double bestcost = 100000000;
		ArrayList<Ship[][]> Neighbours = new ArrayList<Ship[][]>();
		Neighbours = mapNeighbours(V);
		for(Ship[][] n: Neighbours) {
			if (bestcost > costObj(n,avgWeight)){
				bestcost = costObj(n,avgWeight);
				best = n;
			}
		}
		
		System.out.println(bestcost);
		return best;
	}
	
	public ArrayList<Ship[][]> mapNeighbours(Ship[][] V) {
		ArrayList<Ship[][]> N = new ArrayList<Ship[][]>();
		int maxHand = 0;
		Ship tempShip = new Ship();
			
		for(int j = 0; j<V.length;j++) {
			for(int i = 0; i<V[0].length;i++) {
				if(V[j][i].getDuration()<=0) {
					continue;
				}
				if (maxHand<V[j][i].getHandlingTime()) {
					maxHand = V[j][i].getHandlingTime();
					tempShip = V[j][i];
				}				
			}
		}
		int besthand = 0;
		int bestBerth = 0;
		for(int j = 0; j<V.length;j++) {
			if(besthand < V[j][tempShip.getId()].getHandlingTime() && j != tempShip.getBerth()) {
				besthand = V[j][tempShip.getId()].getHandlingTime();
				bestBerth = j;
			}
			
			
			if(bestBerth == tempShip.getBerth()) {
				
			}
		Ship[][] Vtemp = new Ship [V.length][V[0].length];
		Vtemp = swapBerth(V,tempShip,bestBerth);
		N.add(Vtemp);
		
			
		}		
		return N;
	}
	
	public Ship [][] swapBerth(Ship[][] V,Ship tempShip, int bestBerth){
		Ship [][] newV = V;
		Random rand = new Random();
		int randBerth = rand.nextInt(V.length);

		while (randBerth == tempShip.getBerth()) {
			randBerth = rand.nextInt(V.length);
		}
		
		int randShip = rand.nextInt(V[0].length);
		
		while(V[randBerth][randShip].getDuration()<= 0) {
			randShip = rand.nextInt(V[0].length);
		}
		
		Ship swapShip = V[randBerth][randShip];
		
		Ship newShip = V[randBerth][tempShip.getId()];
		swapShip = V[tempShip.getBerth()][randShip];
		
		newV[tempShip.getBerth()][tempShip.getId()].setDuration(0);
		newV[randBerth][randShip].setDuration(0);
		
		newV = calcnewV(V,newShip,swapShip);
		
		
		
		return newV;
		
	}
	
	public Ship [][] calcnewV(Ship[][]V, Ship newShip, Ship swapShip) {
		Ship [][] newV = new Ship[V.length][V[0].length];
		newV = V;
		boolean [] usedShips = new boolean [V[0].length];
		ArrayList <Integer> order = new ArrayList <Integer>();
		
		
		int j = 0;
		while (j < 5) {
			int minA = 100000000;
			int index = -1;
			for (int i= 0; i<V[0].length;i++) {
				if(V[newShip.getBerth()][i].getArrivalTime()< minA && usedShips[i]==false && V[newShip.getBerth()][i].getDuration()>0) {
					minA = V[newShip.getBerth()][i].getArrivalTime();
					index = V[newShip.getBerth()][i].getId();
					j = 0;
				}
			}
			j++;
			if(index > -1) {
			order.add(index);
			usedShips[index] = true;
			}
				
		}
		//System.out.println(order + " " + order.size());
		//System.out.println(newShip.getBerth() + " "+  order.get(0));
		//System.out.println(V[newShip.getBerth()][order.get(0)].getArrivalTime());
		
		for(int i=0; i<order.size();i++) {
			int tempStart = 0;
			int tempWait = 0;
			int tempDura = 0;
			int endTime = 0;
			if(i == 0 && V[newShip.getBerth()][order.get(i)].getArrivalTime() < V[newShip.getBerth()][order.get(i)].getStartBerth()) {
				tempStart =V[newShip.getBerth()][order.get(i)].getStartBerth();
				newV[newShip.getBerth()][order.get(i)].setStartTime(tempStart);
			} else if(i==0) {
				tempStart = V[newShip.getBerth()][order.get(i)].getArrivalTime();
				newV[newShip.getBerth()][order.get(i)].setStartTime(tempStart);
			}
			
			if(i == 0) {
				tempWait = newV[newShip.getBerth()][order.get(i)].getStartTime()-newV[newShip.getBerth()][order.get(i)].getArrivalTime();
				tempDura = tempWait+V[newShip.getBerth()][order.get(i)].getHandlingTime();
				endTime = tempStart + V[newShip.getBerth()][order.get(i)].getHandlingTime();
				newV[newShip.getBerth()][order.get(i)].setWaitingTime(tempWait);
				newV[newShip.getBerth()][order.get(i)].setDuration(tempDura);
				newV[newShip.getBerth()][order.get(i)].setEndTime(endTime);
			}
			
			if(i > 0) {
				tempStart = V[newShip.getBerth()][order.get(i)].getArrivalTime();
				if (tempStart < V[newShip.getBerth()][order.get(i-1)].getEndTime()) {
					tempStart = V[newShip.getBerth()][order.get(i-1)].getEndTime();
				} else if(tempStart < V[newShip.getBerth()][order.get(i)].getStartBerth()) {
					tempStart = V[newShip.getBerth()][order.get(i)].getStartBerth();
				}
				newV[newShip.getBerth()][order.get(i)].setStartTime(tempStart);
				tempWait = newV[newShip.getBerth()][order.get(i)].getStartTime()-newV[newShip.getBerth()][order.get(i)].getArrivalTime();
				tempDura = tempWait+V[newShip.getBerth()][order.get(i)].getHandlingTime();
				endTime = tempStart + V[newShip.getBerth()][order.get(i)].getHandlingTime();
				newV[newShip.getBerth()][order.get(i)].setWaitingTime(tempWait);
				newV[newShip.getBerth()][order.get(i)].setDuration(tempDura);
				newV[newShip.getBerth()][order.get(i)].setEndTime(endTime);	
			}
			
			
		}
		
		
		
		
		
		return newV;
	}

}
