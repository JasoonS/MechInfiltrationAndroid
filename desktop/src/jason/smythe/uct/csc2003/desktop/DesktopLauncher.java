package jason.smythe.uct.csc2003.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import jason.smythe.uct.csc2003.MechInfultration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "MechInfiltration";
		config.height = 400;
		config.width = 800;
		new LwjglApplication(new MechInfultration(), config);
	}
}
