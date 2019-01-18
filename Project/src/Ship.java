
class Ship {
	private int arrivalTime; //Arrival time of ship
	private int handlingTime; //Handling time of ship in berth j
	private int startTime = 0; //Start time of the ship handling in a berth
	private int endTime = 0; //End time of the ship in berth.
	private int waitingTime;
	private int duration; //The duration of the operation
	private int id; //id of ship
	private int berth; //Number of berth
	private int startBerth; //Starting time for berth j
	private int endBerth; //ending time for berth j
	
	public Ship (int berth, int id, int handlingTime) {
		this.berth = berth;
		this.id = id;
		this.handlingTime = handlingTime;
	}
	public Ship () {
		berth = -1;
		id = -1;
		handlingTime = 1000000;
	}
	
	public int getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public int getHandlingTime() {
		return handlingTime;
	}
	public void setHandlingTime(int handlingTime) {
		this.handlingTime = handlingTime;
	}
	public int getStartTime() {
		if (startTime<0 ) throw new RuntimeException("Start time cannot be negative, please reconsider");
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	public int getEndTime() {
		return endTime;
	}
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	
	public int getEnd() {
		return startTime + duration;
	}
	
	public void setEnd(int end) {
		int st = end-duration;
		//Throw en exception?
		startTime = st;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBerth() {
		return berth;
	}

	public void setBerth(int berth) {
		this.berth = berth;
	}

	public int getStartBerth() {
		return startBerth;
	}

	public void setStartBerth(int startBerth) {
		this.startBerth = startBerth;
	}

	public int getEndBerth() {
		return endBerth;
	}

	public void setEndBerth(int endBerth) {
		this.endBerth = endBerth;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getWaitingTime() {
		return waitingTime;
	}
	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}

	
	
	
}
