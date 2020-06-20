
import java.util.ArrayList;

import javax.swing.JOptionPane;


//has rules for the bottom pieces programmed in
public class UserRuleCheck {

	public void checkPlayerPossibilities(Square movedTo) {

		if (GamePanel.userColor.equalsIgnoreCase("White")) {

			for (int i = 0; i < GamePanel.whitePieces.length; i++) {
				if (GamePanel.whitePieces[i].wasClicked() == true) {
					if (GamePanel.whitePieces[i].getSource() == "White Pawn.png" && GamePanel.tempPieceIdx == i) {
						pawn(movedTo, GamePanel.whitePieces[i]);
					} else if (GamePanel.whitePieces[i].getSource() == "White Rook.png"
							&& GamePanel.tempPieceIdx == i) {
						rook(movedTo, GamePanel.whitePieces[i]);
					} else if (GamePanel.whitePieces[i].getSource() == "White Knight.png"
							&& GamePanel.tempPieceIdx == i) {
						knight(movedTo, GamePanel.whitePieces[i]);
					} else if (GamePanel.whitePieces[i].getSource() == "White Bishop.png"
							&& GamePanel.tempPieceIdx == i) {
						bishop(movedTo, GamePanel.whitePieces[i]);
					} else if (GamePanel.whitePieces[i].getSource() == "White Queen.png"
							&& GamePanel.tempPieceIdx == i) {
						queen(movedTo, GamePanel.whitePieces[i]);
					} else if (GamePanel.whitePieces[i].getSource() == "White King.png"
							&& GamePanel.tempPieceIdx == i) {
						king(movedTo, GamePanel.whitePieces[i]);
					}
				}
			}

		} else if (GamePanel.userColor.equalsIgnoreCase("Black")) {
			for (int i = 0; i < GamePanel.blackPieces.length; i++) {
				if (GamePanel.blackPieces[i].wasClicked() == true) {
					if (GamePanel.blackPieces[i].getSource() == "Black Pawn.png" && GamePanel.tempPieceIdx == i) {
						pawn(movedTo, GamePanel.blackPieces[i]);
					} else if (GamePanel.blackPieces[i].getSource() == "Black Rook.png"
							&& GamePanel.tempPieceIdx == i) {
						rook(movedTo, GamePanel.blackPieces[i]);
					} else if (GamePanel.blackPieces[i].getSource() == "Black Knight.png"
							&& GamePanel.tempPieceIdx == i) {
						knight(movedTo, GamePanel.blackPieces[i]);
					} else if (GamePanel.blackPieces[i].getSource() == "Black Bishop.png"
							&& GamePanel.tempPieceIdx == i) {
						bishop(movedTo, GamePanel.blackPieces[i]);
					} else if (GamePanel.blackPieces[i].getSource() == "Black Queen.png"
							&& GamePanel.tempPieceIdx == i) {
						queen(movedTo, GamePanel.blackPieces[i]);
					} else if (GamePanel.blackPieces[i].getSource() == "Black King.png"
							&& GamePanel.tempPieceIdx == i) {
						king(movedTo, GamePanel.blackPieces[i]);
					}
				}
			}
		}
	}

