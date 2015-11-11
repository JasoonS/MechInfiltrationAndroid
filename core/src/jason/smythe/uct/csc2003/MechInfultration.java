package jason.smythe.uct.csc2003;

import jason.smythe.uct.csc2003.screens.GearBeltSetup;
import jason.smythe.uct.csc2003.screens.Splash;
import jason.smythe.uct.csc2003.screens.MainMenu;
import jason.smythe.uct.csc2003.util.GameSoundController;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class MechInfultration extends Game {
	
	GameSoundController sound;
	
	@Override
	public void create () {
		//add sound and start the splash screen (chaining into all the other screens)
		sound = new GameSoundController();
//		setScreen(new Splash(sound));
//		setScreen(new MainMenu(sound));
		setScreen(new GearBeltSetup(sound, "maps/grid1.tmx", false));
	}
}
