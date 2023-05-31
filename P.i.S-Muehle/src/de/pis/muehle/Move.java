package de.pis.muehle;

public class Move {

	private int from;
	private int to;

	public Move(int from, int to) {
		setFrom(from);
		setTo(to);
	}

	private void setFrom(int from) {
		assert from <= 24 && from>=0;
		this.from = from;
	}
	private void setTo(int to) {
		assert to <= 24 && to>=0;
		this.to = to;
	}
	
	public int getFrom() {
		return from;
	}

	public int getTo() {
		return to;
	}

	static Move of(int from, int to) {
		return new Move(from, to);
	}

}
