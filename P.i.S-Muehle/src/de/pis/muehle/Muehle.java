package de.pis.muehle;

import java.util.ArrayList;
import java.util.List;

public class Muehle implements IMuehle {
	
	private int[] board;
	private List<int[]> boardstates = new ArrayList<>();
	private Player currentPlayer;
	private Player PlayerOne = new Player(-1),PlayerTwo = new Player(1);
	private int[][] mills = {
	        {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
	        {0, 9, 21}, {3, 10, 18}, {6, 11, 15},
	        {1, 10, 19}, {4, 11, 16}, {7, 12, 13},
	        {2, 11, 20}, {5, 12, 17}, {8, 13, 14},
	        {9, 10, 11}, {12, 13, 14}, {15, 16, 17},
	        {18, 19, 20}, {21, 22, 23}
	        };
	
	
	public Muehle() {
		this.board = new int[24];
		this.currentPlayer = PlayerOne;
	}
	private Muehle(int[] board,Player currentPlayer) {
		this.board = board;
		this.currentPlayer = currentPlayer;
	}
	
	private void switchPlayer() {
		currentPlayer = getOpponentPlayer();
	}
	private Player getOpponentPlayer() {
		return currentPlayer.getValue() == -1 ? PlayerTwo : PlayerOne;
	}
	
	
	public IMuehle play(Move move) {
	    Muehle newState = new Muehle(board.clone(), currentPlayer);
	    if (newState.isPossibleMove(currentPlayer, move)) {
	        switch (newState.currentPlayer.getPhase()) {
	            case SETTING:
	            	newState.board[move.to()] = newState.currentPlayer.getValue();
	            	newState.currentPlayer.removeStoneToPlace();
	        	    if(newState.currentPlayer.getPlacedStones() == 9) {
	        	    	newState.currentPlayer.setPhase(Phase.MOVING);
	        	    }
	                break;
	            case MOVING: case END:
	            	newState.board[move.from()] = 0;
	            	newState.board[move.to()] = newState.currentPlayer.getValue();
	                break;
	        }
		    if (newState.isMillFormed(move.to())) {
		    	
		    }
	        newState.switchPlayer();
	        if(isGameOver()) {
	        	
	        }
	    }
	    
	    return newState;
	}
	
	private boolean isMillFormed(int position) {
	    int playerValue = currentPlayer.getValue();
	    for (int[] mill : mills) {
	        if (board[mill[0]] == playerValue &&
	            board[mill[1]] == playerValue &&
	            board[mill[2]] == playerValue && 
	            (mill[0] == position || mill[1] == position || mill[2] == position)) {
	            return true;
	        }
	    }
	    return false;
	}

	public void undoMove(Move move) {
		board[move.to()] = 0;
		board[move.from()] = currentPlayer.getValue();
	}

	public Move generatePerfactMove() {
	    int depth = 5; // Die Suchtiefe des Algorithmus
	    int alpha = Integer.MIN_VALUE;
	    int beta = Integer.MAX_VALUE;
	    int bestValue = Integer.MIN_VALUE;
	    Move bestMove = null;
	    
	    List<Move> possibleMoves = listMoves();
	    
	    for (Move move : possibleMoves) {
	        IMuehle nextState = play(move);
	        int value = miniMax(nextState, depth - 1, alpha, beta, false);
	        
	        if (value > bestValue) {
	            bestValue = value;
	            bestMove = move;
	        }
	        
	        undoMove(move);
	    }
	    
	    return bestMove;
	}

	private int miniMax(IMuehle state, int depth, int alpha, int beta, boolean isMaximizingPlayer) {
	    if (depth == 0 || state.isGameOver()) {
	        return evaluateState(state);
	    }
	    List<Move> possibleMoves = state.listMoves();
	    int wert = isMaximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (Move move : possibleMoves) {
            IMuehle nextState = state.play(move);
            int eval = miniMax(nextState, depth - 1, alpha, beta, !isMaximizingPlayer);
            wert = isMaximizingPlayer ? Math.max(wert, eval) : Math.min(wert, eval);
            alpha = isMaximizingPlayer ? Math.max(alpha, eval) : Math.min(beta, eval);
            
            if (isMaximizingPlayer && beta <= alpha) {
                break; // Beta-Cutoff
            }
            if (!isMaximizingPlayer && beta <= alpha) {
                break; // Alpha-Cutoff
            }
            state.undoMove(move);
        }
        return wert;
	}

	private int evaluateState(IMuehle state) {
		int currentPlayerValue = currentPlayer.getValue();
		int opponentPlayerValue = -currentPlayerValue;

		// Überprüfen, ob das Spiel vorbei ist
		if (state.isGameOver()) {
			if (currentPlayerValue == 1) {
				return Integer.MIN_VALUE; // Gegner hat gewonnen
			} else {
				return Integer.MAX_VALUE; // Aktueller Spieler hat gewonnen
			}
		}

		// Zähler für die Anzahl der offenen Mühlen
		int currentPlayerMills = 0;
		int opponentPlayerMills = 0;

		// Zähler für die Anzahl der möglichen Mühlen
		int currentPlayerPossibleMills = 0;
		int opponentPlayerPossibleMills = 0;

		// Überprüfen, ob jede Position auf dem Spielbrett eine Mühle für den aktuellen
		// Spieler bildet
		for (int i = 0; i < 24; i++) {
			if (state.getBoard()[i] == currentPlayerValue) {
				currentPlayerMills += countMills(state, currentPlayer);
				currentPlayerPossibleMills += countPossibleMills(state, i, currentPlayer);
			} else if (state.getBoard()[i] == opponentPlayerValue) {
				opponentPlayerMills += countMills(state, currentPlayer);
				opponentPlayerPossibleMills += countPossibleMills(state, i, currentPlayer);
			}
		}

		// Berechnen der Bewertung basierend auf den Ergebnissen
		int score = currentPlayerMills - opponentPlayerMills;
		score += currentPlayerPossibleMills - opponentPlayerPossibleMills;

		return score;
	}

	private int countPossibleMills(IMuehle state, int position, Player player) {
	    int count = 0;
	    int[] board = state.getBoard();
	    
	    if (board[position] == 0) {
	        board[position] = player.getValue();
	        if (countMills(state, player) > 0) {
	            count++;
	        }
	        board[position] = 0;
	    }
	    
	    return count;
	}
   

	@Override
	public boolean isGameOver() {
		if(listMoves().size() == 0)return true;
		if(!currentPlayer.hasStonesToCreateMill())return true;
		if(!getOpponentPlayer().hasStonesToCreateMill())return true;
		//Wenn 3 mal das selbe board auch!
		return false;
	}
	private int countMills(IMuehle state, Player player) {
	    
	    
	    int millCount = 0;
	    
	    for (int[] mill : mills) {
	        if (isMill(state, player, mill)) {
	            millCount++;
	        }
	    }
	    
	    return millCount;
	}

	private boolean isMill(IMuehle state, Player player, int[] mill) {
	    int[] board = state.getBoard();
	    for (int position : mill) {
	        if (board[position] != player.getValue()) {
	            return false;
	        }
	    }
	    return true;
	}
	public List<Move> listMoves(){
		ArrayList<Move> moves = new ArrayList<>();
		switch (currentPlayer.getPhase()) {
		case SETTING: {
			for (int j = 0; j < 24; j++) {
				Move m = new Move(-1,j);
				if(isPossibleMove(currentPlayer,m))moves.add(m);
			}
			return moves;
		}
		case MOVING:{
			for(int i = 0; i < 24; i++) {
				for (int j = 0; j < 24; j++) {
					Move m = new Move(i,j);
					if(isPossibleMove(currentPlayer,m))moves.add(m);
				}
			}
		}
		case END:{
			for(int i = 0; i < 24; i++) {
				for (int j = 0; j < 24; j++) {
					Move m = new Move(i,j);
					if(isPossibleMove(currentPlayer,m))moves.add(m);
				}
			}
			return moves;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + currentPlayer.getPhase());
		}
	}
	
	
	private boolean isPossibleMove(Player p,Move m) {
		switch (p.getPhase()) {
		case SETTING: case END:{
			if(board[m.to()] == 0)return true;
			return false;
		}
		case MOVING:{
			if(board[m.to()] != 0) return false;
			if(!areNeighbors(m.from(),m.to()))return false;
			return true;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + p.getPhase());
		}
		
	} 

	private boolean areNeighbors(int from, int to) {
		if(from == -1 && currentPlayer.getPhase() == Phase.SETTING)return true;
		assert (from >= 0 && from <= 23) && (to >= 0 && to<=23); 
		if (inACircle(from, to) && (from + 1 == to || from - 1 == to)) return true; // Normales +1 auf einem Ring oder -1 auf einem Ring
		if (inACircle(from, to) && ((((from + 1) / 8 > to / 8) && (from - 7 == to)) || (((from - 1) / 8 < to / 8) && (from + 7 == to)) || ((from - 1 == -1)))) 
			return true; // // Kreise überlaufen und fangen von vorne an mit Sonderfall 0-1 == -1 ist ein nachbar für From 0 zu To 7
		if (!inACircle(from, to) && ((from % 2 != 0) && (from + 8 == to || from - 8 == to))) return true; // Horizontale und Vertikale Ring switches.
		return false;
	}

	private boolean inACircle(int from, int to) {
		assert (from >= 0 && from <= 23) && (to >= 0 && to<=23); 
		return ((from / 8) == (to / 8));
	}
	
	
	@Override
	public String toString() {
		String s = "\n"+
				   "(0)------(1)------(2)\n"
				  +"|      |      |\n"
				  +"| (8)----(9)----(10) |\n"
				  +"| |    |    | |\n"
				  +"| | (16)--(17)--(18) | |\n"
				  +"| | |     | | |\n"
				  +"(7)-(15)-(23)     (19)-(11)-(3)\n"
				  +"| | |     | | |\n"
				  +"| | (22)--(21)--(20) | |\n"
				  +"| |    |    | |\n"
				  +"| (14)----(13)----(12) |\n"
				  +"(6)------(5)------(4)";
		for (int i = 0; i < board.length; i++) {
			if(board[i]==0) {
				s = s.replace("("+i+")", "O");
			}else {
				s = s.replace("("+i+")", board[i]==-1 ? "W":"B");
			}
		}
		return s;
	}
	@Override
	public int[] getBoard() {
		final int[] b = this.board;
		return b;
	}
}
