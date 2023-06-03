package de.pis.muehle;

import java.util.List;

public interface IMuehle {
	
	IMuehle play(Move move);
	
	void undoMove(Move move);
	
	Move generatePerfactMove();
	boolean isGameOver();
	List<Move> listMoves();
	int[] getBoard();

}
