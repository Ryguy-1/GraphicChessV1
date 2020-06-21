
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class RuleCheckAll {

	private Piece pMoved;
	private Square sMovedTo;

	private int valueAdded;
	private Piece pieceRemoved;

	private Piece rightRook;
	private Piece leftRook;

	public Piece getPieceMoved() {
		return pMoved;
	}

	public Square getSquareMovedTo() {
		return sMovedTo;
	}

	public int getValueAdded() {
		return valueAdded;
	}

	public Piece getPieceRemoved() {
		return pieceRemoved;
	}

	
	
	
	public boolean checkPlayerPossibilities(Piece p, Square movedTo, Piece rightRook, Piece leftRook,
			String whichPlayer) {

		valueAdded = 0;

		this.rightRook = rightRook;
		this.leftRook = leftRook;
		this.pMoved = p;
		this.sMovedTo = movedTo;

		if (whichPlayer.equalsIgnoreCase("Computer")) {
			if (!GamePanel.userColor.equalsIgnoreCase("White")) {

				if (p.getSource() == "White Pawn.png") {
					return computerPawn(movedTo, p);
				} else if (p.getSource() == "White Rook.png") {
					return computerRook(movedTo, p);
				} else if (p.getSource() == "White Knight.png") {
					return computerKnight(movedTo, p);
				} else if (p.getSource() == "White Bishop.png") {
					return computerBishop(movedTo, p);
				} else if (p.getSource() == "White Queen.png") {
					return computerQueen(movedTo, p);
				} else if (p.getSource() == "White King.png") {
					return computerKing(movedTo, p);
				}

			} else if (!GamePanel.userColor.equalsIgnoreCase("Black")) {
				if (p.getSource() == "Black Pawn.png") {
					return computerPawn(movedTo, p);
				} else if (p.getSource() == "Black Rook.png") {
					return computerRook(movedTo, p);
				} else if (p.getSource() == "Black Knight.png") {
					return computerKnight(movedTo, p);
				} else if (p.getSource() == "Black Bishop.png") {
					return computerBishop(movedTo, p);
				} else if (p.getSource() == "Black Queen.png") {
					return computerQueen(movedTo, p);
				} else if (p.getSource() == "Black King.png") {
					return computerKing(movedTo, p);
				}

			}
			return false;
		} else if (whichPlayer.equalsIgnoreCase("User")) {
			if (GamePanel.userColor.equalsIgnoreCase("White")) {

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

			} else if (GamePanel.userColor.equalsIgnoreCase("Black")) {
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
		return false;
	}

	private boolean computerPawn(Square movedTo, Piece p) {

		boolean willReturn;

		int numIdxAbove = p.getSquare().getIIndex() - movedTo.getIIndex();
		if (movedTo.getJIndex() == p.getSquare().getJIndex()) {
			if (numIdxAbove == -2 && p.pawnFirst() == false && p.getSquare().getIIndex() == 1) {
				if (null == computerCanMove(computerGetBelow(numIdxAbove, p))) {
					p.setPawnFirst(true);
					willReturn = true;
				} else {
					willReturn = false;
				}
			} else if (numIdxAbove == -1) {
				if (null == computerCanMove(computerGetBelow(numIdxAbove, p))) {
					willReturn = true;
				} else {
					willReturn = false;
				}
			} else {
				willReturn = false;
			}

		} else {

			// means you may try to attack another piece diagonally

			if (movedTo.hasPlayer() == true && movedTo.hasPlayer()
					&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
				if (movedTo.getJIndex() == p.getSquare().getJIndex() + 1
						&& movedTo.getIIndex() == p.getSquare().getIIndex() + 1) {
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
					willReturn = true;
				} else if (movedTo.getJIndex() == p.getSquare().getJIndex() - 1
						&& movedTo.getIIndex() == p.getSquare().getIIndex() + 1) {
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
					willReturn = true;
				} else {
					willReturn = false;
				}

			} else {
				willReturn = false;
			}

		}

		// checks if pawn reached end and swaps it to queen if it has been to end

		if (p.getSquare().getIIndex() == 6 && numIdxAbove == -1 && willReturn == true) {
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

	private boolean computerRook(Square movedTo, Piece p) {

		int numIdxAbove = p.getSquare().getIIndex() - movedTo.getIIndex();
		int numIdxSide = p.getSquare().getJIndex() - movedTo.getJIndex();

		if (movedTo.getJIndex() == p.getSquare().getJIndex()) {
			if (numIdxAbove >= 1) {
				if (null == computerCanMove(computerGetAbove(numIdxAbove, p))) {
					p.setRookFirst(true);
					return true;
				} else if (movedTo == computerCanMove(computerGetAbove(numIdxAbove, p))
						&& computerCheckIfOnlyLast(computerGetAbove(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded += movedTo.getPiece().getValue();
					p.setRookFirst(true);
					return true;

				} else {
					return false;
				}
			} else if (numIdxAbove <= -1) {
				if (null == computerCanMove(computerGetBelow(numIdxAbove, p))) {
					p.setRookFirst(true);
					return true;
				} else if (movedTo == computerCanMove(computerGetBelow(numIdxAbove, p))
						&& computerCheckIfOnlyLast(computerGetBelow(numIdxAbove, p))
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
				if (null == computerCanMove(computerGetLeftSquares(numIdxSide, p))) {
					p.setRookFirst(true);
					return true;
				} else if (movedTo == computerCanMove(computerGetLeftSquares(numIdxSide, p))
						&& computerCheckIfOnlyLast(computerGetLeftSquares(numIdxSide, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded += movedTo.getPiece().getValue();
					p.setRookFirst(true);
					return true;
				} else {
					return false;
				}
			} else if (numIdxSide <= -1) {

				if (null == computerCanMove(computerGetRightSquares(numIdxSide, p))) {
					p.setRookFirst(true);
					return true;
				} else if (movedTo == computerCanMove(computerGetRightSquares(numIdxSide, p))
						&& computerCheckIfOnlyLast(computerGetRightSquares(numIdxSide, p))
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

	private boolean computerKnight(Square movedTo, Piece p) {

		if (p.getSquare().getIIndex() - 2 == movedTo.getIIndex()
				&& p.getSquare().getJIndex() - 1 == movedTo.getJIndex()) {
			if (null == computerCanMoveSingle(movedTo)) {
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
			if (null == computerCanMoveSingle(movedTo)) {
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
			if (null == computerCanMoveSingle(movedTo)) {
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
			if (null == computerCanMoveSingle(movedTo)) {
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
			if (null == computerCanMoveSingle(movedTo)) {
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
			if (null == computerCanMoveSingle(movedTo)) {
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
			if (null == computerCanMoveSingle(movedTo)) {
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
			if (null == computerCanMoveSingle(movedTo)) {
				if (movedTo.hasPlayer()) {
					pieceRemoved = movedTo.getPiece();
					valueAdded += movedTo.getPiece().getValue();
				}
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	private boolean computerBishop(Square movedTo, Piece p) {
		// if >1, moving up, if <1, moving down
		int numIdxAbove = p.getSquare().getIIndex() - movedTo.getIIndex();
		if (movedTo.getJIndex() != p.getSquare().getJIndex()
				&& Math.abs(p.getSquare().getIIndex() - movedTo.getIIndex()) == Math
						.abs(p.getSquare().getJIndex() - movedTo.getJIndex())) {
			if (numIdxAbove >= 1 && movedTo.getJIndex() < p.getSquare().getJIndex()) {
				if (null == computerCanMove(computerGetUpLeftDiagonal(numIdxAbove, p))) {
					return true;
				} else if (movedTo == computerCanMove(computerGetUpLeftDiagonal(numIdxAbove, p))
						&& computerCheckIfOnlyLast(computerGetUpLeftDiagonal(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded += movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove >= 1 && movedTo.getJIndex() > p.getSquare().getJIndex()) {
				if (null == computerCanMove(computerGetUpRightDiagonal(numIdxAbove, p))) {
					return true;
				} else if (movedTo == computerCanMove(computerGetUpRightDiagonal(numIdxAbove, p))
						&& computerCheckIfOnlyLast(computerGetUpRightDiagonal(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded += movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove <= -1 && movedTo.getJIndex() < p.getSquare().getJIndex()) {
				if (null == computerCanMove(computerGetDownLeftDiagonal(numIdxAbove, p))) {
					return true;
				} else if (movedTo == computerCanMove(computerGetDownLeftDiagonal(numIdxAbove, p))
						&& computerCheckIfOnlyLast(computerGetDownLeftDiagonal(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded += movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove <= -1 && movedTo.getJIndex() > p.getSquare().getJIndex()) {
				if (null == computerCanMove(computerGetDownRightDiagonal(numIdxAbove, p))) {
					return true;
				} else if (movedTo == computerCanMove(computerGetDownRightDiagonal(numIdxAbove, p))
						&& computerCheckIfOnlyLast(computerGetDownRightDiagonal(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded += movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			} else {
				// added in this else too from origional. Shouldn't matter and eclipse is
				// impartial if I put this here.
				return false;
			}

		} else {
			return false;
		}

	}

	private boolean computerQueen(Square movedTo, Piece p) {
		int numIdxAbove = p.getSquare().getIIndex() - movedTo.getIIndex();
		int numIdxSide = p.getSquare().getJIndex() - movedTo.getJIndex();
		if (movedTo.getJIndex() == p.getSquare().getJIndex()) {
			if (numIdxAbove >= 1) {
				if (null == computerCanMove(computerGetAbove(numIdxAbove, p))) {
					return true;
				} else if (movedTo == computerCanMove(computerGetAbove(numIdxAbove, p))
						&& computerCheckIfOnlyLast(computerGetAbove(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded += movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove <= -1) {
				if (null == computerCanMove(computerGetBelow(numIdxAbove, p))) {
					return true;
				} else if (movedTo == computerCanMove(computerGetBelow(numIdxAbove, p))
						&& computerCheckIfOnlyLast(computerGetBelow(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded += movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			}
		} else if (movedTo.getJIndex() != p.getSquare().getJIndex()
				&& Math.abs(p.getSquare().getIIndex() - movedTo.getIIndex()) == Math
						.abs(p.getSquare().getJIndex() - movedTo.getJIndex())) {
			if (numIdxAbove >= 1 && movedTo.getJIndex() < p.getSquare().getJIndex()) {
				if (null == computerCanMove(computerGetUpLeftDiagonal(numIdxAbove, p))) {
					return true;
				} else if (movedTo == computerCanMove(computerGetUpLeftDiagonal(numIdxAbove, p))
						&& computerCheckIfOnlyLast(computerGetUpLeftDiagonal(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded += movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove >= 1 && movedTo.getJIndex() > p.getSquare().getJIndex()) {
				if (null == computerCanMove(computerGetUpRightDiagonal(numIdxAbove, p))) {
					return true;
				} else if (movedTo == computerCanMove(computerGetUpRightDiagonal(numIdxAbove, p))
						&& computerCheckIfOnlyLast(computerGetUpRightDiagonal(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded += movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove <= -1 && movedTo.getJIndex() < p.getSquare().getJIndex()) {
				if (null == computerCanMove(computerGetDownLeftDiagonal(numIdxAbove, p))) {
					return true;
				} else if (movedTo == computerCanMove(computerGetDownLeftDiagonal(numIdxAbove, p))
						&& computerCheckIfOnlyLast(computerGetDownLeftDiagonal(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded += movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove <= -1 && movedTo.getJIndex() > p.getSquare().getJIndex()) {
				if (null == computerCanMove(computerGetDownRightDiagonal(numIdxAbove, p))) {
					return true;
				} else if (movedTo == computerCanMove(computerGetDownRightDiagonal(numIdxAbove, p))
						&& computerCheckIfOnlyLast(computerGetDownRightDiagonal(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded += movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			}
		} else if (movedTo.getIIndex() == p.getSquare().getIIndex()) {
			if (numIdxSide >= 1) {
				if (null == computerCanMove(computerGetLeftSquares(numIdxSide, p))) {
					return true;
				} else if (movedTo == computerCanMove(computerGetLeftSquares(numIdxSide, p))
						&& computerCheckIfOnlyLast(computerGetLeftSquares(numIdxSide, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded += movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			} else if (numIdxSide <= -1) {

				if (null == computerCanMove(computerGetRightSquares(numIdxSide, p))) {
					return true;
				} else if (movedTo == computerCanMove(computerGetRightSquares(numIdxSide, p))
						&& computerCheckIfOnlyLast(computerGetRightSquares(numIdxSide, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					valueAdded += movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			}

		} else {
			return false;
		}

		// made me add in.
		return false;

	}

	private boolean computerKing(Square movedTo, Piece p) {

		int numIdxAbove = p.getSquare().getIIndex() - movedTo.getIIndex();
		int numIdxSide = p.getSquare().getJIndex() - movedTo.getJIndex();
		if (movedTo.getJIndex() == p.getSquare().getJIndex()) {
			if (numIdxAbove == 1) {
				if (null == computerCanMove(computerGetAbove(numIdxAbove, p))) {
					p.setKingFirst(true);
					return true;
				} else if (movedTo == computerCanMove(computerGetAbove(numIdxAbove, p))
						&& computerCheckIfOnlyLast(computerGetAbove(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					p.setKingFirst(true);
					valueAdded += movedTo.getPiece().getValue();
					return true;

				} else {
					return false;
				}
			} else if (numIdxAbove == -1) {
				if (null == computerCanMove(computerGetBelow(numIdxAbove, p))) {
					p.setKingFirst(true);
					return true;
				} else if (movedTo == computerCanMove(computerGetBelow(numIdxAbove, p))
						&& computerCheckIfOnlyLast(computerGetBelow(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					p.setKingFirst(true);
					valueAdded += movedTo.getPiece().getValue();
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
				if (null == computerCanMove(computerGetUpLeftDiagonal(numIdxAbove, p))) {
					p.setKingFirst(true);
					return true;
				} else if (movedTo == computerCanMove(computerGetUpLeftDiagonal(numIdxAbove, p))
						&& computerCheckIfOnlyLast(computerGetUpLeftDiagonal(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					p.setKingFirst(true);
					valueAdded += movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove == 1 && movedTo.getJIndex() > p.getSquare().getJIndex()) {
				if (null == computerCanMove(computerGetUpRightDiagonal(numIdxAbove, p))) {
					p.setKingFirst(true);
					return true;
				} else if (movedTo == computerCanMove(computerGetUpRightDiagonal(numIdxAbove, p))
						&& computerCheckIfOnlyLast(computerGetUpRightDiagonal(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					p.setKingFirst(true);
					valueAdded += movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove == -1 && movedTo.getJIndex() < p.getSquare().getJIndex()) {
				if (null == computerCanMove(computerGetDownLeftDiagonal(numIdxAbove, p))) {
					p.setKingFirst(true);
					return true;
				} else if (movedTo == computerCanMove(computerGetDownLeftDiagonal(numIdxAbove, p))
						&& computerCheckIfOnlyLast(computerGetDownLeftDiagonal(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					p.setKingFirst(true);
					valueAdded += movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove == -1 && movedTo.getJIndex() > p.getSquare().getJIndex()) {
				if (null == computerCanMove(computerGetDownRightDiagonal(numIdxAbove, p))) {
					p.setKingFirst(true);
					return true;
				} else if (movedTo == computerCanMove(computerGetDownRightDiagonal(numIdxAbove, p))
						&& computerCheckIfOnlyLast(computerGetDownRightDiagonal(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					p.setKingFirst(true);
					valueAdded += movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}

		} else if (movedTo.getIIndex() == p.getSquare().getIIndex()) {

			if (numIdxSide == 1) {
				if (null == computerCanMove(computerGetLeftSquares(numIdxSide, p))) {
					p.setKingFirst(true);
					return true;
				} else if (movedTo == computerCanMove(computerGetLeftSquares(numIdxAbove, p))
						&& computerCheckIfOnlyLast(computerGetLeftSquares(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					p.setKingFirst(true);
					valueAdded += movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
			} else if (numIdxSide == -1) {

				if (null == computerCanMove(computerGetRightSquares(numIdxSide, p))) {
					p.setKingFirst(true);
					return true;
				} else if (movedTo == computerCanMove(computerGetRightSquares(numIdxAbove, p))
						&& computerCheckIfOnlyLast(computerGetRightSquares(numIdxAbove, p))
						&& movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					pieceRemoved = movedTo.getPiece();
					p.setKingFirst(true);
					valueAdded += movedTo.getPiece().getValue();
					return true;
				} else {
					return false;
				}
				// check rook king swap
			} else if (p.getSquare().getJIndex() == 4 && numIdxSide == -2) {
				if (p.kingFirst() == false && rightRook.rookFirst() == false) {
					if (null == computerCanMove(computerGetRightSquares(numIdxSide, p))) {
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
					if (null == computerCanMove(computerGetLeftSquares(numIdxSide, p))) {
						leftRook.setRookFirst(true);
						leftRook.moveTo(leftRook.getSquare(), GamePanel.squares[0][3]);
						p.setKingFirst(true);
						return true;
					}
				} else {
					return false;
				}

			} else {
				return false;
			}

		} else {
			return false;
		}
		// made me add in.
		return false;

	}

	private Square[] computerGetRightSquares(int xRight, Piece p) {
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

	private Square[] computerGetLeftSquares(int xLeft, Piece p) {
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

	private Square[] computerGetAbove(int xAbove, Piece p) {
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

	private Square[] computerGetUpRightDiagonal(int xAbove, Piece p) {
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

	private Square[] computerGetUpLeftDiagonal(int xAbove, Piece p) {

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

	private Square[] computerGetDownLeftDiagonal(int xBelow, Piece p) {
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

	private Square[] computerGetDownRightDiagonal(int xBelow, Piece p) {
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

	private Square computerCanMove(Square[] squareArray) {

		for (int i = 0; i < squareArray.length; i++) {
			if (squareArray[i].hasPlayer() == true) {
				return squareArray[i];
			}
		}
		return null;

	}

	private Square computerCanMoveSingle(Square s) {
		if (s.hasPlayer() == true && !s.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
			return s;
		}
		return null;
	}

	private Square[] computerGetBelow(int xBelow, Piece p) {
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

	private boolean computerCheckIfOnlyLast(Square[] a) {

		for (int i = 0; i < a.length; i++) {
			if (i != a.length - 1 && a[i].hasPlayer()) {
				return false;
			}
		}
		return true;

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Below
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// is
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// User
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Section
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean pawn(Square movedTo, Piece p) {
		boolean willReturn = false;

		int numIdxAbove = p.getSquare().getIIndex() - movedTo.getIIndex();
		if (movedTo.getJIndex() == p.getSquare().getJIndex()) {
			if (numIdxAbove == 2 && p.pawnFirst() == false && p.getSquare().getIIndex() == 6) {
				if (null == canMove(getAbove(numIdxAbove, p))) {
					p.setPawnFirst(true);
					willReturn = true;
				} else {
					willReturn = false;
				}
			} else if (numIdxAbove == 1) {
				if (null == canMove(getAbove(numIdxAbove, p))) {
					willReturn = true;

				} else {
					willReturn = false;
				}
			} else {
				willReturn = false;
			}

		} else {

			// means you may try to attack another piece diagonally

			if (movedTo.hasPlayer() == true && movedTo.hasPlayer()
					&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
				if (movedTo.getJIndex() == p.getSquare().getJIndex() + 1
						&& movedTo.getIIndex() == p.getSquare().getIIndex() - 1) {
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
					willReturn = true;
				} else if (movedTo.getJIndex() == p.getSquare().getJIndex() - 1
						&& movedTo.getIIndex() == p.getSquare().getIIndex() - 1) {
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
					willReturn = true;
				} else {
					willReturn = false;
				}

			} else {
				willReturn = false;
			}

		}

		// checks if pawn reached end and swaps it to queen if it has been to end

		if (p.getSquare().getIIndex() == 1 && numIdxAbove == 1 && willReturn == true) {
			System.out.println("got this far");
			if (GamePanel.userColor.equalsIgnoreCase("White")) {
				System.out.println("WHITE");
				for (int i = 0; i < GamePanel.whitePieces.length; i++) {
					if (GamePanel.whitePieces[i].getSource() == "White Queen.png") {
						p.setSource("White Queen.png");
						p.setBufferedImage(GamePanel.whitePieces[i].getImage());
						System.out.println("registered end");
						System.out.println(p.getSource());
						break;
					}
				}
				for (int i = 0; i < GamePanel.piecesUserTook.size(); i++) {
					if (GamePanel.piecesUserTook.get(i).getSource() == "White Queen.png") {
						p.setSource("White Queen.png");
						p.setBufferedImage(GamePanel.piecesUserTook.get(i).getImage());
						break;
					}
				}

				// check pieces user took if things r actually added to it
			} else if (GamePanel.userColor.equalsIgnoreCase("Black")) {
				for (int i = 0; i < GamePanel.blackPieces.length; i++) {
					if (GamePanel.blackPieces[i].getSource() == "Black Queen.png") {
						p.setSource("Black Queen.png");
						p.setBufferedImage(GamePanel.blackPieces[i].getImage());
						break;
					}
				}
				for (int i = 0; i < GamePanel.piecesUserTook.size(); i++) {
					if (GamePanel.piecesUserTook.get(i).getSource() == "Black Queen.png") {
						p.setSource("Black Queen.png");
						p.setBufferedImage(GamePanel.piecesUserTook.get(i).getImage());
						break;
					}
				}
			}

		}

		return willReturn;

	}

	private boolean rook(Square movedTo, Piece p) {

		int numIdxAbove = p.getSquare().getIIndex() - movedTo.getIIndex();
		int numIdxSide = p.getSquare().getJIndex() - movedTo.getJIndex();

		if (movedTo.getJIndex() == p.getSquare().getJIndex()) {
			if (numIdxAbove >= 1) {
				if (null == canMove(getAbove(numIdxAbove, p))) {
					p.setRookFirst(true);
					return true;
				} else if (movedTo == canMove(getAbove(numIdxAbove, p)) && checkIfOnlyLast(getAbove(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					valueAdded += movedTo.getPiece().getValue();
					p.setRookFirst(true);
					pieceRemoved = movedTo.getPiece();
					return true;

				} else {
					return false;
				}
			} else if (numIdxAbove <= -1) {
				if (null == canMove(getBelow(numIdxAbove, p))) {
					p.setRookFirst(true);
					return true;
				} else if (movedTo == canMove(getBelow(numIdxAbove, p)) && checkIfOnlyLast(getBelow(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					p.setRookFirst(true);
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
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
						&& checkIfOnlyLast(getLeftSquares(numIdxSide, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					p.setRookFirst(true);
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
					return true;
				} else {
					return false;
				}
			} else if (numIdxSide <= -1) {

				if (null == canMove(getRightSquares(numIdxSide, p))) {
					p.setRookFirst(true);
					return true;
				} else if (movedTo == canMove(getRightSquares(numIdxSide, p))
						&& checkIfOnlyLast(getRightSquares(numIdxSide, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					p.setRookFirst(true);
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
					return true;
				} else {
					return false;
				}
			}

		} else {
			return false;
		}
		// made me add in
		return false;
	}
//need to do takes for night

	private boolean knight(Square movedTo, Piece p) {

		if (p.getSquare().getIIndex() - 2 == movedTo.getIIndex()
				&& p.getSquare().getJIndex() - 1 == movedTo.getJIndex()) {
			if (null == canMoveSingle(movedTo)) {
				if (movedTo.hasPlayer()) {
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
				}
				return true;
			} else {
				return false;
			}
		} else if (p.getSquare().getIIndex() - 1 == movedTo.getIIndex()
				&& p.getSquare().getJIndex() - 2 == movedTo.getJIndex()) {
			if (null == canMoveSingle(movedTo)) {
				if (movedTo.hasPlayer()) {
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
				}
				return true;
			} else {
				return false;
			}
		} else if (p.getSquare().getIIndex() + 1 == movedTo.getIIndex()
				&& p.getSquare().getJIndex() - 2 == movedTo.getJIndex()) {
			if (null == canMoveSingle(movedTo)) {
				if (movedTo.hasPlayer()) {
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
				}
				return true;
			} else {
				return false;
			}
		} else if (p.getSquare().getIIndex() + 2 == movedTo.getIIndex()
				&& p.getSquare().getJIndex() - 1 == movedTo.getJIndex()) {
			if (null == canMoveSingle(movedTo)) {
				if (movedTo.hasPlayer()) {
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
				}
				return true;
			} else {
				return false;
			}
		} else if (p.getSquare().getIIndex() + 2 == movedTo.getIIndex()
				&& p.getSquare().getJIndex() + 1 == movedTo.getJIndex()) {
			if (null == canMoveSingle(movedTo)) {
				if (movedTo.hasPlayer()) {
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
				}
				return true;
			} else {
				return false;
			}
		} else if (p.getSquare().getIIndex() + 1 == movedTo.getIIndex()
				&& p.getSquare().getJIndex() + 2 == movedTo.getJIndex()) {
			if (null == canMoveSingle(movedTo)) {
				if (movedTo.hasPlayer()) {
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
				}
				return true;
			} else {
				return false;
			}
		} else if (p.getSquare().getIIndex() - 1 == movedTo.getIIndex()
				&& p.getSquare().getJIndex() + 2 == movedTo.getJIndex()) {
			if (null == canMoveSingle(movedTo)) {
				if (movedTo.hasPlayer()) {
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
				}
				return true;
			} else {
				return false;
			}
		} else if (p.getSquare().getIIndex() - 2 == movedTo.getIIndex()
				&& p.getSquare().getJIndex() + 1 == movedTo.getJIndex()) {
			if (null == canMoveSingle(movedTo)) {
				if (movedTo.hasPlayer()) {
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
				}
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

//working on bishop next OPEN TO HERE
	private boolean bishop(Square movedTo, Piece p) {
		// if >1, moving up, if <1, moving down
		int numIdxAbove = p.getSquare().getIIndex() - movedTo.getIIndex();
		if (movedTo.getJIndex() != p.getSquare().getJIndex()
				&& Math.abs(p.getSquare().getIIndex() - movedTo.getIIndex()) == Math
						.abs(p.getSquare().getJIndex() - movedTo.getJIndex())) {
			if (numIdxAbove >= 1 && movedTo.getJIndex() < p.getSquare().getJIndex()) {
				if (null == canMove(getUpLeftDiagonal(numIdxAbove, p))) {
					return true;
				} else if (movedTo == canMove(getUpLeftDiagonal(numIdxAbove, p))
						&& checkIfOnlyLast(getUpLeftDiagonal(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove >= 1 && movedTo.getJIndex() > p.getSquare().getJIndex()) {
				if (null == canMove(getUpRightDiagonal(numIdxAbove, p))) {
					return true;
				} else if (movedTo == canMove(getUpRightDiagonal(numIdxAbove, p))
						&& checkIfOnlyLast(getUpRightDiagonal(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove <= -1 && movedTo.getJIndex() < p.getSquare().getJIndex()) {
				if (null == canMove(getDownLeftDiagonal(numIdxAbove, p))) {
					return true;
				} else if (movedTo == canMove(getDownLeftDiagonal(numIdxAbove, p))
						&& checkIfOnlyLast(getDownLeftDiagonal(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove <= -1 && movedTo.getJIndex() > p.getSquare().getJIndex()) {
				if (null == canMove(getDownRightDiagonal(numIdxAbove, p))) {
					return true;
				} else if (movedTo == canMove(getDownRightDiagonal(numIdxAbove, p))
						&& checkIfOnlyLast(getDownRightDiagonal(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
		// made me add in.
		return false;

	}

	private boolean queen(Square movedTo, Piece p) {
		int numIdxAbove = p.getSquare().getIIndex() - movedTo.getIIndex();
		int numIdxSide = p.getSquare().getJIndex() - movedTo.getJIndex();
		if (movedTo.getJIndex() == p.getSquare().getJIndex()) {
			if (numIdxAbove >= 1) {
				if (null == canMove(getAbove(numIdxAbove, p))) {
					return true;
				} else if (movedTo == canMove(getAbove(numIdxAbove, p)) && checkIfOnlyLast(getAbove(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove <= -1) {
				if (null == canMove(getBelow(numIdxAbove, p))) {
					return true;
				} else if (movedTo == canMove(getBelow(numIdxAbove, p)) && checkIfOnlyLast(getBelow(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
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
						&& checkIfOnlyLast(getUpLeftDiagonal(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove >= 1 && movedTo.getJIndex() > p.getSquare().getJIndex()) {
				if (null == canMove(getUpRightDiagonal(numIdxAbove, p))) {
					return true;
				} else if (movedTo == canMove(getUpRightDiagonal(numIdxAbove, p))
						&& checkIfOnlyLast(getUpRightDiagonal(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove <= -1 && movedTo.getJIndex() < p.getSquare().getJIndex()) {
				if (null == canMove(getDownLeftDiagonal(numIdxAbove, p))) {
					return true;
				} else if (movedTo == canMove(getDownLeftDiagonal(numIdxAbove, p))
						&& checkIfOnlyLast(getDownLeftDiagonal(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove <= -1 && movedTo.getJIndex() > p.getSquare().getJIndex()) {
				if (null == canMove(getDownRightDiagonal(numIdxAbove, p))) {
					return true;
				} else if (movedTo == canMove(getDownRightDiagonal(numIdxAbove, p))
						&& checkIfOnlyLast(getDownRightDiagonal(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
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
						&& checkIfOnlyLast(getLeftSquares(numIdxSide, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
					return true;
				} else {
					return false;
				}
			} else if (numIdxSide <= -1) {

				if (null == canMove(getRightSquares(numIdxSide, p))) {
					return true;
				} else if (movedTo == canMove(getRightSquares(numIdxSide, p))
						&& checkIfOnlyLast(getRightSquares(numIdxSide, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
					return true;
				} else {
					return false;
				}
			}

		} else {
			return false;
		}
		// made me add in.
		return false;

	}

	private boolean king(Square movedTo, Piece p) {

		int numIdxAbove = p.getSquare().getIIndex() - movedTo.getIIndex();
		int numIdxSide = p.getSquare().getJIndex() - movedTo.getJIndex();
		if (movedTo.getJIndex() == p.getSquare().getJIndex()) {
			if (numIdxAbove == 1) {
				if (null == canMove(getAbove(numIdxAbove, p))) {
					p.setKingFirst(true);
					return true;
				} else if (movedTo == canMove(getAbove(numIdxAbove, p)) && checkIfOnlyLast(getAbove(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					p.setKingFirst(true);
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove == -1) {
				if (null == canMove(getBelow(numIdxAbove, p))) {
					p.setKingFirst(true);
					return true;
				} else if (movedTo == canMove(getBelow(numIdxAbove, p)) && checkIfOnlyLast(getBelow(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					p.setKingFirst(true);
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
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
						&& checkIfOnlyLast(getUpLeftDiagonal(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					p.setKingFirst(true);
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove == 1 && movedTo.getJIndex() > p.getSquare().getJIndex()) {
				if (null == canMove(getUpRightDiagonal(numIdxAbove, p))) {
					p.setKingFirst(true);
					return true;
				} else if (movedTo == canMove(getUpRightDiagonal(numIdxAbove, p))
						&& checkIfOnlyLast(getUpRightDiagonal(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					p.setKingFirst(true);
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove == -1 && movedTo.getJIndex() < p.getSquare().getJIndex()) {
				if (null == canMove(getDownLeftDiagonal(numIdxAbove, p))) {
					p.setKingFirst(true);
					return true;
				} else if (movedTo == canMove(getDownLeftDiagonal(numIdxAbove, p))
						&& checkIfOnlyLast(getDownLeftDiagonal(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					p.setKingFirst(true);
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
					return true;
				} else {
					return false;
				}
			} else if (numIdxAbove == -1 && movedTo.getJIndex() > p.getSquare().getJIndex()) {
				if (null == canMove(getDownRightDiagonal(numIdxAbove, p))) {
					p.setKingFirst(true);
					return true;
				} else if (movedTo == canMove(getDownRightDiagonal(numIdxAbove, p))
						&& checkIfOnlyLast(getDownRightDiagonal(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					p.setKingFirst(true);
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
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
						&& checkIfOnlyLast(getLeftSquares(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					p.setKingFirst(true);
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
					return true;
				} else {
					return false;
				}
			} else if (numIdxSide == -1) {

				if (null == canMove(getRightSquares(numIdxSide, p))) {
					p.setKingFirst(true);
					return true;
				} else if (movedTo == canMove(getRightSquares(numIdxAbove, p))
						&& checkIfOnlyLast(getRightSquares(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					p.setKingFirst(true);
					valueAdded += movedTo.getPiece().getValue();
					pieceRemoved = movedTo.getPiece();
					return true;
				} else {
					return false;
				}
				// check rook king swap
			}

			else if (p.getSquare().getJIndex() == 4 && numIdxSide == -2) {
				if (p.kingFirst() == false && rightRook.rookFirst() == false) {
					if (null == canMove(getRightSquares(numIdxSide, p))) {
						rightRook.moveTo(rightRook.getSquare(), GamePanel.squares[7][5]);
						rightRook.setRookFirst(true);
						p.setKingFirst(true);
						return true;
					}
				} else {
					return false;
				}

			} else if (p.getSquare().getJIndex() == 4 && numIdxSide == 2) {
				if (p.kingFirst() == false && leftRook.rookFirst() == false) {
					if (null == canMove(getLeftSquares(numIdxSide, p))) {
						leftRook.moveTo(leftRook.getSquare(), GamePanel.squares[7][3]);
						leftRook.setRookFirst(true);
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
		// made me add in.
		return false;

	}

	private Square[] getRightSquares(int xRight, Piece p) {
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

	private Square[] getLeftSquares(int xLeft, Piece p) {
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

	private Square[] getAbove(int xAbove, Piece p) {
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

	private Square[] getUpRightDiagonal(int xAbove, Piece p) {
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

	private Square[] getUpLeftDiagonal(int xAbove, Piece p) {

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

	private Square[] getDownLeftDiagonal(int xBelow, Piece p) {
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

	private Square[] getDownRightDiagonal(int xBelow, Piece p) {
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

	private Square canMove(Square[] squareArray) {
		System.out.println("Size of Square Array is " + squareArray.length);
		for (int i = 0; i < squareArray.length; i++) {
			if (squareArray[i].hasPlayer() == true) {
				System.out.println("whoopss");
				return squareArray[i];
			}
		}
		return null;

	}

	private Square canMoveSingle(Square s) {
		if (s.hasPlayer() == true && s.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
			return s;
		}
		return null;
	}

	private Square[] getBelow(int xBelow, Piece p) {
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

//	private void removeComputerPiece(Piece p) {
//		GamePanel.piecesUserTook.add(p);
//		System.out.println("Player has taken " + GamePanel.piecesUserTook.size() + " pieces from the computer.");
//		if (GamePanel.userColor.equalsIgnoreCase("White")) {
//			ArrayList<Piece> temp = new ArrayList<Piece>();
//			for (int i = 0; i < GamePanel.blackPieces.length; i++) {
//				if (p.equals(GamePanel.blackPieces[i])) {
//
//				} else {
//
//					temp.add(GamePanel.blackPieces[i]);
//				}
//			}
//			Piece[] temp2 = new Piece[temp.size()];
//			// System.out.println(temp.size());
//			for (int i = 0; i < temp2.length; i++) {
//				temp2[i] = temp.get(i);
//			}
//
//			GamePanel.blackPieces = temp2;
//
//		} else if (GamePanel.userColor.equalsIgnoreCase("Black")) {
//			ArrayList<Piece> temp = new ArrayList<Piece>();
//			for (int i = 0; i < GamePanel.whitePieces.length; i++) {
//				if (p.equals(GamePanel.whitePieces[i])) {
//
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
//			GamePanel.whitePieces = temp2;
//
//		}
//	}

	private boolean checkIfOnlyLast(Square[] a) {

		for (int i = 0; i < a.length; i++) {
			if (i != a.length - 1 && a[i].hasPlayer()) {
				return false;
			}
		}
		return true;

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
