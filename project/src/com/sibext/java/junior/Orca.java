package com.sibext.java.junior;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The orca
 * 
 * @author Nikolay Moskvin <moskvin@sibext.com>
 * 
 */
public class Orca extends Organism {
	private static final int REPRODUCE_PERIOD = 8;
	private static final int BASE_SATIETY = 3;
	public static final String REPRESENTATION = "orca";
	
	private int satiety;
	
	
	public Orca(World world, int x, int y) {
		super(world, x, y);
		satiety = BASE_SATIETY;
	}
	
	@Override
	protected void doActions() {
		if(!hunt()) {
			tryToMove();
		}
		
		tryToReproduce();
		
		if(--satiety == 0) {
			die();
		}
	}
	
	/**
	 * trying to eat some one nearby
	 * @return true if hunt was successful, false if no sacrifice was found
	 */
	private boolean hunt() {
		ArrayList<Direction> directions = Direction.getAllDirections();
		Collections.shuffle(directions);
		
		for(Direction direction : directions) {
			int sacrificePositionX = this.x + direction.x;
			int sacrificePositionY = this.y + direction.y;
			Organism sacrifice = world.getAt(sacrificePositionX, sacrificePositionY);
			
			if(sacrifice != null && sacrifice.toString().equals(Tux.REPRESENTATION)) {
				sacrifice.die();
				moveTo(sacrificePositionX, sacrificePositionY);
				satiety = BASE_SATIETY;
				return true;
			}
		}
		return false;
	}
	

	/**
	 * string representation of Orca
	 */
	public String toString() {
		return REPRESENTATION;
	}
	
	@Override
	public int getReproducePeriod() {
		return REPRODUCE_PERIOD;
	}

	@Override
	public Organism createChild(int newX, int newY) {
		return new Orca(world, newX, newY);
	}
	
}
