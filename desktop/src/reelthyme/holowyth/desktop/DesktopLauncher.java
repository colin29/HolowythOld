package reelthyme.holowyth.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import reelthyme.holowyth.Holowyth;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Holowyth";
		config.width = 960;
		config.height = 640;
		config.samples = 3;
		new LwjglApplication(new Holowyth(), config);
	}
}
