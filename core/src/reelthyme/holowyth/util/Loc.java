package reelthyme.holowyth.util;

public class Loc {
	public Loc(float x, float y){
		this.x = x;
		this.y = y;
	}
	private float x;
	private float y;
	
	public float x() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float y() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	
	public static float dist(Loc a, Loc b){
		return (float) Math.sqrt(((b.x-a.x)*(b.x-a.x) + (b.y-a.y)*(b.y-a.y)));
	}
}

