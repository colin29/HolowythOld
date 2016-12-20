package reelthyme.holowyth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;

public class GameScreen implements Screen {
    final Holowyth game;

    /*Rendering and pipeline variables*/
    OrthographicCamera camera;
    ShapeRenderer shapeRenderer;
    SpriteBatch batch;
    
    /*Resource variables*/
    
    /*Game logic variables*/

    public GameScreen(final Holowyth game) {
        this.game = game;

        loadImages();
        loadAndConfigureAudio();

        // create a camera for this screen.
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 960, 640);
        
    	shapeRenderer = game.shapeRenderer;  //doing this just for the shorter addressing
    	batch = game.batch;
    	
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

	final private long INITIAL_TIME = System.nanoTime();
	private long timeElapsed = 0;
	private long ticksPerSecond = 60;
	private long timeBetweenTicks = 1000000000/ticksPerSecond; 
	private long timeTillNextTick=0;
	private int maxConsecutiveTicks = 3;
    @Override
    public void render(float delta) {
    	
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
        batch.setProjectionMatrix(camera.combined);

       
        batch.begin();
        
        
        String fps = "FPS: " + String.valueOf(Math.round(logicFps));
        game.font.setColor(Color.BLACK);
        game.font.draw(batch, fps , game.resX-60 , 20, 0, fps.length(), 60, Align.left, false, "");
        //batch.draw(playerSprite, 100, 100, 50, 50);
        
        batch.end();
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
        
        /* Run game logic if enough time has elapsed. If render is slow, run game logic up to three times*/
        timeElapsed = System.nanoTime() - INITIAL_TIME;
        timeTillNextTick -= Gdx.graphics.getRawDeltaTime()*1000000000;
        
        int i;
        for(i=0; (timeTillNextTick<=0 && i<maxConsecutiveTicks); i++){
        	timeTillNextTick += timeBetweenTicks;
        	tickGameLogic();
        }
        
        if(i>0){
        logicFps = -1000000000.0 / (lastTime - (lastTime = System.nanoTime()) * i); 
        }
//        if(timeTillNextTick <=0){ //don't attempt to continue catching up after a long render or streak of renders.
//        	timeTillNextTick = 0;
//        }
    }
    
    private long lastTime =  INITIAL_TIME;
    private double logicFps = 0;
    private void tickGameLogic(){
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