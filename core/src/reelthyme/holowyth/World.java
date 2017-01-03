package reelthyme.holowyth;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import reelthyme.holowyth.util.Loc;

public class World {

	private ArrayList<Unit> units = new ArrayList<Unit>();
	
    private Unit player = new Unit(200, 100, Unit.Faction.FRIENDLY);
    private Unit enemy1 = new Unit(300, 300, Unit.Faction.ENEMY1);
    private Unit enemy2 = new Unit(600, 100, Unit.Faction.ENEMY1);
    
    
    
    /* Constant fields */
    private static float MIN_SPACING = 100;
    
    /* Misc. constant expressions */
    final private float SQRT2 = (float) Math.sqrt(2);
    final private float epsilon = (float) 1E-5;
    
    /* Misc. debugging fields */
    final public int FRAMES_BETWEEN_BREAKPOINTS = 30;
    private int breakPointCounter = 1;


    World(final Holowyth game) {
		
		player.setSpeed(25);
		
		enemy1.attackOrder(player);
		enemy2.attackOrder(player);
		
		
		units.add(player);
		units.add(enemy1);
		units.add(enemy2);
	}

    
	void tickLogic(){
    
	breakPointCounter++;
	if(breakPointCounter > FRAMES_BETWEEN_BREAKPOINTS){
		breakPointCounter = 1;
	}
		
	/* Four step Process for basic movement combat*/ 
		
	
	
	/* Step 1: Figure out targets and set unit moves */
		
	//Units will track towards their attackTargets
	for(Unit unit: units){
		if(unit.getAttackTarget() != null){
			unit.setMoveTarget(unit.getAttackTarget().loc());
		}
	}
	
	/* Start determining unit moves */
	for(Unit unit: units){
		unit.vx = 0;
		unit.vy = 0;
	}
	
	for(Unit unit: units){
		if(!isBeingAttacked(unit)){
			unit.setMoveTowardsTarget();
		}
	}
	if(!isBeingAttacked(player)){
		setPlayerMoveNormalized();
	}
      
    //Any skills or mechanics that deal with movement should make their effect here
    
    moveUnits();
    checkRangeForEngagingUnits();
    
    
    /* Handle combat logic */
    
    //Make units do their combat damage
    for(Unit unit: units){
    	if(unit.getIsAttacking() != null){
    		unit.attackTimer--;
        	if(unit.attackTimer <= 0){
        		System.out.println("unit attacks: " + unit.getUnitNumber());
        		//attack occurs
        		unit.doDamageTo(unit.getIsAttacking(), unit.damage);
        		unit.attackTimer = unit.attackInterval;
        	}
    	}
    	
    }
    //Remove dead units
    for(int i=0; i<units.size(); i++){
    	if(units.get(i).hasDied){
    		units.remove(i);
    		i--;
    	}
    }
    
    
    }


	private void checkRangeForEngagingUnits() {
		for(Unit unit: units){
			if(unit.getAttackTarget() != null){
				Unit foe = unit.getAttackTarget();
			
				if(Loc.dist(unit.loc(), foe.loc()) <= MIN_SPACING + epsilon){
					unit.setIsAttacking(foe);
					
					//units automatically retaliate if they are not attacking another target.
					if(foe.getIsAttacking() == null){
						foe.setIsAttacking(unit);
					}
				}
			}
		}
	}

    private void moveUnits() {
    	
    	@SuppressWarnings("unused")
    	int x=3; //dummy statement
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
	
	/* Helper functions */
	
	private boolean isBeingAttacked(Unit curUnit) {
		boolean isBeingAttacked = false;
		for(Unit unit: units){
			if(unit.getIsAttacking() == curUnit){
				isBeingAttacked = true;
			}
		}
		return isBeingAttacked;
	}
	
	/* Getters and Setters */
	
	/**
	 * Calling this makes a copy of the unit arrayList, so avoid calling repeatedly. Though with unit counts expected to be under 100, this should not matter.
	 */
	ArrayList<UnitV> getUnits(){
		return new ArrayList<UnitV>(units);
	}

	
}
