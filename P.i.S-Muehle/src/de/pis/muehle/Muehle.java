package de.pis.muehle;

public class Muehle implements IMuehle{
		
	private int[] board;
	public Muehle() {
		this.board = new int[24];
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

			public boolean isGameOver() {
				// TODO Auto-generated method stub
				return false;
			}
			
		    private enum Phase {
		        SETTING,
		        MOVING,
		        END
		    }
}
