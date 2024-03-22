// package life;

import java.awt.Color;

/**
 *  Plant class, represents a plant in the Game of Life.
 *  
 *  @author Calvin Lee
 *  @version 1.0
 */
public class Plant extends Lifeform implements edibleByHerbivore, edibleByOmnivore {

  // the minimum number of plants nearby to allow pollination
  private int pollinateCondition = 2;

  // the minimum number of empty cells nearby to allow pollination
  private int emptyCondition = 3;
  
  /**
   *  Plant constructor. Set its colour and action flag.
   */
  public Plant() {
    colour = Color.GREEN;
    hasAction = true;
  }
  
  /**
   *  takeAction: Inherited from Lifeform. The plant attempts to pollinate to a nearby cell
   *  based on how many nearby plants there are.
   *  @param nearbyCells is the array of cells surrounding the plant
   */
  public void takeAction(Cell[] surroundingCells) {

    if (countNearbyPlants(surroundingCells) >= pollinateCondition
      && countNearbyEmptyCells(surroundingCells) >= emptyCondition) {

      int target = RandomGenerator.nextNumber(surroundingCells.length);
      int itr = 0;
      int i = 0;
      boolean done = false;
      // count empty cells and loop until i == target
      while (!done) {
        if (!surroundingCells[itr].isOccupied()) {
          if (i == target) {
            surroundingCells[itr].setOccupant(new Plant());
            surroundingCells[itr].getOccupant().hasAction = false;
            done = true;
            continue;
          }
          else i++;
        }
        itr++;
        if (itr == surroundingCells.length) itr = 0;
      }
    }
    
    hasAction = false;
  }

  /**
   * countNearbyPlants: Count the number of plants surrounding this plant.
   * @param surroundingCells is an array containing the cells that surround this plant
   * @return the number of plants next to this plant
   */
  private int countNearbyPlants(Cell[] surroundingCells) {
    int count = 0;
    for (int i = 0; i < surroundingCells.length; i++) {
      if (surroundingCells[i].isOccupied()) {
        if (surroundingCells[i].getOccupant() instanceof Plant) count++;
      }
    }
    return count;
  }

  /**
   * countNearbyEmptyCells: Count the number of empty cells surrounding this plant.
   * @param surroundingCells is an array containing the cells that surround this plant
   * @return the number of empty cells surrounding this plant
   */
  private int countNearbyEmptyCells(Cell[] surroundingCells) {
    int count = 0;
    for (int i = 0; i < surroundingCells.length; i++) {
      if (!surroundingCells[i].isOccupied()) count++;
    }
    return count;
  }
}
