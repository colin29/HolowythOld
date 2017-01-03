package reelthyme.holowyth;

import reelthyme.holowyth.Unit.Faction;
import reelthyme.holowyth.util.Loc;

 interface UnitB {

	/* Unit commands */
	void moveOrder(Loc loc);
	void attackOrder(Unit unit);
	void setMoveTarget(Loc loc);
	
	/*Helpers*/
	boolean isEnemy();
	boolean areFoes(Unit unit);
	
	/*Getters*/
	Faction getFaction();
	Loc getMoveTarget();
	Unit getAttackTarget();
	Loc loc();
	float x();
	float y();
	float getSpeed();
	int maxHp();
	Unit getIsAttacking();
	
	/*Setters*/
	void setMaxHp(int f);
	void setX(float x);
	void setY(float y);
	void setSpeed(float f);

}
