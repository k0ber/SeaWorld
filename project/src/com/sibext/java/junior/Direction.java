package com.sibext.java.junior;

import java.util.ArrayList;
import java.util.Arrays;

public enum Direction{
	
	UP(0, -1),
	DOWN(0, 1),
	LEFT(-1, 0),
	RIGHT(1, 0),
	
	UP_LEFT(-1, -1),
	UP_RIGHT(1, -1),
	DOWN_LEFT(-1, 1),
	DOWN_RIGHT(1, 1),
	
	NONE(0, 0);
	
	int x;
	int y;
	
	Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public static ArrayList<Direction> getMovingDirections() {
		ArrayList<Direction> directions = new ArrayList<Direction>(Arrays.asList(
				Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT));

		return directions;
	}
	
	public static ArrayList<Direction> getAllDirections() {
		ArrayList<Direction> directions = new ArrayList<Direction>(Arrays.asList(
				Direction.UP, Direction.UP_LEFT, Direction.UP_RIGHT,
				Direction.LEFT, Direction.RIGHT,
				Direction.DOWN, Direction.DOWN_LEFT, Direction.DOWN_RIGHT));

		return directions;
	}
	
}