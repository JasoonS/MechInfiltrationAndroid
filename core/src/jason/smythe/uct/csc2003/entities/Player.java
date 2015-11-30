package jason.smythe.uct.csc2003.entities;

import jason.smythe.uct.csc2003.constants.Movement;
import jason.smythe.uct.csc2003.desktop.maps.MapsController;
import jason.smythe.uct.csc2003.states.GearState;
import jason.smythe.uct.csc2003.states.TouchInputState;
import jason.smythe.uct.csc2003.util.Square;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity {
	
	GearState gearState;
	public boolean isOnBelt;
	private Vector2 beltMovement;
	public Vector2 goalPosition;
	public Vector2 midPointPos;
	private Sprite goalKey;
	public TouchInputState touchInput;
	
	public Player(Sprite sprite, MapsController maps, int startX, int startY, int goalX, int goalY) {
		super(sprite, maps, startX, startY);
		
		isOnBelt = false;
		
		state.nextPosition = state.nextSquare.squareToPos();
		
		goalPosition = (new Square(goalX, goalY)).squareToPos();
		goalKey = new Sprite(new Texture("img/key.png"));
		goalKey.setPosition(goalPosition.x, goalPosition.y);
		
		midPointPos = new Vector2(state.nextPosition);
		
		touchInput = new TouchInputState(this);
	}
	
	public void draw(SpriteBatch batch){
		super.draw(batch);
		goalKey.draw(batch);
		update(Gdx.graphics.getDeltaTime());
	}
	
	//Update the players state, and move the player.
	protected void update(float deltaTime) {
		if( isOnBelt ) {
			if ( state.atGoalBlock() ){
				if ( state.isOnGear() ) {
					isOnBelt = false;
					state.position = state.curSquare.squareToPos();
//					state.printState("No longer on belt");
				}
			} else { 
				moveOnBelt(deltaTime);
			}
		} else {
			if ( state.atGoalBlock() ){
				state.updateState();
				state.position = state.curSquare.squareToPos();
				if ( state.isOnGear() ) {
					state.moveState = Movement.STATIONARY;
					// TODO: experiment with this. Some players may appreciate being able to 'swipe past' gears before reaching them.
//					state.nextMoveState = Movement.STATIONARY; 
				}
			} else { 
				state.move(speed, deltaTime);
			}
		}
		this.setPosition(state.position.x, state.position.y);
		
		updateMidPointPos();
	}
	
	private void updateMidPointPos() {
		midPointPos.x = (state.position.x + goalPosition.x) / (float) 2; 
		midPointPos.y = (state.position.y + goalPosition.y) / (float) 2;

	}

	public boolean playerHasGottenToKey() {
		return (state.position.dst2(goalPosition) < 46);
	}

	private void moveOnBelt(float deltaTime) {
		state.position.add(beltMovement.x * deltaTime, beltMovement.y * deltaTime);
	}
	
	// sets player in the appropriate motion according to where he tries to get onto the belt.
	public void jumpOnBelt() {
		System.out.println("THE STATE ON THE BELT IS: " + GearState.gearBelts[state.curSquare.x][state.curSquare.y]);
		int nextX = 0;
		int nextY = 0;
		switch(GearState.gearBelts[state.curSquare.x][state.curSquare.y]) {
		case START_R: 
			beltMovement = new Vector2(super.speed, super.speed);
			nextX = 2;
			nextY = 2;
			break;
		case START_L: 
			beltMovement = new Vector2( -super.speed, super.speed);
			nextX = -2;
			nextY = 2;
			break;
		case END_R: 
			beltMovement = new Vector2(-super.speed, -super.speed);
			nextX = -2;
			nextY = -2;
			break;
		case END_L:
			beltMovement = new Vector2(super.speed, -super.speed);
			nextX = 2;
			nextY = -2;
			break;
		default:
			break;
		}
		state.position = state.curSquare.squareToPos();
		state.nextSquare.update(state.curSquare, nextX, nextY);
		state.curSquare.transferValues(state.nextSquare);
		state.nextPosition = state.nextSquare.squareToPos();
		state.moveState = Movement.STATIONARY;
		state.nextMoveState = Movement.STATIONARY;
		isOnBelt = true;
	}
}