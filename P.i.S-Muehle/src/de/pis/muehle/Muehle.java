package de.pis.muehle;

import java.util.ArrayList;
import java.util.List;

public class Muehle implements IMuehle{
	
	private int[] board;
	private Phase currentPhase;
	private List<int[]> boardstates = new ArrayList<>();
	
	
	public Muehle() {
		this.board = new int[24];
	}
	private Muehle(int[] board) {
		this.board = board;
	}
	
	
	
	
	public IMuehle play(Move move) {
		
		// TODO Auto-generated method stub
		return null;
	}

	public void undoMove(Move move) {
		// TODO Auto-generated method stub
		
	}

	public Move generatePerfactMove() {
		// TODO Auto-generated method stub
		return null;
	}
	
    private enum Phase {
        SETTING,
        MOVING,
        END
    }

	@Override
	public boolean isGameOver() {
		// TODO Auto-generated method stub
		return false;
	}
	private boolean isPossibleMove(Move m) {
		if(board[m.to()] != 0)return false;
		if(sindNachbarn(m.from(),m.to())) {
			
		}
	}
	private boolean sindNachbarn(int from, int to) {
		if(inACircle(from, to) && (from+1 == to || from-1 == to))return true;
		if(inACircle(from, to) && (((from+1/8 > to/8) &&(from-7 == to)) || ((from-1/8 < to/8) && (from+7 == to)) || ((from-1 == -1) && (from+7==to)))) return true;
		if(!inACircle(from, to) && ((from %2 != 0) && (from+8 == to || from-8 == to)))return true;
		return false;
	}
	private boolean inACircle(int from ,int to) {
		return from/8 == to/8;
	}
	
}

record Move(int from,int to) {
	public Move{
		assert from >= 0 && to >= 0;
	}
}