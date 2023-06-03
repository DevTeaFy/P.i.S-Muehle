package de.pis.muehle;

public class Player {
	
	private int placedStones,stonesToPlace;
	private boolean endPhase;
	private int value;
	private Phase phase;	
	
	public Player(int value) {
		stonesToPlace = 9;
		placedStones = 0;
		endPhase= false;
		this.value = value;
		this.phase = Phase.SETTING;
	}
	
	public Player(int placedStones,int stonesToPlace, boolean endPhase,int value,Phase phase) {
		assert placedStones <= 9 && placedStones >= 0;
		assert stonesToPlace <= 9 && stonesToPlace >= 0;
		this.placedStones = placedStones;
		this.stonesToPlace = stonesToPlace;
		this.endPhase = endPhase;
		this.value = value;
		this.phase = phase;
	}
	public int getPlacedStones() {
		return placedStones;
	}
	private void addPlacedStone() {
		this.placedStones++;
	}
	public void removeActiveStone() {
		this.placedStones--;
	}
	
	public boolean hasStonesToCreateMill() {
		return placedStones >= 3;
	}
	
	public boolean hasStonesToPlaceLeft() {
		return stonesToPlace >= 1;
	}
	
	public void removeStoneToPlace() {
		this.stonesToPlace--;
		addPlacedStone();
	}
	
	public boolean isInEndPhase() {
		return endPhase;
	}
	
	public Phase getPhase() {
		return phase;
	}
	public void setPhase(Phase phase) {
		this.phase = phase;
	}
	public int getValue() {
		return value;
	}
	
}
