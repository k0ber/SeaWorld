package com.sibext.java.junior;

/**
 * The tux
 * 
 * @author Nikolay Moskvin <moskvin@sibext.com>
 * 
 */
public class Tux extends Organism {
	public static final int REPRODUCE_PERIOD = 3;
	public static final String REPRESENTATION = "tux";
	
	
	public Tux(World world, int x, int y) {
		super(world, x, y);
	}

	/**
	 * do what the tux must to do
	 */
	@Override
	protected void doActions() {
		tryToMove();
		tryToReproduce();
	}
	
	/**
	 * returns a string representation of the Tux
	 */
	@Override
	public String toString() {
		return REPRESENTATION;
	}

	@Override
	public int getReproducePeriod() {
		return REPRODUCE_PERIOD;
	}

	@Override
	protected Organism createChild(int newX, int newY) {
		return new Tux(world, newX, newY);
	}
	
}
