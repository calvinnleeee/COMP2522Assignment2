// package life;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.BorderLayout;
import java.awt.*;


/**
 *  World class for the Game of Life. Contains methods for interacting with
 *  its cells and their lifeforms.
 *  
 *  @author Calvin Lee
 *  @version 1.0
 */
public class World extends JPanel {
  
  // constants/variables
  private Cell cellArr[][];                 // array that holds Cells
  private final int makeHerbivore = 85;     // chance to make an herbivore
  private final int makePlant = 65;         // chance to make a plant
  private final int cellSize;               // width/height of a cell

  private final int xbound;                 // x-axis boundary
  private final int ybound;                 // y-axis boundary


  /**
   *  World constructor. Generates the initial world contents.
   *  
   *  @param rows is the number of cells in the y-axis.
   *  @param cols is the number of cells in the x-axis.
   */
  public World(int rows, int cols, int sideLength) {
    cellSize = sideLength;
    xbound = cols;
    ybound = rows;
    
    // create and fill the array of cells
    cellArr = new Cell[cols][rows];
    generateWorld(rows, cols);
  }

  /**
   *  updateWorld: Updates the world after a time unit has passed. Cell contents
   *  will take actions.
   */
  public void updateWorld() {
    for (int x = 0; x < xbound; x++) {
      for (int y = 0; y < ybound; y++) {
        // if the cell has an occupant, tell it to do something
        if (cellArr[x][y].isOccupied()) {

          Lifeform current = cellArr[x][y].getOccupant();
          // if the current occupant is a plant and has an action left to take
          if (current instanceof Plant && current.actionsRemaining != 0) {
            // if number of plants and empty cells nearby satisfies requirement
            // for pollination, then pollinate 
            if (countNearbyPlants(x, y) == 4 && countNearbyEmptyCells(x, y) >= 3) {
              Plant tmp = (Plant) current;
              Cell[] emptyAdjacentCells = getNearbyEmptyCells(x, y);
              int rndNum = RandomGenerator.nextNumber(emptyAdjacentCells.length);
              tmp.pollinate(emptyAdjacentCells, rndNum);
            }
            
          }
          // if the current occupant is an animal and has a remaining action to take
          else if (current instanceof Animal && current.actionsRemaining != 0) {
            // change this section later if other animals are introduced
            Herbivore tmp = (Herbivore) current;
            Cell[] adjacentCells = getNearbyCells(x, y);
            int rndNum = RandomGenerator.nextNumber(adjacentCells.length);
            tmp.move(cellArr[x][y], adjacentCells, rndNum);
          }
        }
      }
    }

    // reset action counter for all lifeforms in the world to prepare for next update
    for (int x = 0; x < xbound; x++) {
      for (int y = 0; y < ybound; y++) {
        if (cellArr[x][y].isOccupied()) cellArr[x][y].getOccupant().actionsRemaining++;
      }
    }
    repaint();
    // System.out.println("clicked me!");
  }

  /**
   *  generateWorld: Set the initial state of the world by filling the
   *  Cell array and randomly generating their contents.
   *  
   *  @param rows is the number of cells in the y-axis.
   *  @param cols is the number of cells in the x-axis.
   */
  private void generateWorld(int rows, int cols) {
    for (int x = 0; x < cols; x++) {
      for (int y = 0; y < rows; y++) {
        Cell tmp = new Cell(cellSize);
        
        // generate random contents
        int random = RandomGenerator.nextNumber(100);
        if (random >= makeHerbivore) {
          tmp.setOccupant(new Herbivore());
        }
        else if (random >= makePlant) {
          tmp.setOccupant(new Plant());
        }
        cellArr[x][y] = tmp;
        add(cellArr[x][y]);
      }
    }
  }

  /**
   *  getNearbyCells: Find the cells adjacent/diagonal to the specified cell.
   * 
   *  @param col is the column/x index of the specified cell
   *  @param row is the row/y index of the specified cell
   *  @return an array containing the nearby cells
   */
  private Cell[] getNearbyCells(int col, int row) {
    ArrayList<Cell> nearby = new ArrayList<>();
    for (int x = -1; x < 2; x++) {
      for (int y = -1; y < 2; y++) {
        // don't add the input cell to the list, we want the ones around it
        if (x != 0 || y != 0) {
          // move on if the cell is out of bounds
          if (col + x < 0 || col + x >= xbound || row + y < 0 || row + y >= ybound) continue;
          nearby.add(cellArr[col + x][row + y]);
        }
      }
    }
    Cell[] nearbyArr = new Cell[nearby.size()];
    nearby.toArray(nearbyArr);
    return nearbyArr;
  }

  /**
   *  countNearbyPlants: Counts the number of plants adjacent/diagonal to the given cell.
   *  @param col is the given cell's x coordinate
   *  @param row is the given cell's y coordinate
   *  @return the number of plants found nearby
   */
  private int countNearbyPlants(int col, int row) {
    int count = 0;

    for (int x = -1; x < 2; x++) {
      for (int y = -1; y < 2; y++) {
        // don't add the input cell to the list, we want the ones around it
        if (x != 0 || y != 0) {
          if (col + x < 0 || col + x >= xbound || row + y < 0 || row + y >= ybound) continue;
          if (cellArr[col + x][row + y].isOccupied()
              && cellArr[col + x][row + y].getOccupant() instanceof Plant) {
                count++;
          }
        }
      }
    }
    return count;
  }

  /**
   *  countNearbyEmptyCells: Counts the number of unoccupied cells around a given cell.
   *  @param col is the given cell's x coordinate
   *  @param row is the given cell's y coordinate
   *  @return the number of empty cells nearby
   */
  private int countNearbyEmptyCells(int col, int row) {
    // get the nearby cells first
    Cell[] cellsToCheck = getNearbyCells(col, row);

    // increment counter and return it for each cell with no occupant
    int count = 0;
    for (Cell c : cellsToCheck) {
      if (!c.isOccupied()) count++;
    }
    return count;
  }

  /**
   *  getNearbyEmptyCells: Find the empty cells adjacent/diagonal to the specified cell.
   *  @param col is the x-coordinate of the given cell
   *  @param row is the y-coordinate of the given cell
   *  @return an array of empty cells
   */
  private Cell[] getNearbyEmptyCells(int col, int row) {
    ArrayList<Cell> nearby = new ArrayList<>();
    for (int x = -1; x < 2; x++) {
      for (int y = -1; y < 2; y++) {
        // don't add the input cell to the list, we want the ones around it
        if (x != 0 || y != 0) {
          // move on if the cell is out of bounds, add if it's unoccupied
          if (col + x < 0 || col + x >= xbound || row + y < 0 || row + y >= ybound) continue;
          if (!cellArr[col + x][row + y].isOccupied()) nearby.add(cellArr[col + x][row + y]);
        }
      }
    }

    Cell[] nearbyEmptyArr = new Cell[nearby.size()];
    nearby.toArray(nearbyEmptyArr);
    return nearbyEmptyArr;
  }
  
}
