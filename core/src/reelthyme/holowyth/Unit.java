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
}
