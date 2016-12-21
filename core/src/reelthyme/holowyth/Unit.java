package reelthyme.holowyth;

public class Unit {

	public Unit(double x, double y){
		this((float) x, (float) y);
	}
	public Unit(float x, float y) {
		this.x = x;
		this.y = y;
	}
	float x;
	float y;
	
	//Velocity variables reset and used every tick. It's the job of tickLogic to ensure that unit
	// unit velocities are modified in a consistent manner.
	//tickLogic can, but does not have to use vx/vy for every unit
	float vx;
	float vy;
}
