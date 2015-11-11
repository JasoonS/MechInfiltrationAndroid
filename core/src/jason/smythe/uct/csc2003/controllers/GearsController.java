package jason.smythe.uct.csc2003.controllers;

import java.util.ArrayList;

import jason.smythe.uct.csc2003.constants.GearBelts;
import jason.smythe.uct.csc2003.screens.ConfirmationScreen;
import jason.smythe.uct.csc2003.states.GearState;
import jason.smythe.uct.csc2003.util.GameSoundController;
import jason.smythe.uct.csc2003.util.Square;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class GearsController implements GestureListener {
	
	GearState gearState;
	private Texture gear;
	private Texture gearSelected;
	public ArrayList<Square> highlighted;
	public final int totalNumOfBelts;
	public int numOfBelts;
	private GameSoundController sound;
	private PlayController playController;
	private Timer timer;
	public boolean stillSelecting = true;

	
	public GearsController (GearState gearState, int totalNumOfBelts, GameSoundController sound, PlayController playController) {
		this.sound = sound;
		this.gearState = gearState;
		this.playController = playController;
		timer = new Timer();
		
		gear = new Texture("img/gear.png");
		gearSelected = new Texture("img/highlightedGear.png");
		
		this.totalNumOfBelts = totalNumOfBelts;
		numOfBelts = totalNumOfBelts;
		
		highlighted = new ArrayList<Square>();
	}
	
	//check if gear at that position, then check if another gear has been touch, then check if suitable to connect the two gears.
	public void makeGearTouch(float x, float y) {
		float halfSquare = (float) (Square.squareSize / 2.3);
		int doubleSquare = (int) Square.squareSize * 2;
		Square square = new Square( (((int)(x - halfSquare) / doubleSquare) * 2) + 1, (((int)(y - halfSquare) / doubleSquare) * 2) + 1);
		
		Gdx.app.log("Square Generated", square.toString());
		
		if( square.x > 0 && square.y > 0 && square.x < GearState.gearBelts.length && square.y < GearState.gearBelts[0].length ) {
			if (highlighted.size() > 0) {
				GearState.gearBelts[square.x][square.y] = GearBelts.GEAR_SELECTED;
				Square compare = highlighted.get(0);
				if ( Math.abs(square.x - compare.x) == 2 && Math.abs(square.y - compare.y) == 2 && square.x != compare.x && square.y != compare.y ) {
					highlighted = new ArrayList<Square>();
					
					gearState.setNewGearBelt(square, compare);
					
					if((--numOfBelts) < 1) {
						stillSelecting = false;

						Gdx.app.log("timer", "starting timer");
						timer.scheduleTask(new Task(){
							
						    @Override
						    public void run() {
								Gdx.app.log("timer", "running timer");
						    	((Game) Gdx.app.getApplicationListener()).setScreen(new ConfirmationScreen(sound, playController));
						    }
						}, 1); 	 
					}
				} else {
					GearState.gearBelts[compare.x][compare.y] = GearBelts.GEAR;
					highlighted = new ArrayList<Square>();
					GearState.gearBelts[square.x][square.y] = GearBelts.GEAR_SELECTED;
					highlighted.add(square);
				}
			} else {
				GearState.gearBelts[square.x][square.y] = GearBelts.GEAR_SELECTED;
				highlighted.add(square);
			}
		}
	}
	
	// display the temporary gears on the screen so players can see them.
	public void printTempGears(SpriteBatch batch) {
		batch.begin();
		for (int i = 1; i < gearState.x; i += 2) {
			for (int j = 1; j < gearState.y; j += 2) {
				switch(GearState.gearBelts[i][j]){
					case GEAR:
						batch.draw(gear, i * Square.squareSize, j * Square.squareSize); //TODO set offsets
						break;
					case GEAR_SELECTED:
						batch.draw(gearSelected, i * Square.squareSize, j * Square.squareSize); //TODO set offsets
						break;
				default:
					break;
				}
			}
		}
		batch.end();
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap(float screenX, float screenY, int count, int button) {
		Gdx.app.log("TAP", "You 'tap' the screen at: " + screenX + " ; " + screenY);
		// out of bounds of play.
//		if ( screenX > 850 || screenY < 50 ) return false;
		if(stillSelecting) {
			makeGearTouch(screenX, Gdx.graphics.getHeight() - screenY);
		}
		return true;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		return false;
	}
}

//class Task extends TimerTask  
//{
//	private GameSoundController sound;
//	private PlayController playController;
//	
//	public Task(GameSoundController sound, PlayController playController){
//		this.sound = sound;
//		this.playController = playController;
//	}
//	
//	public void run() {
//		((Game) Gdx.app.getApplicationListener()).setScreen(new ConfirmationScreen(sound, playController));
//  	}
//
//} 
