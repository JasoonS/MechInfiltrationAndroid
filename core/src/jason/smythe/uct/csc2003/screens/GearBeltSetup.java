package jason.smythe.uct.csc2003.screens;

import jason.smythe.uct.csc2003.controllers.GearsController;
import jason.smythe.uct.csc2003.controllers.PlayController;
import jason.smythe.uct.csc2003.desktop.maps.MapsController;
import jason.smythe.uct.csc2003.states.GearState;
import jason.smythe.uct.csc2003.util.GameSoundController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;

public class GearBeltSetup implements Screen {

	MapsController maps;
	GearState gearState;
	GearsController gearsController;
	GameSoundController sound;
	PlayController playController;
	SpriteBatch batch;
	Texture background;
	ShapeRenderer shapeRenderer;
	
	public GearBeltSetup(GameSoundController sound, String mapToLoad, boolean specialDemo) {
		this.sound = sound;
		playController = new PlayController(sound, mapToLoad, specialDemo);
		batch = new SpriteBatch();
		background = new Texture("img/gearSelectBackground.png");
		shapeRenderer = new ShapeRenderer();
	}
	
	public GearBeltSetup(GameSoundController sound, PlayController playController) {
		this.sound = sound;
		this.playController = playController;
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
//		background = new Texture("img/gearSelectBackground.png");
	}

	@Override
	public void show() {
		maps = playController.maps;
		
		maps.setPlayerPositionVariable(playController.player.state.curSquare);
		
		playController.show();
		
		gearState = new GearState(17, 13, maps);
		gearsController = new GearsController(gearState, 3, sound, playController);
		
		playController.updateGearState(gearState);
		
		GestureDetector gd = new GestureDetector(gearsController);
		Gdx.input.setInputProcessor(gd);
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
//		batch.draw(background, 0, 0);
//		batch.end();
		
		playController.render();
		
		gearsController.printTempGears((SpriteBatch) playController.renderer.getBatch());
		
		//TODO: THERE IS A BUG HERE!! When changing screens sometimes things stop being defined. Intermittant error.
		if(!gearsController.stillSelecting) {
			Gdx.gl.glEnable(GL20.GL_BLEND);
		    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		    shapeRenderer.setColor(new Color(0, 0, 0, 0.5f));
		    shapeRenderer.rect(0, 0, playController.w, playController.h);
		    shapeRenderer.end();
		    Gdx.gl.glDisable(GL20.GL_BLEND);
		}
	}

	@Override
	public void resize(int width, int height) {
		playController.resize(width, height);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose() {
//		playController.dispose();
	}
}
