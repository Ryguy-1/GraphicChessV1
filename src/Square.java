
import java.awt.Color;
import java.awt.Graphics;

public class Square {

	private int x;
	private int y;
	private boolean hasPlayer;
	Color color;

	Square(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	public void draw(Graphics g) {
		g.setColor(this.color);
		g.fillRect(x, y, GamePanel.squareSize, GamePanel.squareSize);

	}

	public boolean contains(int mousex, int mousey) {

		if (mousex >= x && mousex <= x + GamePanel.squareSize && mousey >= y && mousey <= y + GamePanel.squareSize) {
			return true;
		}
		return false;

	}

	public int getIIndex() {
		for (int i = 0; i < GamePanel.squares.length; i++) {
			for (int j = 0; j < GamePanel.squares[i].length; j++) {
				if (this.equals(GamePanel.squares[i][j])) {
					return i;
				}
			}
		}
		return 0;
	}

	public int getJIndex() {
		for (int i = 0; i < GamePanel.squares.length; i++) {
			for (int j = 0; j < GamePanel.squares[i].length; j++) {
				if (this.equals(GamePanel.squares[i][j])) {
					return j;
				}
			}
		}
		return 0;
	}

	public Piece getPiece() {
		for (int i = 0; i < GamePanel.whitePieces.length; i++) {
			if (this.contains(GamePanel.whitePieces[i].getX(), GamePanel.whitePieces[i].getY())) {
				return GamePanel.whitePieces[i];
			}
		}
		for (int i = 0; i < GamePanel.blackPieces.length; i++) {
			if (this.contains(GamePanel.blackPieces[i].getX(), GamePanel.blackPieces[i].getY())) {
				return GamePanel.blackPieces[i];
			}
		}
		return null;
	}

	public boolean hasPlayer() {
		return hasPlayer;
	}

	public void setHasPlayer(boolean hasPlayer) {
		this.hasPlayer = hasPlayer;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}


