
import java.util.ArrayList;

import javax.swing.JOptionPane;


//has rules for top pieces programmed in
public class ComputerRuleCheck {

	private int valueAdded;
	private Piece pieceRemoved;
	
	private Piece rightRook;
	private Piece leftRook;
	
	public boolean checkPlayerPossibilities(Piece p, Square movedTo, Piece rightRook, Piece leftRook) {

		valueAdded = 0;

		this.rightRook = rightRook;
		this.leftRook = leftRook;
		
		if (!GamePanel.userColor.equalsIgnoreCase("White")) {

			if (p.getSource() == "White Pawn.png") {
				return pawn(movedTo, p);
			} else if (p.getSource() == "White Rook.png") {
				return rook(movedTo, p);
			} else if (p.getSource() == "White Knight.png") {
				return knight(movedTo, p);
			} else if (p.getSource() == "White Bishop.png") {
				return bishop(movedTo, p);
			} else if (p.getSource() == "White Queen.png") {
				return queen(movedTo, p);
			} else if (p.getSource() == "White King.png") {
				return king(movedTo, p);
			}

		} else if (!GamePanel.userColor.equalsIgnoreCase("Black")) {
			if (p.getSource() == "Black Pawn.png") {
				return pawn(movedTo, p);
			} else if (p.getSource() == "Black Rook.png") {
				return rook(movedTo, p);
			} else if (p.getSource() == "Black Knight.png") {
				return knight(movedTo, p);
			} else if (p.getSource() == "Black Bishop.png") {
				return bishop(movedTo, p);
			} else if (p.getSource() == "Black Queen.png") {
				return queen(movedTo, p);
			} else if (p.getSource() == "Black King.png") {
				return king(movedTo, p);
			}

		}
		return false;
	}

