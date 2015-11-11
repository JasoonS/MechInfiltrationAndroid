package jason.smythe.uct.csc2003.util;

import com.badlogic.gdx.math.Vector2;

public class Square {
	public int x;
	public int y;
	public static int xOffset;
	public static int yOffset;
	public static float squareSize;
	public static float scale;
	public static final int originalSquareSize = 50;
	
	public Square(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void update(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void update(Square square, int i, int j) {
		this.x = square.x + i;
		this.y = square.y + j;
	}
	
	public Vector2 squareToPos() {		
		return new Vector2((float) (x * squareSize) + xOffset, (float) (y * squareSize) + yOffset);
	}

	//is this needed?
	public Vector2 squareToCenterPos() {		
		return new Vector2((float) (x * squareSize), (float) (y * squareSize));
	}
	
	public String toString(){
		return "(x: " + x + " , y: " + y + " )";
	}

	public static Square getSquare(int xP, int yP) {
		return new Square((int) ((xP - xOffset) / squareSize), (int) ((yP - yOffset) / squareSize));
	}

	public static Square getSquare(float f, float g) {
		return new Square((int) ((f - xOffset) / squareSize), (int) ((g - yOffset) / squareSize));
	}
	
	public static void setOffset(int x, int y, float newSquareSize) {
		xOffset = x;
		yOffset = y;
		squareSize = newSquareSize;
		scale = squareSize / originalSquareSize;
		System.out.println("THE SCALE IS: " + scale);
	}

	public void transferValues(Square otherSquare) {
		this.x = otherSquare.x;
		this.y = otherSquare.y;
	}
}