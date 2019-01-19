
class HC {
	boolean [][] ShipService;
	
	public HC(Ship [][] V) {
		boolean [][] InitService = new boolean [V.length][V[0].length];
		for(int j=0; j<V.length;j++) {
			for(int i=0;i<V[0].length;i++) {
				if(V[j][i].getDuration()>0) {
					InitService[j][i] = true;
				}
			}
		}
		
		
		
	}

}
