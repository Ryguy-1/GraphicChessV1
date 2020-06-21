
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements MouseListener, ActionListener {

	public static final int boardSize = 8;
	public static final int squareSize = 100;

	public static final int offset = 10;

	public static String userColor = "";
	
	public static boolean playerTurn;

	public static int tempPieceIdx = 0;
	private Piece tempPiece;

	public static Piece rightRook;
	public static Piece leftRook;
	
	public static Piece computerLeftRook;
	public static Piece computerRightRook;
	
	

	private boolean pieceClickedBoolean = false;

	public static Piece[] blackPieces = new Piece[16];
	public static Piece[] whitePieces = new Piece[16];
	
	private boolean computerDone;

	public static ArrayList<Piece> piecesComputerTook = new ArrayList<Piece>();
	public static ArrayList<Piece> piecesUserTook = new ArrayList<Piece>();

	// from top left to bottom right
	public static final String[] namesOfBlackPiecesInOrder = { "Black Rook.png", "Black Knight.png", "Black Bishop.png",
			"Black Queen.png", "Black King.png", "Black Bishop.png", "Black Knight.png", "Black Rook.png",
			"Black Pawn.png", "Black Pawn.png", "Black Pawn.png", "Black Pawn.png", "Black Pawn.png", "Black Pawn.png",
			"Black Pawn.png", "Black Pawn.png" };
	public static final String[] namesOfWhitePiecesInOrder = { "White Pawn.png", "White Pawn.png", "White Pawn.png",
			"White Pawn.png", "White Pawn.png", "White Pawn.png", "White Pawn.png", "White Pawn.png", "White Rook.png",
			"White Knight.png", "White Bishop.png", "White Queen.png", "White King.png", "White Bishop.png",
			"White Knight.png", "White Rook.png" };

	public static Square[][] squares = new Square[boardSize][boardSize];

	UserRuleCheck s;
	ComputerTurn c;

	GamePanel() {

		while (!userColor.equalsIgnoreCase("White") && !userColor.equalsIgnoreCase("Black")) {
			userColor = JOptionPane.showInputDialog("Enter White or Black to indicate your color: ");

		}
		
		computerDone = true;

		initializeSquareArray();
		initializePieces();
		s = new UserRuleCheck();
		c = new ComputerTurn();
		if (userColor.equalsIgnoreCase("White")) {
			playerTurn = true;
		} else {
			playerTurn = false;
			c.run();
			computerDone = false;
		}

	}

	void startSim() {
		JOptionPane.showMessageDialog(null,
				"Please press OK to confirm you accept the consequences of using a high powered AI in competitive play.");

	}

	public void paintComponent(Graphics g) {
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares.length; j++) {
				squares[i][j].draw(g);
			}
		}

		for (int i = 0; i < blackPieces.length; i++) {
			blackPieces[i].draw(g);
		}
		for (int i = 0; i < whitePieces.length; i++) {
			whitePieces[i].draw(g);
		}

	}

	public void initializeSquareArray() {
		int tempY = 0;
		Color current = Color.WHITE;
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[i].length; j++) {
				squares[i][j] = new Square(j * squareSize, tempY, current);
				if (current == Color.WHITE && j != 7) {
					current = Color.BLACK;
				} else if (j != 7) {
					current = Color.WHITE;
				}

				if (i == 0 || i == 1 || i == 6 || i == 7) {
					squares[i][j].setHasPlayer(true);
				}
			}

			tempY += squareSize;
		}

	}

	public void initializePieces() {

		if (userColor.equalsIgnoreCase("White")) {

			int tempY = 10;
			int xCounter = 0;
			for (int i = 0; i < namesOfBlackPiecesInOrder.length; i++) {
				blackPieces[i] = new Piece((offset + (xCounter * squareSize)), tempY, squareSize - 20, squareSize - 20,
						namesOfBlackPiecesInOrder[i], "Black");
				blackPieces[i].setBufferedImage(getBufferedImage(blackPieces[i].getSource()));

				xCounter++;
				if (i == 7) {
					tempY += squareSize;
					xCounter = 0;
				}
			}

			tempY = 10 + 6 * squareSize;
			xCounter = 0;
			for (int i = 0; i < namesOfWhitePiecesInOrder.length; i++) {
				whitePieces[i] = new Piece((offset + (xCounter * squareSize)), tempY, squareSize - 20, squareSize - 20,
						namesOfWhitePiecesInOrder[i], "White");
				whitePieces[i].setBufferedImage(getBufferedImage(whitePieces[i].getSource()));

				xCounter++;
				if (i == 7) {
					tempY += squareSize;
					xCounter = 0;
				}
			}

			rightRook = whitePieces[15];
			leftRook = whitePieces[8];
			
			computerRightRook = blackPieces[7];
			computerLeftRook = blackPieces[0];
			

		} else if (userColor.equalsIgnoreCase("Black")) {

			String[] whiteNew = reverseStringArray(namesOfWhitePiecesInOrder);
			String[] blackNew = reverseStringArray(namesOfBlackPiecesInOrder);

			int offset = 10;
			int tempY = 10;
			int xCounter = 0;
			for (int i = 0; i < whiteNew.length; i++) {
				whitePieces[i] = new Piece((offset + (xCounter * squareSize)), tempY, squareSize - 20, squareSize - 20,
						whiteNew[i], "White");
				whitePieces[i].setBufferedImage(getBufferedImage(whitePieces[i].getSource()));

				xCounter++;
				if (i == 7) {
					tempY += squareSize;
					xCounter = 0;
				}
			}

			offset = 10;
			tempY = 10 + 6 * squareSize;
			xCounter = 0;
			for (int i = 0; i < blackNew.length; i++) {
				blackPieces[i] = new Piece((offset + (xCounter * squareSize)), tempY, squareSize - 20, squareSize - 20,
						blackNew[i], "Black");
				blackPieces[i].setBufferedImage(getBufferedImage(blackPieces[i].getSource()));

				xCounter++;
				if (i == 7) {
					tempY += squareSize;
					xCounter = 0;
				}
			}

			rightRook = blackPieces[15];
			leftRook = blackPieces[8];
			
			computerRightRook = whitePieces[7];
			computerLeftRook = whitePieces[0];

		}

	}

	public BufferedImage getBufferedImage(String source) {

		try {
			BufferedImage img = ImageIO.read(this.getClass().getResourceAsStream(source));
			return img;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Image Not Found...");
			return null;
		}

	}

	public String[] reverseStringArray(String[] x) {
		ArrayList<String> temp = new ArrayList<String>(x.length);
		for (int i = x.length - 1; i >= 0; i--) {
			temp.add(x[i]);
		}
		String[] returned = new String[x.length];
		for (int i = 0; i < x.length; i++) {
			returned[i] = temp.get(i);
		}

		return returned;

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		
		if (playerTurn == true) {
			computerDone = true;
			if (pieceClickedBoolean == false) {

				for (int i = 0; i < blackPieces.length; i++) {

					if ((e.getButton() == 1) && blackPieces[i].contains(e.getX(), e.getY())) {
						repaint();
						System.out.println("black " + i + " pressed");
						blackPieces[i].setClicked(true);
						pieceClickedBoolean = true;
						tempPieceIdx = i;
						tempPiece = blackPieces[i];
					}

				}

				for (int i = 0; i < whitePieces.length; i++) {

					if ((e.getButton() == 1) && whitePieces[i].contains(e.getX(), e.getY())) {
						repaint();
						System.out.println("white " + i + " pressed");
						whitePieces[i].setClicked(true);
						pieceClickedBoolean = true;
						tempPieceIdx = i;
						tempPiece = whitePieces[i];
					}

				}

			} else if (pieceClickedBoolean == true) {
				System.out.println("piece was clicked");
				for (int i = 0; i < squares.length; i++) {
					for (int j = 0; j < squares[i].length; j++) {

						if ((e.getButton() == 1) && squares[i][j].contains(e.getX(), e.getY())
								&& squares[i][j].getPiece() != tempPiece && squares[i][j].getPiece() != tempPiece) {
							repaint();
							System.out.println("sqare [" + i + "][" + j + "] pressed");
							playerTurn = false;
							RuleCheckAll ruleCheck = new RuleCheckAll();
							if (userColor.equalsIgnoreCase("White")) {
								if (false == ruleCheck.checkPlayerPossibilities(tempPiece, squares[i][j],
										rightRook, leftRook, "User")) {
									playerTurn = true;
									JOptionPane.showMessageDialog(null, "Invalid Move.");
								} else {
									System.out.println("can move");
									if (ruleCheck.getPieceRemoved() != null) {
										removeComputerPiece(ruleCheck.getPieceRemoved());
									}
									tempPiece.moveTo(tempPiece.getSquare(), squares[i][j]);
									c.run();
								}
							} else if (userColor.equalsIgnoreCase("Black")) {
								if (false == ruleCheck.checkPlayerPossibilities(tempPiece, squares[i][j],
										rightRook, leftRook, "User")) {
									playerTurn = true;
									JOptionPane.showMessageDialog(null, "Invalid Move.");
								} else {
									if (ruleCheck.getPieceRemoved() != null) {
										removeComputerPiece(ruleCheck.getPieceRemoved());
									}
									tempPiece.moveTo(tempPiece.getSquare(), squares[i][j]);
									c.run();
								}
							}
						}

						repaint();
					}

				}
				pieceClickedBoolean = false;
				tempPieceIdx = 0;
				tempPiece = null;

			}

		} else

		{
			JOptionPane.showMessageDialog(null, "Please Wait for Computer to Make Move.");
			if(computerDone == true) {
				c.run();
				computerDone = false;
			}
		}

	}

	private void removeComputerPiece(Piece removedPiece) {
//		if (removedPiece.getColor().equalsIgnoreCase("Black")) {
//			for (int k = 0; k < blackPieces.length; k++) {
//				if (removedPiece == blackPieces[k]) {
//					piecesUserTook.add(removedPiece);
//					Piece[] temp = new Piece[blackPieces.length - 1];
//					ArrayList<Piece> tempPcs = new ArrayList<Piece>();
//					for (int l = 0; l < temp.length; l++) {
//						if (blackPieces[k] == removedPiece) {
//						} else {
//							tempPcs.add(blackPieces[k]);
//						}
//					}
//					for (int l2 = 0; l2 < temp.length; l2++) {
//						temp[l2] = tempPcs.get(l2);
//					}
//					blackPieces = temp;
//				}
//			}
//		} else if (removedPiece.getColor().equalsIgnoreCase("White")) {
//			for (int k = 0; k < whitePieces.length; k++) {
//				if (removedPiece == whitePieces[k]) {
//					piecesUserTook.add(removedPiece);
//					Piece[] temp = new Piece[whitePieces.length - 1];
//					ArrayList<Piece> tempPcs = new ArrayList<Piece>();
//					for (int l = 0; l < temp.length; l++) {
//						if (whitePieces[k] == removedPiece) {
//						} else {
//							tempPcs.add(whitePieces[k]);
//						}
//					}
//					for (int l2 = 0; l2 < temp.length; l2++) {
//						temp[l2] = tempPcs.get(l2);
//					}
//					whitePieces = temp;
//				}
//			}
//		}
//		

		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		GamePanel.piecesUserTook.add(removedPiece);
		System.out.println("Player has taken " + GamePanel.piecesUserTook.size() + " pieces from the computer.");
		if (removedPiece.getColor().equalsIgnoreCase("Black")) {
			ArrayList<Piece> temp = new ArrayList<Piece>();
			for (int i = 0; i < GamePanel.blackPieces.length; i++) {
				if (removedPiece.equals(GamePanel.blackPieces[i])) {

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

		} else if (removedPiece.getColor().equalsIgnoreCase("White")) {
			ArrayList<Piece> temp = new ArrayList<Piece>();
			for (int i = 0; i < GamePanel.whitePieces.length; i++) {
				if (removedPiece.equals(GamePanel.whitePieces[i])) {

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

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
