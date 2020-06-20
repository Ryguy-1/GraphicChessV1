
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Piece {

	private int x;
	private int y;
	private int width;
	private int height;
	private String source;
	private BufferedImage img;
	private boolean clicked = false;
	private boolean pawnHasDoneFirstMove;
	private String color = "";
	private boolean kingHasDoneFirstMove;
	private boolean rookHasDoneFirstMove;
	private int value;

	Piece(int x, int y, int width, int height, String source, String color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.source = source;
		if (source.contains("Pawn")) {
			pawnHasDoneFirstMove = false;
		}else if(source.contains("King")) {
			kingHasDoneFirstMove = false;
		}else if(source.contains("Rook")) {
			rookHasDoneFirstMove = false;
		}
		this.color = color;
		
		
		
		
		
		if(this.source.contains("Pawn")) {
			value = 1;
		}else if(this.source.contains("Knight") || this.source.contains("Bishop")) {
			value = 3;
		}else if(this.source.contains("Rook")) {
			value = 5;
		}else if(this.source.contains("Queen")) {
			value = 9;
		}else if(this.source.contains("King")) {
			value = 10000;
		}
		
		
		
		

	}

	public void draw(Graphics g) {
		g.drawImage(img, x, y, width, height, null);
	}

	public boolean contains(int mousex, int mousey) {

		if (mousex >= x && mousex <= x + width && mousey >= y && mousey <= y + height) {
			return true;
		}
		return false;

	}
	
	
	
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}

	// getters
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getSource() {
		return source;
	}

	public BufferedImage getImage() {
		return img;
	}

	public boolean wasClicked() {
		return clicked;
	}

	public boolean pawnFirst() {
		return pawnHasDoneFirstMove;
	}
	
	public boolean kingFirst() {
		return kingHasDoneFirstMove;
	}
	
	public boolean rookFirst() {
		return rookHasDoneFirstMove;
	}

	public void setPawnFirst(boolean hasDoneFirstMove) {
		this.pawnHasDoneFirstMove = hasDoneFirstMove;
	}
	public void setKingFirst(boolean hasDoneFirstMove) {
		this.kingHasDoneFirstMove = hasDoneFirstMove;
	}
	public void setRookFirst(boolean hasDoneFirstMove) {
		this.rookHasDoneFirstMove = hasDoneFirstMove;
	}

	public void moveTo(Square movedFrom, Square movedTo) {

		y = movedTo.getIIndex() * GamePanel.squareSize + GamePanel.offset;
		x = movedTo.getJIndex() * GamePanel.squareSize + GamePanel.offset;

		movedFrom.setHasPlayer(false);
		movedTo.setHasPlayer(true);

	}

	public String getColor() {
		return color;
	}

	public Square getSquare() {

		for (int i = 0; i < GamePanel.squares.length; i++) {
			for (int j = 0; j < GamePanel.squares[i].length; j++) {
				if (GamePanel.squares[i][j].contains(x, y)) {

					return GamePanel.squares[i][j];

				}
			}
		}
		return null;
	}

	// Setters
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setBufferedImage(BufferedImage img) {
		this.img = img;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	public void addX(int addedX) {
		x += addedX;
	}

	public void addY(int addedY) {
		y += addedY;
	}

}

