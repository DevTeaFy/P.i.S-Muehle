package de.pis.muehle;

import java.util.List;

public interface IMuehle {
	
	IMuehle play(Move move);
	Move generatePerfactMove();
	List<Move> listMoves();
	
	Player getCurrentPlayer();
	
	boolean isPossibleMove(Move m);
	boolean isGameOver();
	boolean isMillFormed(int position);
	
	int[] getBoard();

	void switchPlayer();
}
