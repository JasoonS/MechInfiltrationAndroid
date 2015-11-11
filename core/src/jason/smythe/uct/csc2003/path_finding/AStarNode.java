package jason.smythe.uct.csc2003.path_finding;

import jason.smythe.uct.csc2003.constants.Movement;
import jason.smythe.uct.csc2003.constants.SquareType;
import jason.smythe.uct.csc2003.desktop.maps.GridSquareDetail;
import jason.smythe.uct.csc2003.states.AStarSquareResultState;
import jason.smythe.uct.csc2003.util.Square;

public class AStarNode extends GridSquareDetail implements Comparable<AStarNode> {
	
	// TODO: replace with movement
	public boolean searchable;
	public Movement moveDirectionTo; //the direction of movement the algorithm took to GET TO this node.
	
	public AStarNode previous;
	
	public int g;
	public int h;
	public float enemyBreadCrumbs;
	public float breadCrumbWeight;
	public static float breadCrumbWeightIncrement;
	public float f;

	public AStarNode(int x, int y, SquareType squareType) {
		super(x,y,squareType);
		this.squareType = squareType;
		moveDirectionTo = Movement.STATIONARY;
		breadCrumbWeight = 0;
	}

	@Override
	public int compareTo(AStarNode otherNode) {
		//if both nodes have the same f value, I compare them on their h value next.
		if (this.f < otherNode.f) {
			return -1;
		} else if (this.f > otherNode.f) {
			return 1;
		} else if (this.h < otherNode.h) { //These next two checks not strictly necessary, however sort the priority Queue slightly more.
			return -1;
		} else if (this.h > otherNode.h) {
			return 1;
		}
		return 0;
	}

	public void setCostsAndPrevious(AStarNode node, AStarNode target, Movement moveDirectionTo) {
		previous = node;
		this.moveDirectionTo = moveDirectionTo;
		
		g = node.g + 1;
		
		enemyBreadCrumbs = node.enemyBreadCrumbs + breadCrumbWeight;
		
		h = Math.abs(this.x - target.x) + Math.abs(this.y - target.y);
		
		f = g + h + enemyBreadCrumbs;
	}
	
	public void incrementBreadCrumbWeight() {
		breadCrumbWeight += breadCrumbWeightIncrement;
	}

	public AStarSquareResultState returnResultForm() {
		return new AStarSquareResultState((Square) this, moveDirectionTo);
	}
	
	public String toString(){
		return this + ", g: " + g + ", h: " + h + ", breadCrumb: " + enemyBreadCrumbs + ", f: " + f + ", direction: " + moveDirectionTo;
	}

	public void reset() {
		moveDirectionTo = Movement.STATIONARY;
	}

	public void resetBreadCrumbs() {
		breadCrumbWeight = 0;
	}

	public boolean canMoveInDirecton(Movement direction) {
		switch(direction) {
		case UP:
			return upCan;
		case DOWN:
			return downCan;
		case LEFT:
			return leftCan;
		case RIGHT:
			return rightCan;
		}
		return false;
	}
}
