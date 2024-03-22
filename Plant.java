// package life;

import java.awt.Color;

/**
 *  Plant class, represents a plant in the Game of Life.
 *  
 *  @author Calvin Lee
 */
public class Plant extends Lifeform implements edibleByHerbivore, edibleByOmnivore {
  
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
    if (canReproduce(surroundingCells)) {
      reproduce(surroundingCells);
    }
    
    hasAction = false;
  }
  // can give birth if there are at least 2 plant neighbors, and at least 3 free neighboring cells, 
  //          and 0 amount of food in neighboring cells
  /**
   *  canReproduce: Inherited from Lifeform. Check nearby cells to see if conditions are met
   *  for this Plant to reproduce.
   *  @param surroundingCells is an array containing the Cells that neighbour this Plant.
   */
  protected boolean canReproduce(Cell[] surroundingCells) {
    int nearbyPlants = 0;
    int emptyCells = 0;
    for (Cell c : surroundingCells) {
      if (!c.isOccupied()) emptyCells++;
      else if (c.getOccupant() instanceof Plant) nearbyPlants++;
    }
    return (nearbyPlants >= 2 && emptyCells >= 3);
  }

  /**
   *  reproduce: Inherited by Lifeform. Creates the same, new lifeform at an empty cell.
   *  @param surroundingCells is an array of cells that neighbour this Plant.
   */
  protected void reproduce(Cell[] surroundingCells) {
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

  /**
   * countNearbyPlants: Count the number of plants surrounding this plant.
   * @param surroundingCells is an array containing the cells that surround this plant
   * @return the number of plants next to this plant
   */
  // private int countNearbyPlants(Cell[] surroundingCells) {
  //   int count = 0;
  //   for (Cell c : surroundingCells) {
  //     if (c.isOccupied() && c.getOccupant() instanceof Plant) {
  //       count++;
  //     }
  //   }
  //   return count;
  // }

  /**
   * countNearbyEmptyCells: Count the number of empty cells surrounding this plant.
   * @param surroundingCells is an array containing the cells that surround this plant
   * @return the number of empty cells surrounding this plant
   */
  // private int countNearbyEmptyCells(Cell[] surroundingCells) {
  //   int count = 0;
  //   for (Cell c : surroundingCells) {
  //     if (!c.isOccupied()) count++;
  //   }
  //   return count;
  // }
}