	public void pawn(Square movedTo, Piece p) {

		int numIdxAbove = p.getSquare().getIIndex() - movedTo.getIIndex();
		if (movedTo.getJIndex() == p.getSquare().getJIndex()) {
			if (numIdxAbove == 2 && p.pawnFirst() == false && p.getSquare().getIIndex() == 6) {
				if (null == canMove(getAbove(numIdxAbove, p))) {
					p.moveTo(p.getSquare(), movedTo);
					p.setPawnFirst(true);

				} else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}
			} else if (numIdxAbove == 1) {
				if (null == canMove(getAbove(numIdxAbove, p))) {
					p.moveTo(p.getSquare(), movedTo);

				} else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}
			} else {
				JOptionPane.showMessageDialog(null, "Illegal Move");
				GamePanel.playerTurn = true;
			}

		} else {

			// means you may try to attack another piece diagonally

			if (movedTo.hasPlayer() == true && movedTo.hasPlayer()
					&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
				if (movedTo.getJIndex() == p.getSquare().getJIndex() + 1
						&& movedTo.getIIndex() == p.getSquare().getIIndex() - 1) {
					removeComputerPiece(movedTo.getPiece());
					p.moveTo(p.getSquare(), movedTo);
					System.out.println("Diag Right");
				} else if (movedTo.getJIndex() == p.getSquare().getJIndex() - 1
						&& movedTo.getIIndex() == p.getSquare().getIIndex() - 1) {
					removeComputerPiece(movedTo.getPiece());
					p.moveTo(p.getSquare(), movedTo);
					System.out.println("Diag Left");
				} else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}

			} else {
				JOptionPane.showMessageDialog(null, "Illegal Move");
				GamePanel.playerTurn = true;
			}

		}

		// checks if pawn reached end and swaps it to queen if it has been to end

		if (p.getSquare().getIIndex() == 0) {
			if (GamePanel.userColor.equalsIgnoreCase("White")) {
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

	}

	public void rook(Square movedTo, Piece p) {

		int numIdxAbove = p.getSquare().getIIndex() - movedTo.getIIndex();
		int numIdxSide = p.getSquare().getJIndex() - movedTo.getJIndex();

		if (movedTo.getJIndex() == p.getSquare().getJIndex()) {
			if (numIdxAbove >= 1) {
				if (null == canMove(getAbove(numIdxAbove, p))) {
					p.moveTo(p.getSquare(), movedTo);
					p.setRookFirst(true);
				} else if (movedTo == canMove(getAbove(numIdxAbove, p)) && checkIfOnlyLast(getAbove(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					removeComputerPiece(movedTo.getPiece());
					p.moveTo(p.getSquare(), movedTo);
					p.setRookFirst(true);
				} else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}
			} else if (numIdxAbove <= -1) {
				if (null == canMove(getBelow(numIdxAbove, p))) {
					p.moveTo(p.getSquare(), movedTo);
					p.setRookFirst(true);
				} else if (movedTo == canMove(getBelow(numIdxAbove, p)) && checkIfOnlyLast(getBelow(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					removeComputerPiece(movedTo.getPiece());
					p.moveTo(p.getSquare(), movedTo);
					p.setRookFirst(true);
				} else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}
			}
		} else if (movedTo.getIIndex() == p.getSquare().getIIndex()) {
			if (numIdxSide >= 1) {
				if (null == canMove(getLeftSquares(numIdxSide, p))) {
					p.moveTo(p.getSquare(), movedTo);
					p.setRookFirst(true);
				} else if (movedTo == canMove(getLeftSquares(numIdxSide, p))
						&& checkIfOnlyLast(getLeftSquares(numIdxSide, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					System.out.println("left move ran");
					removeComputerPiece(movedTo.getPiece());
					p.moveTo(p.getSquare(), movedTo);
					p.setRookFirst(true);
				} else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}
			} else if (numIdxSide <= -1) {

				if (null == canMove(getRightSquares(numIdxSide, p))) {
					p.moveTo(p.getSquare(), movedTo);
					p.setRookFirst(true);
				} else if (movedTo == canMove(getRightSquares(numIdxSide, p))
						&& checkIfOnlyLast(getRightSquares(numIdxSide, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					removeComputerPiece(movedTo.getPiece());
					p.moveTo(p.getSquare(), movedTo);
					p.setRookFirst(true);
				} else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}
			}

		} else {
			JOptionPane.showMessageDialog(null, "Illegal Move");
			GamePanel.playerTurn = true;
		}
	}
//need to do takes for night

	public void knight(Square movedTo, Piece p) {

		if (p.getSquare().getIIndex() - 2 == movedTo.getIIndex()
				&& p.getSquare().getJIndex() - 1 == movedTo.getJIndex()) {
			if (null == canMoveSingle(movedTo)) {
				if (movedTo.hasPlayer()) {
					removeComputerPiece(movedTo.getPiece());
				}
				p.moveTo(p.getSquare(), movedTo);

			} else {
				JOptionPane.showMessageDialog(null, "Illegal Move");
				GamePanel.playerTurn = true;
			}
		} else if (p.getSquare().getIIndex() - 1 == movedTo.getIIndex()
				&& p.getSquare().getJIndex() - 2 == movedTo.getJIndex()) {
			if (null == canMoveSingle(movedTo)) {
				if (movedTo.hasPlayer()) {
					removeComputerPiece(movedTo.getPiece());
				}
				p.moveTo(p.getSquare(), movedTo);
			} else {
				JOptionPane.showMessageDialog(null, "Illegal Move");
				GamePanel.playerTurn = true;
			}
		} else if (p.getSquare().getIIndex() + 1 == movedTo.getIIndex()
				&& p.getSquare().getJIndex() - 2 == movedTo.getJIndex()) {
			if (null == canMoveSingle(movedTo)) {
				if (movedTo.hasPlayer()) {
					removeComputerPiece(movedTo.getPiece());
				}
				p.moveTo(p.getSquare(), movedTo);
			} else {
				JOptionPane.showMessageDialog(null, "Illegal Move");
				GamePanel.playerTurn = true;
			}
		} else if (p.getSquare().getIIndex() + 2 == movedTo.getIIndex()
				&& p.getSquare().getJIndex() - 1 == movedTo.getJIndex()) {
			if (null == canMoveSingle(movedTo)) {
				if (movedTo.hasPlayer()) {
					removeComputerPiece(movedTo.getPiece());
				}
				p.moveTo(p.getSquare(), movedTo);
			} else {
				JOptionPane.showMessageDialog(null, "Illegal Move");
				GamePanel.playerTurn = true;
			}
		} else if (p.getSquare().getIIndex() + 2 == movedTo.getIIndex()
				&& p.getSquare().getJIndex() + 1 == movedTo.getJIndex()) {
			if (null == canMoveSingle(movedTo)) {
				if (movedTo.hasPlayer()) {
					removeComputerPiece(movedTo.getPiece());
				}
				p.moveTo(p.getSquare(), movedTo);
			} else {
				JOptionPane.showMessageDialog(null, "Illegal Move");
				GamePanel.playerTurn = true;
			}
		} else if (p.getSquare().getIIndex() + 1 == movedTo.getIIndex()
				&& p.getSquare().getJIndex() + 2 == movedTo.getJIndex()) {
			if (null == canMoveSingle(movedTo)) {
				if (movedTo.hasPlayer()) {
					removeComputerPiece(movedTo.getPiece());
				}
				p.moveTo(p.getSquare(), movedTo);
			} else {
				JOptionPane.showMessageDialog(null, "Illegal Move");
				GamePanel.playerTurn = true;
			}
		} else if (p.getSquare().getIIndex() - 1 == movedTo.getIIndex()
				&& p.getSquare().getJIndex() + 2 == movedTo.getJIndex()) {
			if (null == canMoveSingle(movedTo)) {
				if (movedTo.hasPlayer()) {
					removeComputerPiece(movedTo.getPiece());
				}
				p.moveTo(p.getSquare(), movedTo);
			} else {
				JOptionPane.showMessageDialog(null, "Illegal Move");
				GamePanel.playerTurn = true;
			}
		} else if (p.getSquare().getIIndex() - 2 == movedTo.getIIndex()
				&& p.getSquare().getJIndex() + 1 == movedTo.getJIndex()) {
			if (null == canMoveSingle(movedTo)) {
				if (movedTo.hasPlayer()) {
					removeComputerPiece(movedTo.getPiece());
				}
				p.moveTo(p.getSquare(), movedTo);
			} else {
				JOptionPane.showMessageDialog(null, "Illegal Move");
				GamePanel.playerTurn = true;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Illegal Move");
			GamePanel.playerTurn = true;
		}

	}

	public void bishop(Square movedTo, Piece p) {
		// if >1, moving up, if <1, moving down
		int numIdxAbove = p.getSquare().getIIndex() - movedTo.getIIndex();
		if (movedTo.getJIndex() != p.getSquare().getJIndex()
				&& Math.abs(p.getSquare().getIIndex() - movedTo.getIIndex()) == Math
						.abs(p.getSquare().getJIndex() - movedTo.getJIndex())) {
			if (numIdxAbove >= 1 && movedTo.getJIndex() < p.getSquare().getJIndex()) {
				if (null == canMove(getUpLeftDiagonal(numIdxAbove, p))) {
					p.moveTo(p.getSquare(), movedTo);
				} else if (movedTo == canMove(getUpLeftDiagonal(numIdxAbove, p))
						&& checkIfOnlyLast(getUpLeftDiagonal(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					removeComputerPiece(movedTo.getPiece());
					p.moveTo(p.getSquare(), movedTo);
				} else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}
			} else if (numIdxAbove >= 1 && movedTo.getJIndex() > p.getSquare().getJIndex()) {
				if (null == canMove(getUpRightDiagonal(numIdxAbove, p))) {
					p.moveTo(p.getSquare(), movedTo);
				} else if (movedTo == canMove(getUpRightDiagonal(numIdxAbove, p))
						&& checkIfOnlyLast(getUpRightDiagonal(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					removeComputerPiece(movedTo.getPiece());
					p.moveTo(p.getSquare(), movedTo);
				} else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}
			} else if (numIdxAbove <= -1 && movedTo.getJIndex() < p.getSquare().getJIndex()) {
				if (null == canMove(getDownLeftDiagonal(numIdxAbove, p))) {
					p.moveTo(p.getSquare(), movedTo);
				} else if (movedTo == canMove(getDownLeftDiagonal(numIdxAbove, p))
						&& checkIfOnlyLast(getDownLeftDiagonal(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					removeComputerPiece(movedTo.getPiece());
					p.moveTo(p.getSquare(), movedTo);
				} else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}
			} else if (numIdxAbove <= -1 && movedTo.getJIndex() > p.getSquare().getJIndex()) {
				if (null == canMove(getDownRightDiagonal(numIdxAbove, p))) {
					p.moveTo(p.getSquare(), movedTo);
				} else if (movedTo == canMove(getDownRightDiagonal(numIdxAbove, p))
						&& checkIfOnlyLast(getDownRightDiagonal(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					removeComputerPiece(movedTo.getPiece());
					p.moveTo(p.getSquare(), movedTo);
				} else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Illegal Move");
			GamePanel.playerTurn = true;
		}

	}

	public void queen(Square movedTo, Piece p) {
		int numIdxAbove = p.getSquare().getIIndex() - movedTo.getIIndex();
		int numIdxSide = p.getSquare().getJIndex() - movedTo.getJIndex();
		if (movedTo.getJIndex() == p.getSquare().getJIndex()) {
			if (numIdxAbove >= 1) {
				if (null == canMove(getAbove(numIdxAbove, p))) {
					p.moveTo(p.getSquare(), movedTo);
				} else if (movedTo == canMove(getAbove(numIdxAbove, p)) && checkIfOnlyLast(getAbove(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					removeComputerPiece(movedTo.getPiece());
					p.moveTo(p.getSquare(), movedTo);
				} else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}
			} else if (numIdxAbove <= -1) {
				if (null == canMove(getBelow(numIdxAbove, p))) {
					p.moveTo(p.getSquare(), movedTo);
				} else if (movedTo == canMove(getBelow(numIdxAbove, p)) && checkIfOnlyLast(getBelow(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					removeComputerPiece(movedTo.getPiece());
					p.moveTo(p.getSquare(), movedTo);
				} else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}
			}
		} else if (movedTo.getJIndex() != p.getSquare().getJIndex()
				&& Math.abs(p.getSquare().getIIndex() - movedTo.getIIndex()) == Math
						.abs(p.getSquare().getJIndex() - movedTo.getJIndex())) {
			if (numIdxAbove >= 1 && movedTo.getJIndex() < p.getSquare().getJIndex()) {
				if (null == canMove(getUpLeftDiagonal(numIdxAbove, p))) {
					p.moveTo(p.getSquare(), movedTo);
				} else if (movedTo == canMove(getUpLeftDiagonal(numIdxAbove, p))
						&& checkIfOnlyLast(getUpLeftDiagonal(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					removeComputerPiece(movedTo.getPiece());
					p.moveTo(p.getSquare(), movedTo);
				} else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}
			} else if (numIdxAbove >= 1 && movedTo.getJIndex() > p.getSquare().getJIndex()) {
				if (null == canMove(getUpRightDiagonal(numIdxAbove, p))) {
					p.moveTo(p.getSquare(), movedTo);
				} else if (movedTo == canMove(getUpRightDiagonal(numIdxAbove, p))
						&& checkIfOnlyLast(getUpRightDiagonal(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					removeComputerPiece(movedTo.getPiece());
					p.moveTo(p.getSquare(), movedTo);
				} else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}
			} else if (numIdxAbove <= -1 && movedTo.getJIndex() < p.getSquare().getJIndex()) {
				if (null == canMove(getDownLeftDiagonal(numIdxAbove, p))) {
					p.moveTo(p.getSquare(), movedTo);
				} else if (movedTo == canMove(getDownLeftDiagonal(numIdxAbove, p))
						&& checkIfOnlyLast(getDownLeftDiagonal(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					removeComputerPiece(movedTo.getPiece());
					p.moveTo(p.getSquare(), movedTo);
				} else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}
			} else if (numIdxAbove <= -1 && movedTo.getJIndex() > p.getSquare().getJIndex()) {
				if (null == canMove(getDownRightDiagonal(numIdxAbove, p))) {
					p.moveTo(p.getSquare(), movedTo);
				} else if (movedTo == canMove(getDownRightDiagonal(numIdxAbove, p))
						&& checkIfOnlyLast(getDownRightDiagonal(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					removeComputerPiece(movedTo.getPiece());
					p.moveTo(p.getSquare(), movedTo);
				} else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}
			}
		} else if (movedTo.getIIndex() == p.getSquare().getIIndex()) {
			if (numIdxSide >= 1) {
				if (null == canMove(getLeftSquares(numIdxSide, p))) {
					p.moveTo(p.getSquare(), movedTo);
				} else if (movedTo == canMove(getLeftSquares(numIdxSide, p))
						&& checkIfOnlyLast(getLeftSquares(numIdxSide, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					removeComputerPiece(movedTo.getPiece());
					p.moveTo(p.getSquare(), movedTo);
				} else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}
			} else if (numIdxSide <= -1) {

				if (null == canMove(getRightSquares(numIdxSide, p))) {
					p.moveTo(p.getSquare(), movedTo);
				} else if (movedTo == canMove(getRightSquares(numIdxSide, p))
						&& checkIfOnlyLast(getRightSquares(numIdxSide, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					removeComputerPiece(movedTo.getPiece());
					p.moveTo(p.getSquare(), movedTo);
				} else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}
			}

		} else {
			JOptionPane.showMessageDialog(null, "Illegal Move");
			GamePanel.playerTurn = true;
		}

	}

	public void king(Square movedTo, Piece p) {

		int numIdxAbove = p.getSquare().getIIndex() - movedTo.getIIndex();
		int numIdxSide = p.getSquare().getJIndex() - movedTo.getJIndex();
		if (movedTo.getJIndex() == p.getSquare().getJIndex()) {
			if (numIdxAbove == 1) {
				if (null == canMove(getAbove(numIdxAbove, p))) {
					p.moveTo(p.getSquare(), movedTo);
					p.setKingFirst(true);
				} else if (movedTo == canMove(getAbove(numIdxAbove, p)) && checkIfOnlyLast(getAbove(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					removeComputerPiece(movedTo.getPiece());
					p.moveTo(p.getSquare(), movedTo);
					p.setKingFirst(true);
				} else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}
			} else if (numIdxAbove == -1) {
				if (null == canMove(getBelow(numIdxAbove, p))) {
					p.moveTo(p.getSquare(), movedTo);
					p.setKingFirst(true);
				} else if (movedTo == canMove(getBelow(numIdxAbove, p)) && checkIfOnlyLast(getBelow(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					removeComputerPiece(movedTo.getPiece());
					p.moveTo(p.getSquare(), movedTo);
					p.setKingFirst(true);
				} else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}
			} else {
				JOptionPane.showMessageDialog(null, "Illegal Move");
				GamePanel.playerTurn = true;
			}
		} else if (movedTo.getJIndex() != p.getSquare().getJIndex()
				&& Math.abs(p.getSquare().getIIndex() - movedTo.getIIndex()) == Math
						.abs(p.getSquare().getJIndex() - movedTo.getJIndex())) {
			if (numIdxAbove == 1 && movedTo.getJIndex() < p.getSquare().getJIndex()) {
				if (null == canMove(getUpLeftDiagonal(numIdxAbove, p))) {
					p.moveTo(p.getSquare(), movedTo);
					p.setKingFirst(true);
				} else if (movedTo == canMove(getUpLeftDiagonal(numIdxAbove, p))
						&& checkIfOnlyLast(getUpLeftDiagonal(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					removeComputerPiece(movedTo.getPiece());
					p.moveTo(p.getSquare(), movedTo);
					p.setKingFirst(true);
				} else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}
			} else if (numIdxAbove == 1 && movedTo.getJIndex() > p.getSquare().getJIndex()) {
				if (null == canMove(getUpRightDiagonal(numIdxAbove, p))) {
					p.moveTo(p.getSquare(), movedTo);
					p.setKingFirst(true);
				} else if (movedTo == canMove(getUpRightDiagonal(numIdxAbove, p))
						&& checkIfOnlyLast(getUpRightDiagonal(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					removeComputerPiece(movedTo.getPiece());
					p.moveTo(p.getSquare(), movedTo);
					p.setKingFirst(true);
				} else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}
			} else if (numIdxAbove == -1 && movedTo.getJIndex() < p.getSquare().getJIndex()) {
				if (null == canMove(getDownLeftDiagonal(numIdxAbove, p))) {
					p.moveTo(p.getSquare(), movedTo);
					p.setKingFirst(true);
				} else if (movedTo == canMove(getDownLeftDiagonal(numIdxAbove, p))
						&& checkIfOnlyLast(getDownLeftDiagonal(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					removeComputerPiece(movedTo.getPiece());
					p.moveTo(p.getSquare(), movedTo);
					p.setKingFirst(true);
				} else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}
			} else if (numIdxAbove == -1 && movedTo.getJIndex() > p.getSquare().getJIndex()) {
				if (null == canMove(getDownRightDiagonal(numIdxAbove, p))) {
					p.moveTo(p.getSquare(), movedTo);
					p.setKingFirst(true);
				} else if (movedTo == canMove(getDownRightDiagonal(numIdxAbove, p))
						&& checkIfOnlyLast(getDownRightDiagonal(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					removeComputerPiece(movedTo.getPiece());
					p.moveTo(p.getSquare(), movedTo);
					p.setKingFirst(true);
				} else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}
			} else {
				JOptionPane.showMessageDialog(null, "Illegal Move");
				GamePanel.playerTurn = true;
			}

		} else if (movedTo.getIIndex() == p.getSquare().getIIndex()) {

			if (numIdxSide == 1) {
				if (null == canMove(getLeftSquares(numIdxSide, p))) {
					p.moveTo(p.getSquare(), movedTo);
					p.setKingFirst(true);
				} else if (movedTo == canMove(getLeftSquares(numIdxAbove, p))
						&& checkIfOnlyLast(getLeftSquares(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					removeComputerPiece(movedTo.getPiece());
					p.moveTo(p.getSquare(), movedTo);
					p.setKingFirst(true);
				} else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}
			} else if (numIdxSide == -1) {

				if (null == canMove(getRightSquares(numIdxSide, p))) {
					p.moveTo(p.getSquare(), movedTo);
					p.setKingFirst(true);
				} else if (movedTo == canMove(getRightSquares(numIdxAbove, p))
						&& checkIfOnlyLast(getRightSquares(numIdxAbove, p))
						&& !movedTo.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
					removeComputerPiece(movedTo.getPiece());
					p.moveTo(p.getSquare(), movedTo);
					p.setKingFirst(true);
				} else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}
				// check rook king swap
			} 
			
			
			
			
			
			
			
			else if (p.getSquare().getJIndex() == 4 && numIdxSide == -2) {
				if (p.kingFirst() == false && GamePanel.rightRook.rookFirst() == false) {
					if (null == canMove(getRightSquares(numIdxSide, p))) {
						p.moveTo(p.getSquare(), movedTo);
						GamePanel.rightRook.moveTo(GamePanel.rightRook.getSquare(), GamePanel.squares[7][5]);
						GamePanel.rightRook.setRookFirst(true);
						p.setKingFirst(true);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}

			} else if (p.getSquare().getJIndex() == 4 && numIdxSide == 2) {
				if (p.kingFirst() == false && GamePanel.leftRook.rookFirst() == false) {
					if (null == canMove(getLeftSquares(numIdxSide, p))) {
						p.moveTo(p.getSquare(), movedTo);
						GamePanel.leftRook.moveTo(GamePanel.leftRook.getSquare(), GamePanel.squares[7][3]);
						GamePanel.leftRook.setRookFirst(true);
						p.setKingFirst(true);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}

			} 
			
			
			else if(p.getSquare().getJIndex() == 3 && numIdxSide == 2) {
				
				if (p.kingFirst() == false && GamePanel.leftRook.rookFirst() == false) {
					if (null == canMove(getLeftSquares(numIdxSide, p))) {
						p.moveTo(p.getSquare(), movedTo);
						GamePanel.leftRook.moveTo(GamePanel.leftRook.getSquare(), GamePanel.squares[7][2]);
						GamePanel.leftRook.setRookFirst(true);
						p.setKingFirst(true);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}

			
			}else if(p.getSquare().getJIndex() == 3 && numIdxSide == -2) {
				
				if (p.kingFirst() == false && GamePanel.rightRook.rookFirst() == false) {
					if (null == canMove(getRightSquares(numIdxSide, p))) {
						p.moveTo(p.getSquare(), movedTo);
						GamePanel.rightRook.moveTo(GamePanel.rightRook.getSquare(), GamePanel.squares[7][4]);
						GamePanel.rightRook.setRookFirst(true);
						p.setKingFirst(true);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Illegal Move");
					GamePanel.playerTurn = true;
				}
			
			
			
			
			}	
			
			else {
				JOptionPane.showMessageDialog(null, "Illegal Move");
				GamePanel.playerTurn = true;
			}

		} else {
			JOptionPane.showMessageDialog(null, "Illegal Move");
			GamePanel.playerTurn = true;
		}

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
		if (s.hasPlayer() == true && s.getPiece().getColor().equalsIgnoreCase(GamePanel.userColor)) {
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

	public void removeComputerPiece(Piece p) {
		GamePanel.piecesUserTook.add(p);
		System.out.println("Player has taken " + GamePanel.piecesUserTook.size() + " pieces from the computer.");
		if (GamePanel.userColor.equalsIgnoreCase("White")) {
			ArrayList<Piece> temp = new ArrayList<Piece>();
			for (int i = 0; i < GamePanel.blackPieces.length; i++) {
				if (p.equals(GamePanel.blackPieces[i])) {

				} else {

					temp.add(GamePanel.blackPieces[i]);
				}
			}
			Piece[] temp2 = new Piece[temp.size()];
			// System.out.println(temp.size());
			for (int i = 0; i < temp2.length; i++) {
				temp2[i] = temp.get(i);
			}

			GamePanel.blackPieces = temp2;

		} else if (GamePanel.userColor.equalsIgnoreCase("Black")) {
			ArrayList<Piece> temp = new ArrayList<Piece>();
			for (int i = 0; i < GamePanel.whitePieces.length; i++) {
				if (p.equals(GamePanel.whitePieces[i])) {

				} else {

					temp.add(GamePanel.whitePieces[i]);
				}
			}
			Piece[] temp2 = new Piece[temp.size()];

			for (int i = 0; i < temp2.length; i++) {
				temp2[i] = temp.get(i);
			}

			GamePanel.whitePieces = temp2;

		}
	}

	public boolean checkIfOnlyLast(Square[] a) {

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


