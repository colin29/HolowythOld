package reelthyme.holowyth;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
    final Holowyth game;

    /*Rendering and pipeline variables*/
    OrthographicCamera camera;
    
    /*Resource variables*/
    
    /*Game logic variables*/

    public GameScreen(final Holowyth game) {
        this.game = game;

        loadImages();
        loadAndConfigureAudio();

        // create a camera for this screen.
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 960, 640);

        initializeGameLogic();

    }

    Texture playerSprite;
	private void loadImages() {
		playerSprite = new Texture(Gdx.files.internal("icons\\200px\\blue.png"));
	}

	private void loadAndConfigureAudio() {
	}
	
	private void initializeGameLogic() {
	}

	
    @Override
    public void render(float delta) {
    	ShapeRenderer shapeRenderer = game.shapeRenderer; 
        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(0.8f, 1f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops
        game.batch.begin();
        
        //game.batch.draw(playerSprite, 100, 100, 50, 50);
        
        game.batch.end();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 1, 1);
        shapeRenderer.circle(100, 100, 25);
        shapeRenderer.end();
//        // process user input
//        if (Gdx.input.isTouched()) {
//            Vector3 touchPos = new Vector3();
//            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//            camera.unproject(touchPos);
//            bucket.x = touchPos.x - 64 / 2;
//        }
//        if (Gdx.input.isKeyPressed(Keys.LEFT))
//            bucket.x -= 200 * Gdx.graphics.getDeltaTime();
//        if (Gdx.input.isKeyPressed(Keys.RIGHT))
//            bucket.x += 200 * Gdx.graphics.getDeltaTime();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

}