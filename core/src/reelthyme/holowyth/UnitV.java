package reelthyme.holowyth;

import reelthyme.holowyth.Unit.Faction;
import reelthyme.holowyth.util.Loc;

/**
 * Is the interface used by components that only need view and not modify Unit data
 */
public interface UnitV {

	/*Helpers*/
	boolean isEnemy();
	boolean areFoes(Unit unit);
	
	/*Getters*/
	Faction getFaction();
	Loc getMoveTarget();
	UnitV getAttackTarget();
	Loc loc();
	float x();
	float y();
	float getSpeed();
	int maxHp();
	int hp();
	UnitV getIsAttacking();
	
	//Screen XY are special in that they are modified and used as needed by the caller.
	float screenX();
	float screenY();
	void setScreenX(float x);
	void setScreenY(float y);

}
