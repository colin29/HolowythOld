package reelthyme.holowyth;

import reelthyme.holowyth.util.Loc;

public class Unit {
	
	private float x;
	private float y;
	
	//Velocity variables reset and used every tick. It's the job of tickLogic to ensure that unit
	// unit velocities are modified in a consistent manner.
	//tickLogic can, but does not have to use vx/vy for every unit
	float vx;
	float vy;
	
	private Loc moveTarget;
	private float speed = 20; //default speed. (20 speed corresponds with 200 pixels per second.
	
	
	private int maxHp = 120;
	private int hp = maxHp;
	
	public enum Faction {FRIENDLY, NEUTRAL, ENEMY1, ENEMY2, ENEMY3} //Enemies is 2 and up.
	private Faction myFaction;
	
	private Unit attackTarget;
	private Unit isAttacking; //is null if not currently Attacking
	
	static final int SPEED_CONVERSION_FACTOR = 6;
	
	public Unit(double x, double y, Faction faction){
		this((float) x, (float) y, faction);
	}
	public Unit(float x, float y, Faction faction) {
		this.x = x;
		this.y = y;
		this.myFaction = faction;
	}
	
	/* Unit command interface */ 
	
	public void moveOrder(Loc loc){
		setMoveTarget(loc);
		attackTarget = null;
	}
	public void attackOrder(Unit unit){
		if(this.areFoes(unit)){
			this.attackTarget = unit;	
		}else{
			System.out.println("Target unit is not a foe");
		}
		//Does not need to set the moveTarget (it is set automatically to the target's location every turn)
	}
	
	/* Protected Controls */
	
	/** 
	 * Sets the move (vx, vy) of a unit based on the unit's moveTarget;
	 *
	 **/
	void setMoveTowardsTarget() {
		if(this.getMoveTarget() == null){
			this.vx = 0;
			this.vy = 0;
			return;
		}
		Loc dest = this.getMoveTarget();
		
		float tempX;
		float tempY;
		float distX;
		float distY;
		float dist;
		if(moveTarget != null){
			//1. Calculate difference vectors (target - this).
			distX = dest.x() - this.x;
			distY = dest.y() - this.y;
			
			//2. If distance to unit is less than 10, instead set interim target to a point 10 dist. away
			//and recalculate distance vectors
			float minSpacing = World.getMinSpacing();
			tempX = dest.x();
			tempY = dest.y();
			dist = (float) Math.sqrt((distX * distX + distY * distY));
			if(dist <= minSpacing + this.speed / Unit.SPEED_CONVERSION_FACTOR){
				distX *= -(minSpacing-dist)/dist;
				distY *= -(minSpacing-dist)/dist;
				tempX = x + distX;
				tempY = y + distY;
				dist = minSpacing-dist;
			}
			
			//If distance is less than speed, set the unit to move to the interim target.
			if(dist < this.speed){
				vx = tempX - x;
				vy = tempY - y;
			}else{
			//Otherwise, divide the distance vectors by (speed / distance).
				vx = distX * (this.speed / dist) / Unit.SPEED_CONVERSION_FACTOR;
				vy = distY * (this.speed / dist) / Unit.SPEED_CONVERSION_FACTOR;
			}

		}
	}
	
	public void setMoveTarget(Loc loc){
		moveTarget = new Loc(loc.x(), loc.y());
	}

	/* Logical helper functions */
	
	public boolean isEnemy(){
		return (this.myFaction.ordinal() >= 2);
	}
	
	public boolean areFoes(Unit unit){
		return (this.getFaction()==Unit.Faction.FRIENDLY && unit.isEnemy() ||
				this.isEnemy() && unit.getFaction() == Unit.Faction.FRIENDLY);
	}
	
	
	/* Getters and setter */
	
	public Faction getFaction() {
		return myFaction;
	}
	
	public Loc getMoveTarget(){
		if(moveTarget != null){
			return new Loc(moveTarget.x(), moveTarget.y());
		}else{
			return null;
		}
		
	}
	public Unit getAttackTarget(){
		return this.attackTarget;
	}
	

	public Loc loc(){
		return new Loc(this.x, this.y);
	}
	
	/* alternative getters for fields x and y */
	public float x() {
		return x;
	}
	public float y() {
		return y;
	}
	
	public float getSpeed() {
		return speed;
	}

	public int maxHp(){
		return this.maxHp;
	}
	
	public Unit getIsAttacking() {
		return isAttacking;
	}
	
	/* Protected setters */
	void setMaxHp(int value){
		if(value < 1){
			System.out.println("Error: MaxHp must be greater than 0");
		}
		maxHp = value;
		if(hp > maxHp){
			hp = maxHp;
		}
	}
	void setIsAttacking(Unit unit){
		if(this.areFoes(unit)){
			this.isAttacking = unit;
		}else{
			System.out.println("Error in world control: Trying to engage a unit which is not a foe");
			System.out.println(unit.getFaction() + " " + unit.getAttackTarget().getFaction());
		}
		//Does not check distance, because that is a World semantic, and expected to be managed there.
	}
	
	void setX(float x) {
		this.x = x;
	}
	void setY(float y) {
		this.y = y;
	}
	void setSpeed(float speed) {
		this.speed = speed;
	}


	
}


