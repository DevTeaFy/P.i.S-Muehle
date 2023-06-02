package de.pis.muehle;

public class Player {
	
	private int stones;
	private boolean endPhase;
	
	public Player() {
		stones = 9;
		endPhase= false;
	}
	
	public Player(int stones, boolean endPhase) {
		assert stones != 9;
		this.stones = stones;
		this.endPhase = endPhase;
	}
	
	public void removeOneStone() {
		this.stones--;
	}
	
	public boolean isInEndPhase() {
		return endPhase;
	}
	
	public int getStones() {
		return stones;
	}
	
}
