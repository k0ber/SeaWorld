package com.sibext.java.junior;

import java.util.Collections;
import java.util.List;

/**
 * The organism abstraction
 * 
 * @author Nikolay Moskvin <moskvin@sibext.com>
 * 
 */
public abstract class Organism {
	protected World world;
	protected int x;
	protected int y;
	protected int age;
	protected boolean simulated;
	
	
	public Organism(World world, int x, int y) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.age = 0;
		simulated = true;
	}

	/**
	 * returns the string representation of the organism
	 */
	public abstract String toString();

	/**
	 * returns the reproduce period of organism
	 */
	public abstract int getReproducePeriod();
	
	/**
	 * create progeny of organism
	 * @param newX x coordinate of new organism
	 * @param newY y coordinate of new organism
	 * @return new instance of organism with given coordinates
	 */
	protected abstract Organism createChild(int newX, int newY);
	
	/**
	 * simulate one day of life
	 */
	public void simulate() {
		// don't simulate twice in a round
		if (simulated)
			return;
		
		simulated = true;
		age++;
		
		// now move, breed, ....
		doActions();
	}

	/**
	 * execute actions that organism must to do in his turn
	 */
	protected abstract void doActions();
	
	/**
	 * move the organism in random direction or stay still if it's no free way to move
	 */
	protected void tryToMove() {
		Direction intentDirection = getRandomFreeDirection(Direction.getMovingDirections());
		if(!intentDirection.equals(Direction.NONE)) {
			moveTo(this.x + intentDirection.x, this.y + intentDirection.y);
		}
	}
	
	/**
	 * move organism to given coordinates
	 * @param x x coordinate of position to move
	 * @param y y coordinate of position to move
	 */
	protected void moveTo(int x, int y) {
		world.setAt(this.x, this.y, null);
		this.x = x;
		this.y = y;
		world.setAt(this.x,  this.y,  this);
	}

	/**
	 * reproduce the new organism in random free near place if it's time to get child
	 */
	protected void tryToReproduce() {
		if(age % getReproducePeriod() != 0) return;
		
		Direction intentDirection = getRandomFreeDirection(Direction.getAllDirections());
		if(!intentDirection.equals(Direction.NONE)) {
			int newX = x + intentDirection.x;
			int newY = y + intentDirection.y;
			world.setAt(newX, newY, createChild(newX , newY));
		}
	}

	private Direction getRandomFreeDirection(List<Direction> directions) {
		Collections.shuffle(directions);
		for(Direction direction : directions) {
			if(isFreeDirection(direction.x, direction.y)) {
				return direction;
			}
		}
		return Direction.NONE;
	}
	
	/**
	 * delete creature from world
	 */
	protected void die() {
		world.setAt(x, y, null);
	}
	
	/**
	 * Checking given direction in the world
	 * @param dx x offset
	 * @param dy y offset
	 * @return true if it's not world border and it's no creature on this destination
	 */
	private boolean isFreeDirection(int dx, int dy) {
		return world.isFreeCell(x+dx, y+dy);
	}

	/**
	 * indicate that the organism hasn't simulated this round
	 */
	public void resetSimulation() {
		simulated = false;
	}
	
}
