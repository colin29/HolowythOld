package reelthyme.holowyth;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Holowyth extends Game {

    public final int resX = 960;
    public final int resY = 640;
	
    /* Rendering and pipeline variables */
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	BitmapFont font;

	
	@Override
	public void create () {		
		initializeSharedResources();
//		this.setScreen(new MainMenuScreen(this));
		this.setScreen(new GameScreen(this));
	}
	private void initializeSharedResources() {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
        font = new BitmapFont(); //Use LibGDX's default Arial font.
	}

	@Override
	public void render () {
		super.render(); //Calls Game.render, which will render the screens
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
	
}
