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
	private int maxConsecutiveTicks = 1; //normally is 3

	private void runLogicAtFixedFPS() {
		/* Game logic tick timing control */
        
        //Run game logic if enough time has elapsed. If render is slow, run game logic up to three times
        timeElapsed = System.nanoTime() - INITIAL_TIME;
        timeTillNextTick -= Gdx.graphics.getRawDeltaTime()*1000000000;
        
        int i;
        for(i=0; (timeTillNextTick<=0 && i<maxConsecutiveTicks); i++){
        	timeTillNextTick += timeBetweenTicks;
        	world.tickLogic();
        }
        
        calculateLogicFPS(i);
//        if(timeTillNextTick <=0){ //don't attempt to continue catching up after a long render or streak of renders.
//        	timeTillNextTick = 0;
//        }
	}

	private long lastTime =  INITIAL_TIME;
    private double logicFps = 0;
    
	private void calculateLogicFPS(int i) {
		if(i>0){
        logicFps = -1000000000.0 / (lastTime - (lastTime = System.nanoTime()) * i); 
        }
	}
    
    @Override
    public void render(float delta) {
    	
        // Clear the screen
        Gdx.gl.glClearColor(0.8f, 1f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));

        
        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the coordinate system specified by the camera.
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        
        
        ArrayList<UnitV> units = world.getUnits(); //get a copy of the units in the world 
        
        //Render units
        
        batch.begin();
        renderUnits(units);
        batch.end();
        
        //Render fonts
        batch.begin();
        
        if(Settings.displayFPSCounter){
        	drawFPSCounter();
        }
        
        
        drawHPLabels(units);

        batch.end();
 

        runLogicAtFixedFPS();
    }

	private void renderUnits(ArrayList<UnitV> units){

        float UNIT_DRAW_SIZE = 25;
        
        
        //Draw units
        for(UnitV unit: units){
        	unit.setScreenX(unit.x());
        	unit.setScreenY(unit.y());
        }
        
        shapeRenderer.begin(ShapeType.Filled);
        for(UnitV unit : units){
        	
        	Color unitColor = new Color();
        	
        	if(unit.getFaction() == Unit.Faction.FRIENDLY){
        		unitColor.set(0, 0, 1, 1);
        	}else if(unit.isEnemy()){ //color enemy units
        		unitColor.set(1, 0, 0, 1);
        	}else { //unit of unknown or neutral type
        		unitColor.set(0.5f, 0.5f, 0.5f, 1);
        	}
        	shapeRenderer.setColor(unitColor);
        	
        	if(unit.getIsAttacking() != null){
        		shapeRenderer.setColor(1, 207f/256, 65f/256, 0);
        		shapeRenderer.circle(unit.x(), unit.y(), UNIT_DRAW_SIZE + 5);
        		shapeRenderer.setColor(unitColor);
            }
            shapeRenderer.circle(unit.x(), unit.y(), UNIT_DRAW_SIZE);
            
            
        }
        shapeRenderer.end();
        
    }
    
	/* Drawing helper functions */
	
	private void drawFPSCounter() {
		//Draw FPS Counter
        String fps = "FPS: " + String.valueOf(Math.round(logicFps));
        game.font.setColor(Color.BLACK);
        game.font.draw(batch, fps , game.resX-60 , 40/*, 0, fps.length(), 60, Align.left, false, ""*/);
	}


    
	
	private void drawHPLabels(ArrayList<UnitV> units) {
		game.font.setColor(Color.BLACK);
	    String str;
	    for(UnitV unit: units){
	    	str = String.valueOf(unit.hp() + "/" + String.valueOf(unit.maxHp())); 
	        game.font.draw(batch, str , unit.screenX() , unit.screenY()); //screenXY of units is set earlier by renderUnits
	    }
	}

	/* Screen Override functions */
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

    //pause() and resume() are unused (only for Android OS)
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

 

}