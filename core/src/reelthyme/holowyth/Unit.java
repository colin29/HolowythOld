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
	
	private float moveTargetX;
	private float moveTargetY;
	
	private float speed = 20; //default speed. (20 speed corresponds with 200 pixels per second.
	private int maxHp = 120;
	private int hp = maxHp;
	
	public enum Faction {FRIENDLY, NEUTRAL, ENEMY1, ENEMY2, ENEMY3} //Enemies is 2 and up.
	private Faction myFaction;
//	public enum Behaviour {RetaliateOnly}
//	private Behaviour myBehaviour;
	
	static final int SPEED_CONVERSION_FACTOR = 6;
	
	public Unit(double x, double y, Faction faction){
		this((float) x, (float) y, faction);
	}
	public Unit(float x, float y, Faction faction) {
		this.x = x;
		this.y = y;
		this.myFaction = faction;
	}
	

	/* Getters and setter */
	
	int maxHp(){
		return this.maxHp;
	}
	void setMaxHp(int value){
		if(value < 1){
			System.out.println("Error: MaxHp must be greater than 0");
		}
		maxHp = value;
		if(hp > maxHp){
			hp = maxHp;
		}
	}
	public Faction getFaction() {
		return myFaction;
	}
	
	public Loc getMoveTarget(){
		return new Loc(this.moveTargetX, this.moveTargetY);
	}
	public void setMoveTarget(Loc loc){
		moveTargetX = loc.x();
		moveTargetY = loc.y();
	}
	public boolean isEnemy(){
		return (this.myFaction.ordinal() >= 2);
	}
	public Loc loc(){
		return new Loc(this.x, this.y);
	}
	
	/* Convenience functions, equivalent to getting and setting Loc */
	public float x() {
		return x;
	}
	public float y() {
		return y;
	}
	void setX(float x) {
		this.x = x;
	}
	void setY(float y) {
		this.y = y;
	}
	public float getSpeed() {
		return speed;
	}
	void setSpeed(float speed) {
		this.speed = speed;
	}

}


