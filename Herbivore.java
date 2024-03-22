// package life;

import java.awt.Color;

/**
 * 
 */
public class Herbivore extends Animal implements edibleByCarnivore {
  
  // variables/attributes
  // the number of turns the this Herbivore can survive without eating
  private int starved = 5;

  /**
   *  Herbivore constructor. Set its colour and action flag.
   */
  public Herbivore(Cell cell) {
    colour = Color.YELLOW;
    hasAction = true;
    ownCell = cell;
  }

  /**
   *  eat: Inherited from Animal. The Herbivore shares a cell with a Plant and will eat it.
   */
  public void eat(Cell targetCell) {
    targetCell.removeOccupant();
    turnsSinceEaten = -1;
  }

  /**
   * move: Inherited from Animal. This Herbivore moves to the given cell.
   * @param targetCell is the cell for this Herbivore to move to.
   */
  public void move(Cell targetCell) {
    targetCell.setOccupant(this);
    ownCell.removeOccupant();
    ownCell = targetCell;
  }

  /**
   *  takeAction: Inherited from Animal. The animal attempts to move to an adjacent cell.
   *  If the target cell contains something edible by this Herbivore, this will eat that inhabitant.
   *  @param surroundingCells is an array storing the Cells that surround this Herbivore
   */
  void takeAction(Cell[] surroundingCells) {
    // pick a valid cell to target (any cell without an animal)
    if (hasAction) {
      Cell targetCell = null;
      int target = RandomGenerator.nextNumber(surroundingCells.length);
      int itr = 0;
      int i = 0;
      int animalCount = 0;
      boolean done = false;
      // loop until we've found a valid cell to target (empty cell or cell with something edible)
      while (!done) {
        if ((surroundingCells[itr].isOccupied() && isEdible(surroundingCells[itr].getOccupant()))
          || !surroundingCells[itr].isOccupied()) {
          if (i == target) {
            targetCell = surroundingCells[itr];
            done = true;
          }
          else i++;
        }
        // keep track of animals encountered, exit if count == length of array
        else {
          animalCount++;
          if (animalCount == surroundingCells.length) {
            done = true;
            continue;
          }
        }
        itr++;
        if (itr == surroundingCells.length) itr = 0;
      }

      // move to the target cell if it was found, eat if something in it
      if (targetCell != null) {
        if (targetCell.isOccupied() ) {
          eat(targetCell);
        }
        move(targetCell);
      }
      // increment turnsSinceEaten, dead if too many turns passed
      turnsSinceEaten++;
      if (turnsSinceEaten >= starved) {
        ownCell.removeOccupant();
        ownCell = null;
      }

      hasAction = false;
    }
  }

  /**
   *  isEdible: Inherited from Animal. Check to see if a Lifeform can be eaten by this Herbivore.
   *  @param target is the Lifeform to check.
   *  @return true if edible by this Herbivore, else false.
   */
  public boolean isEdible(Lifeform target) {
    return (target instanceof edibleByHerbivore);
  }
}
// OLD CODE BELOW, DELETE LATER.

//     boolean moved = false;
//     while (hasAction) {
//       Cell targetCell = nearbyCells[index];
      
//       // if the target cell is occupied, check what it is
//       if (targetCell.isOccupied()) {
//         if (targetCell.getOccupant() instanceof Animal) {
//           // try the next index if target cell has an animal, increment animal counter
//           index = index == nearbyCells.length - 1 ? 0 : index + 1;
//           animalCount++;
//           // every nearby cell has an animal, can't move
//           if (animalCount == nearbyCells.length) actionsRemaining = 0;
//         }
//         else if (targetCell.getOccupant() instanceof Plant) {
//           eat(targetCell);    // change this section if other edible things introduced later
//           turnsSinceEaten = -1;
//           targetCell.setOccupant(this);
//           currentCell.setOccupant(null);
//           actionsRemaining = 0;
//           moved = true;
//         }
//       }
//       else {    // if target cell is not occupied, this herbivore moves into it
//         targetCell.setOccupant(this);
//         currentCell.setOccupant(null);
//         actionsRemaining = 0;
//         moved = true;
//       }
//     }
//     turnsSinceEaten++;
//     if (turnsSinceEaten == 5) {
//       if (moved) nearbyCells[index].setOccupant(null);
//       else currentCell.setOccupant(null);
//     }
//   }

// }
