package reelthyme.holowyth;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import reelthyme.holowyth.util.Loc;

public class World {

	Holowyth game;
	ArrayList<Unit> units = new ArrayList<Unit>();
	
    private Unit player = new Unit(200, 100, Unit.Faction.FRIENDLY);
    private Unit enemy1 = new Unit(400, 400, Unit.Faction.ENEMY1);
    private Unit enemy2 = new Unit(600, 100, Unit.Faction.ENEMY1);
    
    
    
    /* Constant fields */
    private static float MIN_SPACING = 100;
    
    /* Misc. constant expressions */
    final private float SQRT2 = (float) Math.sqrt(2);
    
    /* Misc. debugging fields */
    final public int FRAMES_BETWEEN_BREAKPOINTS = 30;
    private int breakPointCounter = 1;


    World(final Holowyth game) {
		this.game = game;
		
		player.setSpeed(25);
		
		enemy1.attackOrder(player);
		
		
		units.add(player);
		units.add(enemy1);
		//units.add(enemy2);
	}

    
	void tickLogic(){
    
	breakPointCounter++;
	if(breakPointCounter > FRAMES_BETWEEN_BREAKPOINTS){
		breakPointCounter = 1;
	}
		
	/* Four step Process for basic movement combat*/ 
		
	
	
	/* Step 1: Figure out targets and set unit moves */
		
	//Units will track towards their attackTargets
	for(Unit u: units){
		if(u.getAttackTarget() != null){
			u.setMoveTarget(u.getAttackTarget().loc());
		}
	}
	
	for(Unit u: units){
		u.setMoveTowardsTarget();
	}
    setPlayerMoveNormalized();
    
    /* Step 2: Move units */
    moveUnits();
    
    /* Step 3: Detect range for units trying to engage */
    
    for(Unit u: units){
    	if(u.getAttackTarget() != null){
    		if(Loc.dist(u.loc(), u.getAttackTarget().loc()) <= MIN_SPACING){
    			u.setIsAttacking(u.getAttackTarget());
    		}
    	}
    }
    
    getMinSpacing();
    
//    for(Unit u: units){
//	    if(u.isEnemy()){
//			System.out.println(u.getAttackTarget());
//		}
//    }
    
    /* Step 4: Handle combat logic */
   
    
    
    }

	
    private void moveUnits() {
    	for(Unit unit: units){
    		unit.setX(unit.x() + unit.vx);
    		unit.setY(unit.y() + unit.vy);
    	}
		
	}

	private void setPlayerMoveNormalized() {
		//Move player according to input
		player.vy = 0; player.vx = 0;
		int movementVectorCount = 0;
		
		float speedInPixels = player.getSpeed()/ Unit.SPEED_CONVERSION_FACTOR;
		if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.D)){
			if (Gdx.input.isKeyPressed(Keys.A)){
				player.vx = -speedInPixels;
			}else{
				player.vx = speedInPixels;
			}
			movementVectorCount +=1;
		}
		if (Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.W)){
			if (Gdx.input.isKeyPressed(Keys.S)){
				player.vy = -speedInPixels;
			}else{
				player.vy = speedInPixels;
			}
			movementVectorCount +=1;
		}
		//normalize movement speed (ie. for moving diagonally)
		if(movementVectorCount == 2){
			player.vx /= SQRT2;
			player.vy /= SQRT2;
		}
	}

	public static float getMinSpacing() {
		return MIN_SPACING;
	}
	
	/* Getters and Setters */

	
}
