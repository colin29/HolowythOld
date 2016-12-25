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


    World(final Holowyth game) {
		this.game = game;
		
		player.setSpeed(25);
		units.add(player);
		units.add(enemy1);
		//units.add(enemy2);
	}

    
	void tickLogic(){
    
		
	/* Four step Process for basic movement combat*/ 
		
	
	
	/* Step 1: Set unit movement */
	//Handle the enemy AI
	for(Unit u: units){
		if(u.isEnemy()){
			u.setMoveTarget(player.loc());
		}
	}
	for(Unit u: units){
		if(u.isEnemy()){
			u.setMoveTowardsTarget();
		}
	}
    setPlayerMoveNormalized();
    
    /* Step 2: Move units */
    moveUnits();
    
    /* Step 3: Detect range for units trying to engage */
    
    /* Step 4: Handle combat logic */
    
    }

	private void setMoveTowardsTarget(Unit u) {
		if(u.getMoveTarget() == null){
			return;
		}
		Loc dest = u.getMoveTarget();
		
		float tempX;
		float tempY;
		float distX;
		float distY;
		float dist;
		if(u.getMoveTarget() != null){
			//1. Calculate difference vectors (target - this).
			distX = dest.x() - u.x();
			distY = dest.y() - u.y();
			
			//2. If distance to unit is less than 10, instead set interim target to a point 10 dist. away
			//and recalculate distance vectors
			tempX = dest.x();
			tempY = dest.y();
			dist = (float) Math.sqrt((distX * distX + distY * distY));
			if(dist <= MIN_SPACING + u.getSpeed() * Unit.SPEED_CONVERSION_FACTOR){
				distX *= -(MIN_SPACING-dist)/dist;
				distY *= -(MIN_SPACING-dist)/dist;
				tempX = u.x() + distX;
				tempY = u.y() + distY;
				dist = MIN_SPACING-dist;
			}
			System.out.println(dist);
			
			//If distance is less than speed, set the unit to move to the interim target.
			if(dist < u.getSpeed()){
				u.vx = tempX - u.x();
				u.vy = tempY - u.y();
			}else{
			//Otherwise, divide the distance vectors by (speed / distance).
				u.vx = distX * (u.getSpeed() / dist) / Unit.SPEED_CONVERSION_FACTOR;
				u.vy = distY * (u.getSpeed() / dist) / Unit.SPEED_CONVERSION_FACTOR;
			}

		}
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

	/* Spawn a default enemy that will just attack whatever's nearby */
	private void spawnEnemy(float posX, float posY){
		Unit unit = new Unit(posX, posY, Unit.Faction.ENEMY1);
	}

	public static float getMinSpacing() {
		return MIN_SPACING;
	}
	
	/* Getters and Setters */

	
}
