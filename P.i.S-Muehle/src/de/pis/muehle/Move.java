package de.pis.muehle;

public record Move(int from,int to) {
	public Move {
		if(!(from >= -1 && from <= 23) && (to >= 0 && to<=23))throw new IllegalArgumentException("From -1-23 and To 0-23");
	}
}