	public boolean pawn(Square movedTo, Piece p) {

		boolean willReturn;

		int numIdxAbove = p.getSquare().getIIndex() - movedTo.getIIndex();
		if (movedTo.getJIndex() == p.getSquare().getJIndex()) {
			if (numIdxAbove == -2 && p.pawnFirst() == false && p.getSquare().getIIndex() == 1) {
				if (null == canMove(getBelow(numIdxAbove, p))) {
					p.setPawnFirst(true);
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove == -1) {
				if (null == canMove(getBelow(numIdxAbove, p))) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}

		} else {

			// means you may try to attack another piece diagonally

			if (movedTo.hasPlayer() == true && movedTo.hasPlayer()
					&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
				if (movedTo.getJIndex() == p.getSquare().getJIndex() + 1
						&& movedTo.getIIndex() == p.getSquare().getIIndex() + 1) {
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
					return true;
				} else if (movedTo.getJIndex() == p.getSquare().getJIndex() - 1
						&& movedTo.getIIndex() == p.getSquare().getIIndex() + 1) {
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
					return true;
				} else {
					return false;
				}

			} else {
				willReturn = false;
			}

		}

		// checks if pawn reached end and swaps it to queen if it has been to end

		if (p.getSquare().getIIndex() == 7) {
			if (!GamePanel.userColor.equalsIgnoreCase("White")) {
				for (int i = 0; i < GamePanel.whitePieces.length; i++) {
					if (GamePanel.whitePieces[i].getSource() == "White Queen.png") {
						p.setSource("White Queen.png");
						p.setBufferedImage(GamePanel.whitePieces[i].getImage());
						p.setValue(GamePanel.whitePieces[i].getValue());
						valueAdded += p.getValue();
						break;
					}
				}
				for (int i = 0; i < GamePanel.piecesUserTook.size(); i++) {
					if (GamePanel.piecesUserTook.get(i).getSource() == "White Queen.png") {
						p.setSource("White Queen.png");
						p.setBufferedImage(GamePanel.whitePieces[i].getImage());
						p.setValue(GamePanel.whitePieces[i].getValue());
						valueAdded += p.getValue();
						break;
					}
				}

				// check pieces user took if things r actually added to it
			} else if (GamePanel.userColor.equalsIgnoreCase("Black")) {
				for (int i = 0; i < GamePanel.blackPieces.length; i++) {
					if (GamePanel.blackPieces[i].getSource() == "Black Queen.png") {
						p.setSource("Black Queen.png");
						p.setBufferedImage(GamePanel.blackPieces[i].getImage());
						p.setValue(GamePanel.blackPieces[i].getValue());
						valueAdded += p.getValue();
						break;
					}
				}
				for (int i = 0; i < GamePanel.piecesUserTook.size(); i++) {
					if (GamePanel.piecesUserTook.get(i).getSource() == "Black Queen.png") {
						p.setSource("Black Queen.png");
						p.setBufferedImage(GamePanel.piecesUserTook.get(i).getImage());
						p.setValue(GamePanel.blackPieces[i].getValue());
						valueAdded += p.getValue();
						break;
					}
				}
			}

		}

		return willReturn;

	}

	public boolean rook(Square movedTo, Piece p) {

		int numIdxAbove = p.getSquare().getIIndex() - movedTo.getIIndex();
		int numIdxSide = p.getSquare().getJIndex() - movedTo.getJIndex();

		if (movedTo.getJIndex() == p.getSquare().getJIndex()) {
			if (numIdxAbove >= 1) {
				if (null == canMove(getAbove(numIdxAbove, p))) {
					p.setRookFirst(true);
					return true;
				} else if (movedTo == canMove(getAbove(numIdxAbove, p))
						&& movedTo.getPiece() == checkIfOnlyLast(getAbove(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded += movedTo.getPiece().getValue();
					p.setRookFirst(true);
					return true;

				} else {
					return false;
				}
			} else if (numIdxAbove <= -1) {
				if (null == canMove(getBelow(numIdxAbove, p))) {
					p.setRookFirst(true);
					return true;
				} else if (movedTo == canMove(getBelow(numIdxAbove, p))
						&& movedTo.getPiece() == checkIfOnlyLast(getBelow(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded += movedTo.getPiece().getValue();
					p.setRookFirst(true);
					return true;
				} else {
					return false;
				}
			}
		} else if (movedTo.getIIndex() == p.getSquare().getIIndex()) {
			if (numIdxSide >= 1) {
				if (null == canMove(getLeftSquares(numIdxSide, p))) {
					p.setRookFirst(true);
					return true;
				} else if (movedTo == canMove(getLeftSquares(numIdxSide, p))
						&& movedTo.getPiece() == checkIfOnlyLast(getLeftSquares(numIdxSide, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded += movedTo.getPiece().getValue();
					p.setRookFirst(true);
					return true;
				} else {
					return false;
				}
			} else if (numIdxSide <= -1) {

				if (null == canMove(getRightSquares(numIdxSide, p))) {
					p.setRookFirst(true);
					return true;
				} else if (movedTo == canMove(getRightSquares(numIdxSide, p))
						&& movedTo.getPiece() == checkIfOnlyLast(getRightSquares(numIdxSide, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded += movedTo.getPiece().getValue();
					p.setRookFirst(true);
					return true;
				} else {
					return false;
				}
			}

		} else {
			return false;
		}

		return false;
	}

	public boolean knight(Square movedTo, Piece p) {

		if (p.getSquare().getIIndex() - 2 == movedTo.getIIndex()
				&& p.getSquare().getJIndex() - 1 == movedTo.getJIndex()) {
			if (null == canMoveSingle(movedTo)) {
				if (movedTo.hasPlayer()) {
					pieceRemoved = movedTo.getPiece();
					valueAdded += movedTo.getPiece().getValue();
				}
				return true;

			} else {
				return false;
			}
		} else if (p.getSquare().getIIndex() - 1 == movedTo.getIIndex()
				&& p.getSquare().getJIndex() - 2 == movedTo.getJIndex()) {
			if (null == canMoveSingle(movedTo)) {
				if (movedTo.hasPlayer()) {
					pieceRemoved = movedTo.getPiece();
					valueAdded += movedTo.getPiece().getValue();
				}
				return true;
			} else {
				return false;
			}
		} else if (p.getSquare().getIIndex() + 1 == movedTo.getIIndex()
				&& p.getSquare().getJIndex() - 2 == movedTo.getJIndex()) {
			if (null == canMoveSingle(movedTo)) {
				if (movedTo.hasPlayer()) {
					pieceRemoved = movedTo.getPiece();
					valueAdded += movedTo.getPiece().getValue();
				}
				return true;
			} else {
				return false;
			}
		} else if (p.getSquare().getIIndex() + 2 == movedTo.getIIndex()
				&& p.getSquare().getJIndex() - 1 == movedTo.getJIndex()) {
			if (null == canMoveSingle(movedTo)) {
				if (movedTo.hasPlayer()) {
					pieceRemoved = movedTo.getPiece();
					valueAdded += movedTo.getPiece().getValue();
				}
				return true;
			} else {
				return false;
			}
		} else if (p.getSquare().getIIndex() + 2 == movedTo.getIIndex()
				&& p.getSquare().getJIndex() + 1 == movedTo.getJIndex()) {
			if (null == canMoveSingle(movedTo)) {
				if (movedTo.hasPlayer()) {
					pieceRemoved = movedTo.getPiece();
					valueAdded += movedTo.getPiece().getValue();
				}
				return true;
			} else {
				return false;
			}
		} else if (p.getSquare().getIIndex() + 1 == movedTo.getIIndex()
				&& p.getSquare().getJIndex() + 2 == movedTo.getJIndex()) {
			if (null == canMoveSingle(movedTo)) {
				if (movedTo.hasPlayer()) {
					pieceRemoved = movedTo.getPiece();
					valueAdded += movedTo.getPiece().getValue();
				}
				return true;
			} else {
				return false;
			}
		} else if (p.getSquare().getIIndex() - 1 == movedTo.getIIndex()
				&& p.getSquare().getJIndex() + 2 == movedTo.getJIndex()) {
			if (null == canMoveSingle(movedTo)) {
				if (movedTo.hasPlayer()) {
					pieceRemoved = movedTo.getPiece();
					valueAdded += movedTo.getPiece().getValue();
				}
				return true;
			} else {
				return false;
			}
		} else if (p.getSquare().getIIndex() - 2 == movedTo.getIIndex()
				&& p.getSquare().getJIndex() + 1 == movedTo.getJIndex()) {
			if (null == canMoveSingle(movedTo)) {
				if (movedTo.hasPlayer()) {
					pieceRemoved = movedTo.getPiece();
					valueAdded+=movedTo.getPiece().getValue();
				}
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	public boolean bishop(Square movedTo, Piece p) {
		// if >1, moving up, if <1, moving down
		int numIdxAbove = p.getSquare().getIIndex() - movedTo.getIIndex();
		if (movedTo.getJIndex() != p.getSquare().getJIndex()
				&& Math.abs(p.getSquare().getIIndex() - movedTo.getIIndex()) == Math
						.abs(p.getSquare().getJIndex() - movedTo.getJIndex())) {
			if (numIdxAbove >= 1 && movedTo.getJIndex() < p.getSquare().getJIndex()) {
				if (null == canMove(getUpLeftDiagonal(numIdxAbove, p))) {
					return true;
				} else if (movedTo == canMove(getUpLeftDiagonal(numIdxAbove, p))
						&& movedTo.getPiece() == checkIfOnlyLast(getUpLeftDiagonal(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded+=movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove >= 1 && movedTo.getJIndex() > p.getSquare().getJIndex()) {
				if (null == canMove(getUpRightDiagonal(numIdxAbove, p))) {
					return true;
				} else if (movedTo == canMove(getUpRightDiagonal(numIdxAbove, p))
						&& movedTo.getPiece() == checkIfOnlyLast(getUpRightDiagonal(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded+=movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove <= -1 && movedTo.getJIndex() < p.getSquare().getJIndex()) {
				if (null == canMove(getDownLeftDiagonal(numIdxAbove, p))) {
					return true;
				} else if (movedTo == canMove(getDownLeftDiagonal(numIdxAbove, p))
						&& movedTo.getPiece() == checkIfOnlyLast(getDownLeftDiagonal(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded+=movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove <= -1 && movedTo.getJIndex() > p.getSquare().getJIndex()) {
				if (null == canMove(getDownRightDiagonal(numIdxAbove, p))) {
					return true;
				} else if (movedTo == canMove(getDownRightDiagonal(numIdxAbove, p))
						&& movedTo.getPiece() == checkIfOnlyLast(getDownRightDiagonal(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded+=movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			}else {
				//added in this else too from origional. Shouldn't matter and eclipse is impartial if I put this here.
				return false;
			}
			
		} else {
			return false;
		}
		

	}

	public boolean queen(Square movedTo, Piece p) {
		int numIdxAbove = p.getSquare().getIIndex() - movedTo.getIIndex();
		int numIdxSide = p.getSquare().getJIndex() - movedTo.getJIndex();
		if (movedTo.getJIndex() == p.getSquare().getJIndex()) {
			if (numIdxAbove >= 1) {
				if (null == canMove(getAbove(numIdxAbove, p))) {
					return true;
				} else if (movedTo == canMove(getAbove(numIdxAbove, p)) && movedTo.getPiece() == checkIfOnlyLast(getAbove(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded+=movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove <= -1) {
				if (null == canMove(getBelow(numIdxAbove, p))) {
					return true;
				} else if (movedTo == canMove(getBelow(numIdxAbove, p)) && movedTo.getPiece() == checkIfOnlyLast(getBelow(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded+=movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			}
		} else if (movedTo.getJIndex() != p.getSquare().getJIndex()
				&& Math.abs(p.getSquare().getIIndex() - movedTo.getIIndex()) == Math
						.abs(p.getSquare().getJIndex() - movedTo.getJIndex())) {
			if (numIdxAbove >= 1 && movedTo.getJIndex() < p.getSquare().getJIndex()) {
				if (null == canMove(getUpLeftDiagonal(numIdxAbove, p))) {
					return true;
				} else if (movedTo == canMove(getUpLeftDiagonal(numIdxAbove, p))
						&& movedTo.getPiece() == checkIfOnlyLast(getUpLeftDiagonal(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded+=movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove >= 1 && movedTo.getJIndex() > p.getSquare().getJIndex()) {
				if (null == canMove(getUpRightDiagonal(numIdxAbove, p))) {
					return true;
				} else if (movedTo == canMove(getUpRightDiagonal(numIdxAbove, p))
						&& movedTo.getPiece() == checkIfOnlyLast(getUpRightDiagonal(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded+=movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove <= -1 && movedTo.getJIndex() < p.getSquare().getJIndex()) {
				if (null == canMove(getDownLeftDiagonal(numIdxAbove, p))) {
					return true;
				} else if (movedTo == canMove(getDownLeftDiagonal(numIdxAbove, p))
						&& movedTo.getPiece() == checkIfOnlyLast(getDownLeftDiagonal(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded+=movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove <= -1 && movedTo.getJIndex() > p.getSquare().getJIndex()) {
				if (null == canMove(getDownRightDiagonal(numIdxAbove, p))) {
					return true;
				} else if (movedTo == canMove(getDownRightDiagonal(numIdxAbove, p))
						&& movedTo.getPiece() == checkIfOnlyLast(getDownRightDiagonal(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded+=movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			}
		} else if (movedTo.getIIndex() == p.getSquare().getIIndex()) {
			if (numIdxSide >= 1) {
				if (null == canMove(getLeftSquares(numIdxSide, p))) {
					return true;
				} else if (movedTo == canMove(getLeftSquares(numIdxSide, p))
						&& movedTo.getPiece() == checkIfOnlyLast(getLeftSquares(numIdxSide, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded+=movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			} else if (numIdxSide <= -1) {

				if (null == canMove(getRightSquares(numIdxSide, p))) {
					return true;
				} else if (movedTo == canMove(getRightSquares(numIdxSide, p))
						&& movedTo.getPiece() == checkIfOnlyLast(getRightSquares(numIdxSide, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded+=movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			}

		} else {
			return false;
		}
		
		//made me add in.
		return false;

	}

	public boolean king(Square movedTo, Piece p) {

		int numIdxAbove = p.getSquare().getIIndex() - movedTo.getIIndex();
		int numIdxSide = p.getSquare().getJIndex() - movedTo.getJIndex();
		if (movedTo.getJIndex() == p.getSquare().getJIndex()) {
			if (numIdxAbove == 1) {
				if (null == canMove(getAbove(numIdxAbove, p))) {
					p.setKingFirst(true);
					return true;
				} else if (movedTo == canMove(getAbove(numIdxAbove, p)) && movedTo.getPiece() == checkIfOnlyLast(getAbove(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					p.setKingFirst(true);
					valueAdded+=movedTo.getPiece().getValue();
					return true;
					
				} else {
					return false;
				}
			} else if (numIdxAbove == -1) {
				if (null == canMove(getBelow(numIdxAbove, p))) {
					p.setKingFirst(true);
					return true;
				} else if (movedTo == canMove(getBelow(numIdxAbove, p)) && movedTo.getPiece() == checkIfOnlyLast(getBelow(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					p.setKingFirst(true);
					valueAdded+=movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else if (movedTo.getJIndex() != p.getSquare().getJIndex()
				&& Math.abs(p.getSquare().getIIndex() - movedTo.getIIndex()) == Math
						.abs(p.getSquare().getJIndex() - movedTo.getJIndex())) {
			if (numIdxAbove == 1 && movedTo.getJIndex() < p.getSquare().getJIndex()) {
				if (null == canMove(getUpLeftDiagonal(numIdxAbove, p))) {
					p.setKingFirst(true);
					return true;
				} else if (movedTo == canMove(getUpLeftDiagonal(numIdxAbove, p))
						&& movedTo.getPiece() == checkIfOnlyLast(getUpLeftDiagonal(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					p.setKingFirst(true);
					valueAdded+=movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove == 1 && movedTo.getJIndex() > p.getSquare().getJIndex()) {
				if (null == canMove(getUpRightDiagonal(numIdxAbove, p))) {
					p.setKingFirst(true);
					return true;
				} else if (movedTo == canMove(getUpRightDiagonal(numIdxAbove, p))
						&& movedTo.getPiece() == checkIfOnlyLast(getUpRightDiagonal(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					p.setKingFirst(true);
					valueAdded+=movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove == -1 && movedTo.getJIndex() < p.getSquare().getJIndex()) {
				if (null == canMove(getDownLeftDiagonal(numIdxAbove, p))) {
					p.setKingFirst(true);
					return true;
				} else if (movedTo == canMove(getDownLeftDiagonal(numIdxAbove, p))
						&& movedTo.getPiece() == checkIfOnlyLast(getDownLeftDiagonal(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					p.setKingFirst(true);
					valueAdded+=movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove == -1 && movedTo.getJIndex() > p.getSquare().getJIndex()) {
				if (null == canMove(getDownRightDiagonal(numIdxAbove, p))) {
					p.setKingFirst(true);
					return true;
				} else if (movedTo == canMove(getDownRightDiagonal(numIdxAbove, p))
						&& movedTo.getPiece() == checkIfOnlyLast(getDownRightDiagonal(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					p.setKingFirst(true);
					valueAdded+=movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}

		} else if (movedTo.getIIndex() == p.getSquare().getIIndex()) {

			if (numIdxSide == 1) {
				if (null == canMove(getLeftSquares(numIdxSide, p))) {
					p.setKingFirst(true);
					return true;
				} else if (movedTo == canMove(getLeftSquares(numIdxAbove, p))
						&& movedTo.getPiece() == checkIfOnlyLast(getLeftSquares(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					p.setKingFirst(true);
					valueAdded+=movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			} else if (numIdxSide == -1) {

				if (null == canMove(getRightSquares(numIdxSide, p))) {
					p.setKingFirst(true);
					return true;
				} else if (movedTo == canMove(getRightSquares(numIdxAbove, p))
						&& movedTo.getPiece() == checkIfOnlyLast(getRightSquares(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					p.setKingFirst(true);
					valueAdded+=movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
				// check rook king swap
			}
			else if (p.getSquare().getJIndex() == 4 && numIdxSide == -2) {
				if (p.kingFirst() == false && rightRook.rookFirst() == false) {
					if (null == canMove(getRightSquares(numIdxSide, p))) {
						rightRook.setRookFirst(true);
						rightRook.moveTo(rightRook.getSquare(), GamePanel.squares[0][5]);
						p.setKingFirst(true);
						return true;
					}
				} else {
					return false;
				}

			} else if (p.getSquare().getJIndex() == 4 && numIdxSide == 2) {
				if (p.kingFirst() == false && leftRook.rookFirst() == false) {
					if (null == canMove(getLeftSquares(numIdxSide, p))) {
						leftRook.setRookFirst(true);
						leftRook.moveTo(leftRook.getSquare(), GamePanel.squares[0][3]);
						p.setKingFirst(true);
						return true;
					}
				} else {
					return false;
				}

			}
			else {
				return false;
			}

		} else {
			return false;
		}
		//made me add in.
		return false;

	}

	public Square[] getRightSquares(int xRight, Piece p) {
		System.out.println(xRight);
		ArrayList<Square> squaresAbvList = new ArrayList<Square>();
		for (int i = 0; i < GamePanel.squares.length; i++) {
			for (int j = 0; j < GamePanel.squares[i].length; j++) {
				if (p.getSquare().equals(GamePanel.squares[i][j])) {
					for (int j2 = 1; j2 <= Math.abs(xRight); j2++) {
						squaresAbvList.add(GamePanel.squares[i][j + j2]);
						System.out.println("added right");
					}

				}
			}
		}
		Square[] temp = new Square[squaresAbvList.size()];
		for (int i = 0; i < squaresAbvList.size(); i++) {
			temp[i] = squaresAbvList.get(i);
		}
		return temp;

	}

	public Square[] getLeftSquares(int xLeft, Piece p) {
		ArrayList<Square> squaresAbvList = new ArrayList<Square>();
		for (int i = 0; i < GamePanel.squares.length; i++) {
			for (int j = 0; j < GamePanel.squares[i].length; j++) {
				if (p.getSquare().equals(GamePanel.squares[i][j])) {
					for (int j2 = 1; j2 <= xLeft; j2++) {
						squaresAbvList.add(GamePanel.squares[i][j - j2]);
					}

				}
			}
		}
		Square[] temp = new Square[squaresAbvList.size()];
		for (int i = 0; i < squaresAbvList.size(); i++) {
			temp[i] = squaresAbvList.get(i);
		}
		return temp;

	}

	public Square[] getAbove(int xAbove, Piece p) {
		ArrayList<Square> squaresAbvList = new ArrayList<Square>();
		for (int i = 0; i < GamePanel.squares.length; i++) {
			for (int j = 0; j < GamePanel.squares[i].length; j++) {
				if (p.getSquare().equals(GamePanel.squares[i][j])) {
					for (int j2 = 1; j2 <= xAbove; j2++) {
						squaresAbvList.add(GamePanel.squares[i - j2][j]);
					}

				}
			}
		}
		Square[] temp = new Square[squaresAbvList.size()];
		for (int i = 0; i < squaresAbvList.size(); i++) {
			temp[i] = squaresAbvList.get(i);
		}
		return temp;

	}

	public Square[] getUpRightDiagonal(int xAbove, Piece p) {
		ArrayList<Square> squaresAbvList = new ArrayList<Square>();
		for (int i = 0; i < GamePanel.squares.length; i++) {
			for (int j = 0; j < GamePanel.squares[i].length; j++) {
				if (p.getSquare().equals(GamePanel.squares[i][j])) {

					for (int j2 = 1; j2 <= xAbove; j2++) {
						squaresAbvList.add(GamePanel.squares[i - j2][j + j2]);
						System.out.println("Up Right");
					}

				}
			}
		}
		Square[] temp = new Square[squaresAbvList.size()];
		for (int i = 0; i < squaresAbvList.size(); i++) {
			temp[i] = squaresAbvList.get(i);
			System.out.println(temp[i].getIIndex() + ", " + temp[i].getJIndex());
		}
		return temp;

	}

	public Square[] getUpLeftDiagonal(int xAbove, Piece p) {

		ArrayList<Square> squaresAbvList = new ArrayList<Square>();
		for (int i = 0; i < GamePanel.squares.length; i++) {
			for (int j = 0; j < GamePanel.squares[i].length; j++) {
				if (p.getSquare().equals(GamePanel.squares[i][j])) {

					for (int j2 = 1; j2 <= xAbove; j2++) {
						squaresAbvList.add(GamePanel.squares[i - j2][j - j2]);
					}

				}
			}
		}
		Square[] temp = new Square[squaresAbvList.size()];
		for (int i = 0; i < squaresAbvList.size(); i++) {
			temp[i] = squaresAbvList.get(i);
		}
		return temp;

	}

	public Square[] getDownLeftDiagonal(int xBelow, Piece p) {
		ArrayList<Square> squaresAbvList = new ArrayList<Square>();
		for (int i = 0; i < GamePanel.squares.length; i++) {
			for (int j = 0; j < GamePanel.squares[i].length; j++) {
				if (p.getSquare().equals(GamePanel.squares[i][j])) {

					for (int j2 = 1; j2 <= Math.abs(xBelow); j2++) {
						squaresAbvList.add(GamePanel.squares[i + j2][j - j2]);

					}

				}
			}
		}
		Square[] temp = new Square[squaresAbvList.size()];
		for (int i = 0; i < squaresAbvList.size(); i++) {
			temp[i] = squaresAbvList.get(i);
		}
		return temp;

	}

	public Square[] getDownRightDiagonal(int xBelow, Piece p) {
		ArrayList<Square> squaresAbvList = new ArrayList<Square>();
		for (int i = 0; i < GamePanel.squares.length; i++) {
			for (int j = 0; j < GamePanel.squares[i].length; j++) {
				if (p.getSquare().equals(GamePanel.squares[i][j])) {

					for (int j2 = 1; j2 <= Math.abs(xBelow); j2++) {
						squaresAbvList.add(GamePanel.squares[i + j2][j + j2]);

					}

				}
			}
		}
		Square[] temp = new Square[squaresAbvList.size()];
		for (int i = 0; i < squaresAbvList.size(); i++) {
			temp[i] = squaresAbvList.get(i);
		}
		return temp;

	}

	public Square canMove(Square[] squareArray) {
		System.out.println("Size of Square Array is " + squareArray.length);
		for (int i = 0; i < squareArray.length; i++) {
			if (squareArray[i].hasPlayer() == true) {
				System.out.println("whoopss");
				return squareArray[i];
			}
		}
		return null;

	}

	public Square canMoveSingle(Square s) {
		if (s.hasPlayer() == true && !s.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
			return s;
		}
		return null;
	}

	public Square[] getBelow(int xBelow, Piece p) {
		ArrayList<Square> squaresAbvList = new ArrayList<Square>();
		for (int i = 0; i < GamePanel.squares.length; i++) {
			for (int j = 0; j < GamePanel.squares[i].length; j++) {
				if (p.getSquare().equals(GamePanel.squares[i][j])) {

					for (int j2 = 1; j2 <= Math.abs(xBelow); j2++) {
						squaresAbvList.add(GamePanel.squares[i + j2][j]);

					}

				}
			}
		}
		Square[] temp = new Square[squaresAbvList.size()];
		for (int i = 0; i < squaresAbvList.size(); i++) {
			temp[i] = squaresAbvList.get(i);
		}
		return temp;

	}

	public Piece checkIfOnlyLast(Square[] a) {

		for (int i = 0; i < a.length; i++) {
			if (i != a.length - 1 && a[i].hasPlayer()) {
				return a[i].getPiece();
			}
		}
		return null;

	}

	// to use in ComputerRuleCheck class -> Will change simulator class to
	// PlayerRuleCheck class

//	public void removeUserPiece(Piece p) {
//		GamePanel.piecesComputerTook.add(p);
//
//		if (GamePanel.userColor.equalsIgnoreCase("Black")) {
//			ArrayList<Piece> temp = new ArrayList<Piece>();
//			for (int i = 0; i < GamePanel.blackPieces.length; i++) {
//				if (p.equals(GamePanel.blackPieces[i])) {
//				} else {
//
//					temp.add(GamePanel.blackPieces[i]);
//				}
//			}
//			Piece[] temp2 = new Piece[temp.size()];
//
//			for (int i = 0; i < temp2.length; i++) {
//				temp2[i] = temp.get(i);
//			}
//
//			GamePanel.blackPieces = temp2;
//
//		} else if (GamePanel.userColor.equalsIgnoreCase("White")) {
//			ArrayList<Piece> temp = new ArrayList<Piece>();
//			for (int i = 0; i < GamePanel.whitePieces.length; i++) {
//				if (p.equals(GamePanel.whitePieces[i])) {
//				} else {
//
//					temp.add(GamePanel.whitePieces[i]);
//				}
//			}
//			Piece[] temp2 = new Piece[temp.size()];
//
//			for (int i = 0; i < temp2.length; i++) {
//				temp2[i] = temp.get(i);
//			}
//
//			GamePanel.blackPieces = temp2;
//
//		}
//
//	}

}
