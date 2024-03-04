// package life;

import java.awt.Color;

/**
 * 
 */
public class Herbivore extends Animal {
  
  // variables/attributes
  
  /**
   *  Herbivore constructor. Set its colour and actions per turn
   */
  public Herbivore() {
    colour = Color.YELLOW;
    actionsRemaining = 1;
  }
  
  /**
   *  die: Inherited from Animal. Returns null to the reference of this animal.
   */
  public void die() {
    
  }

  /**
   *  eat: Inherited from Animal. The Herbivore shares a cell with a Plant and will eat it.
   */
  public void eat(Cell targetCell) {
    targetCell.setOccupant(null);
  }

  /**
   *  move: Inherited from Animal. The animal attempts to move to an adjacent cell.
   *  If the target cell contains an Animal, it will try to move to another one.
   *  @param currentCell is the cell is Herbivore is currently inhabiting
   *  @param nearbyCells is an array containing the cells adjacent/diagonal to the Herbivore
   *  @param index is a random number to choose which cell to move into
   */
  void move(Cell currentCell, Cell[] nearbyCells, int index) {
    int animalCount = 0;    // keep track of number of animals encountered
    boolean moved = false;
    while (actionsRemaining != 0) {
      Cell targetCell = nearbyCells[index];
      
      // if the target cell is occupied, check what it is
      if (targetCell.isOccupied()) {
        if (targetCell.getOccupant() instanceof Animal) {
          // try the next index if target cell has an animal, increment animal counter
          index = index == nearbyCells.length - 1 ? 0 : index + 1;
          animalCount++;
          // every nearby cell has an animal, can't move
          if (animalCount == nearbyCells.length) actionsRemaining = 0;
        }
        else if (targetCell.getOccupant() instanceof Plant) {
          eat(targetCell);    // change this section if other edible things introduced later
          turnsSinceEaten = -1;
          targetCell.setOccupant(this);
          currentCell.setOccupant(null);
          actionsRemaining = 0;
          moved = true;
        }
      }
      else {    // if target cell is not occupied, this herbivore moves into it
        targetCell.setOccupant(this);
        currentCell.setOccupant(null);
        actionsRemaining = 0;
        moved = true;
      }
    }
    turnsSinceEaten++;
    if (turnsSinceEaten == 5) {
      if (moved) nearbyCells[index].setOccupant(null);
      else currentCell.setOccupant(null);
    }
  }

}
