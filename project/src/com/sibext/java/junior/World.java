package com.sibext.java.junior;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

/**
 * The world of aquatic
 * 
 * @author Nikolay Moskvin <moskvin@sibext.com>
 * 
 */
@SuppressWarnings("serial")
public class World extends GraphicsProgram {
	public static final int GRID_SIZE_ROW = 15;
	public static final int GRID_SIZE_COL = 10;
	
	private int tuxCount;
	private int orcaCount;
	private int day = 1;
	
	/**
	 * A two arrays that holds the set of organisms in the world
	 */
	private Organism[][] creatures = new Organism[GRID_SIZE_ROW][GRID_SIZE_COL];
	
	/**
	 * Randomly generates Tux and Orca and places them in the world
	 * displays the world of Tux and Orca with GImages
	 */
	public void run() {
		setBackground(new Color(0,206,209));
		setSize(750, 500);
		RandomGenerator rgen = RandomGenerator.getInstance();
		
		for (int row = 0; row < GRID_SIZE_ROW; row++) {
			for (int col = 0; col < GRID_SIZE_COL; col++) {
				int randNum = rgen.nextInt(0, 100);

				// approx 50% the spots will be heald by tux
				if (randNum > 90) { // TODO: 50
					creatures[row][col] = new Tux(this, row, col);
				}
				// approx 5% of the spots will be heald by orca
				else if (randNum < 50) { // TODO: 5
					creatures[row][col] = new Orca(this, row, col);
				}
			}
		}
		
		display();
		addMouseListeners();
	}

	/**
	 * Each time the mouse is clicked the world is resimulated
	 */
	public void mouseClicked(MouseEvent e) {
		System.out.println("Tuxes: " + tuxCount + "\nOrcas: " + orcaCount);
		if((orcaCount == 0 && tuxCount == 0) || tuxCount == GRID_SIZE_ROW * GRID_SIZE_COL) {
			removeAll();
			GImage image = new GImage("../img/game_over.png", 100, 150);
			GLabel text = new GLabel("Days gone: " + day, 150, 270);
			add(image);
			add(text);
			return;
		}
		
		simulateOneStep();
	}
	
	/**
	 * This avoids a creatures being simulated more than once
	 * simulates all the orca first
	 * then simulates the tux
	 * then displays the resulting world to the screen
	 */
	public void simulateOneStep() {
		System.out.println("Day number " + day);
		resetSimulation(); 
		simulate("orca");
		simulate("tux");

		day++;
		display();
	}

	/**
	 * Sets all the creatures to say they haven't been simulated
	 */
	public void resetSimulation() {
		for (int row = 0; row < GRID_SIZE_ROW; row++) {
			for (int col = 0; col < GRID_SIZE_COL; col++) {
				if (creatures[row][col] != null) {
					creatures[row][col].resetSimulation();
				}
			}
		}
	}

	/**
	 * Simulates the specified type of creatures
	 * @param type the kind of creatures
	 */
	public void simulate(String type) {
		for (int row = 0; row < GRID_SIZE_ROW; row++) {
			for (int col = 0; col < GRID_SIZE_COL; col++) {
				if (creatures[row][col] != null
						&& creatures[row][col].toString().equals(type)) {
					creatures[row][col].simulate();
				}
			}
		}
	}

	/**
	 * Displays all the creatures to the screen
	 * Every time clears the screen first 
	 */
	public void display() {
		removeAll();
		tuxCount = orcaCount = 0;
		
		for (int row = 0; row < GRID_SIZE_ROW; row++) {

			for (int col = 0; col < GRID_SIZE_COL; col++) {
				Organism organism = creatures[row][col];

				if (organism != null) {
					// draws the specified creature to the screen
					GImage bImage = new GImage("../img/" + organism.toString() + ".png",
							organism.x * 50, organism.y * 50);
					add(bImage);
					
					if(organism.toString().equals("tux")) tuxCount++;
					if(organism.toString().equals("orca")) orcaCount++;
				}
			}
		}
	}

	/**
	 * @param x the horizontal coordinate
	 * @param y the vertical coordinate
	 * @return the creature at the given array coordinates in the world
	 */
	public Organism getAt(int x, int y) {
		if (!pointInGrid(x, y))
			return null;

		return creatures[x][y];
	}

	/**
	 * Sets the creature at the given array coordinates in the world
	 * @param x the horizontal coordinate
	 * @param y the vertical coordinate
	 * @param creature the child of Organism 
	 */
	public void setAt(int x, int y, Organism creature) {
		creatures[x][y] = creature;
	}
	
	/**
	 * @param x the horizontal coordinate
	 * @param y the vertical coordinate
	 * @return true if the given coordinates are in the grid and false otherwise
	 */
	public boolean pointInGrid(int x, int y) {
		if (x >= GRID_SIZE_ROW || x < 0 || y >= GRID_SIZE_COL || y < 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * @param x the horizontal coordinate
	 * @param y the vertical coordinate
	 * @return true if cell inside the grid and does not contains creature
	 */
	public boolean isFreeCell(int x, int y) {
		if(pointInGrid(x, y)) {
			if(creatures[x][y]==null) {
				return true;
			}
		}
		return false;
	}
}
