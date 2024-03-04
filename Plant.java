// package life;

import java.awt.Color;

/**
 *  Plant class, represents a plant in the Game of Life.
 *  
 *  @author Calvin Lee
 *  @version 1.0
 */
public class Plant extends Lifeform {
  
  // constants/attributes
  
  /**
   *  Plant constructor. Set its colour and actions per turn.
   */
  public Plant() {
    colour = Color.GREEN;
    actionsRemaining = 1;
  }

  /**
   *  die: Implementation of method from Lifeform interface.
   */
  public void die() {

  }
  
  /**
   *  pollinate: The Plant attempts to procreate by spreading to a nearby cell
   *  based on how many nearby plants there are.
   *  @param nearbyCells is the array of empty cells to choose from
   *  @param index is the empty cell selected by the random generator 
   */
  public void pollinate(Cell[] nearbyCells, int index) {
    // create a new plant at the given cell, but don't let it take actions until the next update
    nearbyCells[index].setOccupant(new Plant());
    nearbyCells[index].getOccupant().actionsRemaining = 0;
    actionsRemaining--;
  }
  
}
