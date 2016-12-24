package reelthyme.holowyth;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.sun.javafx.print.Units;

public class GameScreen implements Screen {
    final Holowyth game;

    /*Rendering and pipeline variables*/
    OrthographicCamera camera;
    ShapeRenderer shapeRenderer;
    SpriteBatch batch;
    
    /*Resource variables*/
    
    /*Game logic variables*/
    private World world;
    
    public GameScreen(final Holowyth game) {
        this.game = game;
        world = new World(game);
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

	/* Variables for enforcing fixed fps */
	final private long INITIAL_TIME = System.nanoTime();
	private long timeElapsed = 0;
	private long ticksPerSecond = 60;
	private long timeBetweenTicks = 1000000000/ticksPerSecond; 
	private long timeTillNextTick=0;
	private int maxConsecutiveTicks = 3;
	
	/* Variables for calculating logicFps */
	private long lastTime =  INITIAL_TIME;
    private double logicFps = 0;

    
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
        shapeRenderer.setProjectionMatrix(camera.combined);
        
        batch.begin();
        renderGameObjects();
        batch.end();
        
        /* Run game logic if enough time has elapsed. If render is slow, run game logic up to three times*/
        timeElapsed = System.nanoTime() - INITIAL_TIME;
        timeTillNextTick -= Gdx.graphics.getRawDeltaTime()*1000000000;
        
        int i;
        for(i=0; (timeTillNextTick<=0 && i<maxConsecutiveTicks); i++){
        	timeTillNextTick += timeBetweenTicks;
        	world.tickLogic();
        }
        
        if(i>0){
        logicFps = -1000000000.0 / (lastTime - (lastTime = System.nanoTime()) * i); 
        }
//        if(timeTillNextTick <=0){ //don't attempt to continue catching up after a long render or streak of renders.
//        	timeTillNextTick = 0;
//        }
    }

    private void renderGameObjects(){
        
        //Draw FPS Counter
        String fps = "FPS: " + String.valueOf(Math.round(logicFps));
        game.font.setColor(Color.BLACK);
        game.font.draw(batch, fps , game.resX-60 , 20, 0, fps.length(), 60, Align.left, false, "");
        //batch.draw(playerSprite, 100, 100, 50, 50);   
        
        
        //Draw units
        shapeRenderer.begin(ShapeType.Filled);
        for(Unit unit : world.units){
        	if(unit.getFaction() == Unit.Faction.FRIENDLY){
        		shapeRenderer.setColor(0, 0, 1, 1);
        	}else if((unit.getFaction().ordinal()) >= 2){ //color enemy units
        		shapeRenderer.setColor(1, 0, 0, 1);
        	}else { //unit of unknown or neutral type
        		shapeRenderer.setColor(0, 0, 0, 1);
        	}
        	
            shapeRenderer.circle(unit.x(), unit.y(), 25);
        }
        shapeRenderer.end();
        
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
    public void dispose() {
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

 

}