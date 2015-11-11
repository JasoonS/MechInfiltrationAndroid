package jason.smythe.uct.csc2003.desktop.maps;

import jason.smythe.uct.csc2003.constants.SquareType;
import jason.smythe.uct.csc2003.util.Square;

public class GridSquareDetail extends Square {

	public SquareType squareType;
	
	public boolean upCan;
	public boolean downCan;
	public boolean rightCan;
	public boolean leftCan;
//	public GridSquareDetail up;
//	public GridSquareDetail down;
//	public GridSquareDetail right;
//	public GridSquareDetail left;
	
	public GridSquareDetail(int x, int y, SquareType squareType) {
		super(x, y);
		this.squareType = squareType;
		this.upCan = downCan = rightCan = leftCan = false;
	}

}
