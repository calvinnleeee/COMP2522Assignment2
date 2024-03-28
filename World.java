// package life;

import javax.swing.*;
import java.util.ArrayList;
// import java.awt.event.*;
// import java.awt.Graphics;
// import java.awt.Color;
// import java.awt.Container;
// import java.awt.Dialog;
// import java.awt.BorderLayout;
// import java.awt.*;


/**
 *  World class for the Game of Life. Contains methods for interacting with
 *  its cells and their lifeforms.
 *  
 *  @author Calvin Lee
 */
public class World extends JPanel {
  
  // constants/variables
  private Cell cellArr[][];                 // array that holds Cells
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
          // get the cell's surrounding cells, pass it as argument to lifeform's takeAction method
          Cell[] surroundingCells = getNearbyCells(x, y);
          current.takeAction(surroundingCells);
        }
      }
    }

    // reset action counter for all lifeforms in the world to prepare for next update
    for (int x = 0; x < xbound; x++) {
      for (int y = 0; y < ybound; y++) {
        if (cellArr[x][y].isOccupied()) cellArr[x][y].getOccupant().hasAction = true;
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
    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < cols; x++) {
        Cell tmp = new Cell(cellSize);
        
        // generate random contents
        int random = RandomGenerator.nextNumber(100);
        if (random >= 80) {
          tmp.setOccupant(new Herbivore(tmp));
        }
        else if (random >= 60) {
          tmp.setOccupant(new Plant());
        }
        else if (random >= 50) {
          tmp.setOccupant(new Carnivore(tmp));
        }
        else if (random >= 45) {
          tmp.setOccupant(new Omnivore(tmp));
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
}
