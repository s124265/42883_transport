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
	Ship [][] bestSolution;
	
	
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
		//System.out.print(Obj);
	}
	
	public Ship[][] getSolve() {return bestSolution;}
		
	public double solve(Ship[][] V,boolean [][] ShipService, double [] avgWeight) {
		double Obj = 0;
		double newcost = costObj(V,avgWeight);
		StopWatch watch = new StopWatch(); 
		watch.start();
		//double elapsedTime = 0;
		int j = 0;
		while (j<=1000) {
			Ship [][] S = new Ship [V.length][V[0].length];
			S = Step(V,avgWeight);
			if (newcost > costObj(S,avgWeight)) {
				V = S;
				newcost = costObj(V,avgWeight);
				System.out.println(newcost);
			}
			//elapsedTime = watch.lap();
			j++;
		}
		Obj = newcost;
		bestSolution = V;
		System.out.println("Best solution: " + Obj);
			
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
	
	public boolean terminate (double elapsedTime) {
		if (elapsedTime >= 10) {
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
		//System.out.println("HEJ");
		//System.out.println(bestcost);
		return best;
	}
	
	public ArrayList<Ship[][]> mapNeighbours(Ship[][] V) {
		ArrayList<Ship[][]> N = new ArrayList<Ship[][]>();
		Ship tempShip = new Ship();
		int numRand = V[0].length;
		ArrayList <Integer> usedShips = new ArrayList <Integer>();
		
		for(int k = 0; k<numRand;k++) {
			int maxHand = 0;
			for(int j = 0; j<V.length;j++) {
				for(int i = 0; i<V[0].length;i++) {
					if(V[j][i].getDuration()<=0) {
						continue;
					}
					if (maxHand<V[j][i].getHandlingTime() && usedShips.contains(V[j][i].getId()) == false) {
						maxHand = V[j][i].getHandlingTime();
						tempShip = V[j][i];
					}
				}
			}
			usedShips.add(tempShip.getId());
			//System.out.println(usedShips);
			//System.out.println(usedShips.contains(V[tempShip.getBerth()][tempShip.getId()].getId()));
			int besthand = 10000000;
			int bestBerth = -1;
			for(int j = 0; j<V.length;j++) {
				if(besthand > V[j][tempShip.getId()].getHandlingTime() && j != tempShip.getBerth() && V[j][tempShip.getId()].getHandlingTime() != 0) {
					besthand = V[j][tempShip.getId()].getHandlingTime();
					bestBerth = j;
				}
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
		rand.setSeed(12345);
		
		int randShip = rand.nextInt(V[0].length);
		
		while(V[bestBerth][randShip].getDuration()<= 0 && V[tempShip.getBerth()][randShip].getHandlingTime()>0) {
			randShip = rand.nextInt(V[0].length);
		}
		//System.out.println(V[0].length);
		//System.out.println(randBerth + " " + randShip);
		//System.out.println(tempShip.getId() + " " + tempShip.getBerth());
		
		Ship swapShip = V[bestBerth][randShip];
		
		
		Ship newShip = V[bestBerth][tempShip.getId()];
		
		swapShip = V[tempShip.getBerth()][randShip];
		
		newV[tempShip.getBerth()][tempShip.getId()].setDuration(0);
		newV[tempShip.getBerth()][tempShip.getId()].setWaitingTime(0);
		newV[tempShip.getBerth()][tempShip.getId()].setStartTime(0);
		newV[tempShip.getBerth()][tempShip.getId()].setEndTime(0);
		newV[bestBerth][randShip].setDuration(0);
		newV[bestBerth][randShip].setWaitingTime(0);
		newV[bestBerth][randShip].setStartTime(0);
		newV[bestBerth][randShip].setEndTime(0);
			
		newV = calcnewV(newV,newShip);
		newV = calcnewV(newV,swapShip);
		
		
		
		return newV;
		
	}
	
	public Ship [][] calcnewV(Ship[][]V, Ship newShip) {
		Ship [][] newV = new Ship[V.length][V[0].length];
		newV = V;
		boolean [] usedShips = new boolean [V[0].length];
		ArrayList <Integer> order = new ArrayList <Integer>();
		int arrTime = newShip.getArrivalTime();
		
		int j = 0;
		while (j < 10) {
			int minA = 100000000;
			int index = -1;
			for (int i= 0; i<V[0].length;i++) {
				if(V[newShip.getBerth()][i].getArrivalTime()< minA && usedShips[i]==false && V[newShip.getBerth()][i].getDuration()>0) {
					minA = V[newShip.getBerth()][i].getArrivalTime();
					if (minA > arrTime && usedShips[newShip.getId()] == false) {
						minA = arrTime;
						index = newShip.getId();
						j = 0;
					} else {
					index = V[newShip.getBerth()][i].getId();
					j = 0;
					}
				}
			}
			j++;
			if(index > -1) {
			order.add(index);
			usedShips[index] = true;
			}		
		}
		if(usedShips[newShip.getId()] == false) {
			order.add(newShip.getId());
		}
		//System.out.println("Berth nr: " + newShip.getBerth() + " " + order.size());
		//System.out.println(arrTime);
		/*for(int i = 0; i< order.size();i++) {
			System.out.print(newV[newShip.getBerth()][order.get(i)].getArrivalTime()+ " ");
		}
		System.out.println();*/
		//System.out.println(order);
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
				if (tempWait < 0)
					tempWait = 0;
				tempDura = tempWait+V[newShip.getBerth()][order.get(i)].getHandlingTime();
				endTime = tempStart + V[newShip.getBerth()][order.get(i)].getHandlingTime();
				newV[newShip.getBerth()][order.get(i)].setWaitingTime(tempWait);
				newV[newShip.getBerth()][order.get(i)].setDuration(tempDura);
				newV[newShip.getBerth()][order.get(i)].setEndTime(endTime);
			}
			
			if(i > 0) {
				tempStart = V[newShip.getBerth()][order.get(i)].getArrivalTime();
				//System.out.println(tempStart + " " + V[newShip.getBerth()][order.get(i-1)].getEndTime());
				if (tempStart < V[newShip.getBerth()][order.get(i-1)].getEndTime()) {
					tempStart = V[newShip.getBerth()][order.get(i-1)].getEndTime();
					//System.out.println("Hej1");
				}
				if(tempStart < V[newShip.getBerth()][order.get(i)].getStartBerth()) {
					tempStart = V[newShip.getBerth()][order.get(i)].getStartBerth();
					//System.out.print("Hej2");
				}
				tempWait = tempStart-newV[newShip.getBerth()][order.get(i)].getArrivalTime();
				if (tempWait < 0)
					tempWait = 0;
				tempDura = tempWait+V[newShip.getBerth()][order.get(i)].getHandlingTime();
				endTime = tempStart + V[newShip.getBerth()][order.get(i)].getHandlingTime();
				newV[newShip.getBerth()][order.get(i)].setStartTime(tempStart);
				newV[newShip.getBerth()][order.get(i)].setWaitingTime(tempWait);
				newV[newShip.getBerth()][order.get(i)].setDuration(tempDura);
				newV[newShip.getBerth()][order.get(i)].setEndTime(endTime);	
			}
			
			
		}
	int count = 0;	
	for(int n =0; n<newV.length;n++) {
		for(int i = 0;i< newV[0].length;i++) {
			//System.out.print(newV[n][i].getStartTime() + " ");
			if(newV[n][i].getDuration()> 0) {
				count += 1;
			}
		}
		//System.out.println();
	}
		//System.out.println(count);
		
		return newV;
	}

}
