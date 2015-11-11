package jason.smythe.uct.csc2003.states;

import jason.smythe.uct.csc2003.constants.Movement;
import jason.smythe.uct.csc2003.entities.Player;
import jason.smythe.uct.csc2003.util.Square;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class TouchInputState implements InputProcessor {
	public Vector2 centre;
	public Vector2 movedTo;
	public boolean active;
	public Movement currentDirection;
	ShapeRenderer shapeRenderer;
	private int screenHeight;
	private Player p;
	private float touchTime;
	
	public TouchInputState(Player player) {
		shapeRenderer = new ShapeRenderer();
		centre = new Vector2();
		movedTo = new Vector2();
		screenHeight = Gdx.graphics.getHeight();
		p = player;
	}
	
	public void draw() {
		Gdx.gl.glEnable(GL20.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		if (active) {
		    shapeRenderer.setColor(new Color(0, 0, 1, 0.3f));
		    shapeRenderer.circle(centre.x, centre.y, 200);
		    shapeRenderer.setColor(new Color(0, 1, 0, 0.7f));
		    shapeRenderer.circle(movedTo.x, movedTo.y, 50);
//		    shapeRenderer.rect(0, 0, playController.w, playController.h);
		    shapeRenderer.setColor(new Color(1, 1, 0, 0.7f));
		    
		    if (p.state.moveState != Movement.STATIONARY) drawSelectedDirection(190);
		}
		shapeRenderer.setColor(new Color(1, 0, 0, 1));
		drawDirectionArrow(p.state.moveState);
		shapeRenderer.setColor(new Color(1, 1, 1, 1));
		drawDirectionArrow(p.state.nextMoveState);
		shapeRenderer.end();
	    Gdx.gl.glDisable(GL20.GL_BLEND);
	}
	
	private void drawSelectedDirection(float radius) {
		float start = 0;
//		Gdx.app.log("NextMove", "is: " + p.state.nextMoveState);
		switch (p.state.nextMoveState) {
		case UP:
			start = 45;
			break;
		case DOWN:
			start = 225;
			break;
		case LEFT:
			start = 135;
			break;
		case RIGHT:
			start = -45;
			break;
		}
		shapeRenderer.arc(centre.x, centre.y, radius, start, 90);
	}

	private void drawDirectionArrow(Movement moveState) {
		float xPlus = 0, yPlus = 0;
		switch (moveState) {
		case UP:
			yPlus = Square.squareSize;
			break;
		case DOWN:
			yPlus = -Square.squareSize;
			break;
		case LEFT:
			xPlus = -Square.squareSize;
			break;
		case RIGHT:
			xPlus = Square.squareSize;
			break;
		}
		shapeRenderer.rectLine(p.state.position.x, p.state.position.y, p.state.position.x + xPlus, p.state.position.y + yPlus, 10);
	}

	public void touch(int screenX, int screenY) {
		touchTime = Gdx.graphics.getDeltaTime();
		centre.set(screenX, screenHeight - screenY);
		movedTo.set(screenX, screenHeight - screenY);
		active = true;
	}
	
	public void letGo() {
		if (p.state.isOnGear() && centre.dst2(movedTo) < 20) {
			p.jumpOnBelt();
		}
		active = false;
	}
	
	public Movement updatePosition(int x, int y) {
		
		if (active) movedTo.set(x, screenHeight - y);
		else active = true;
		
		if ( centre.dst2(movedTo) > 20) {
//			Gdx.app.log("angle!", "success!!!");
			return setNextDirection();
		}
		return null;
	}

	private Movement setNextDirection() {
		float changeX = centre.x - movedTo.x;
		float changeY = centre.y - movedTo.y;
		if( changeX < changeY ) { //down, right
			if ( changeX + changeY < 0 ) {
				return Movement.RIGHT;
			} else {
				return Movement.DOWN;
			}
		} else {
			if ( changeX + changeY < 0 ) {
				return Movement.UP;
			} else {
				return Movement.LEFT;
			}
		}
	}
	
//	// keyboard input controller
//	@Override
	public boolean keyDown(int keycode) {
//////		System.out.println("KEY PRESSED" + keycode);
		switch(keycode){
		case Keys.UP:
			if (p.isOnBelt) {
				p.state.moveState = Movement.UP;
			} else {
				p.state.movement(Movement.UP);
			}
			break;
		case Keys.DOWN:
			if (p.isOnBelt) {
				p.state.moveState = Movement.DOWN;
			} else {
				p.state.movement(Movement.DOWN);
			}
			break;
		case Keys.LEFT:
			if (p.isOnBelt) {
				p.state.moveState = Movement.LEFT;
			} else {
				p.state.movement(Movement.LEFT);
			}
			break;
		case Keys.RIGHT:
			if (p.isOnBelt) {
				p.state.moveState = Movement.RIGHT;
			} else {
				p.state.movement(Movement.RIGHT);
			}
			break;
		case Keys.SPACE:
//			System.out.println("pressing space!!");
			if (p.state.isOnGear()) {
//				state.printState("Before");
				System.out.println("Is juming on Belt");
				p.jumpOnBelt();
//				state.printState("After");
			}
			break;
		}
		return true;
//		return false;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		touch(screenX, screenY);
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		letGo();
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
//		Gdx.app.log("touch direction", "" + touchInput.updatePosition(screenX, screenY) );
		Movement inPut = updatePosition(screenX, screenY);
		if (inPut == null) return false;
		
		switch(inPut){
		case UP:
			if (p.isOnBelt) {
				p.state.moveState = Movement.UP;
			} else {
				p.state.movement(Movement.UP);
			}
			break;
		case DOWN:
			if (p.isOnBelt) {
				p.state.moveState = Movement.DOWN;
			} else {
				p.state.movement(Movement.DOWN);
			}
			break;
		case LEFT:
			if (p.isOnBelt) {
				p.state.moveState = Movement.LEFT;
			} else {
				p.state.movement(Movement.LEFT);
			}
			break;
		case RIGHT:
			if (p.isOnBelt) {
				p.state.moveState = Movement.RIGHT;
			} else {
				p.state.movement(Movement.RIGHT);
			}
			break;
		}
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
