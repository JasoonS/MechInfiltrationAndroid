package jason.smythe.uct.csc2003.entities;

import jason.smythe.uct.csc2003.desktop.maps.MapsController;
import jason.smythe.uct.csc2003.states.EnemyState;
import jason.smythe.uct.csc2003.states.EntityState;
import jason.smythe.uct.csc2003.util.Square;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Entity extends Sprite {
	
	protected float speed = (float) 100;
	public EntityState state;
	public MapsController maps;
	
	public Entity(Sprite sprite, MapsController maps, int startX, int startY) {
		super(sprite);
		this.maps = maps;
		state = new EntityState(maps);
		state.curSquare = new Square(startX, startY);
		state.nextSquare = new Square(startX, startY);
		state.position = state.curSquare.squareToPos();
		this.setPosition(state.position.x, state.position.y);
		this.setSize((float)0.8*Square.squareSize, (float)0.8*Square.squareSize);
		speed = Square.scale * 50;
		Gdx.app.log("speed", "The player has a speed of " + speed);
	}
	
	public Entity(Sprite sprite, EnemyState enemyState, int startX, int startY) {
		super(sprite);
		state = enemyState;
		this.maps = enemyState.maps;
		this.setSize((float)0.8*Square.squareSize, (float)0.8*Square.squareSize);
		speed = Square.scale * 50;
		Gdx.app.log("speed", "The Entity has a speed of " + speed);
	}
	
	public void draw(SpriteBatch batch) {
		super.draw(batch);
	}
}
