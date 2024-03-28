// package life;

import java.awt.Color;
import java.util.ArrayList;

/**
 *  Omnivore class, representing an Omnivore in the Game of Life simulation.
 * 
 *  @author Calvin Lee
 */
public class Omnivore extends Animal implements edibleByCarnivore {
  
  /**
   *  Omnivore constructor. Set its colour, action flag, and let it know where it is.
   */
  public Omnivore(Cell cell) {
    colour = Color.BLUE;
    hasAction = true;
    ownCell = cell;
  }

  /**
   *  eat: Inherited from Animal. The Omnivore has found something edible and will eat it.
   */
  protected void eat(Cell targetCell) {
    targetCell.removeOccupant();
    turnsSinceEaten = -1;
  }

  /**
   * move: Inherited from Animal. This Omnivore moves to the given cell.
   * @param targetCell is the cell for this Omnivore to move to.
   */
  protected void move(Cell targetCell) {
    targetCell.setOccupant(this);
    ownCell.removeOccupant();
    ownCell = targetCell;
  }

  /**
   *  takeAction: Inherited from Animal. This Omnivore will take its turn, and attempt to move and
   *  reproduce.
   *  @param surroundingCells is an array storing the Cells that surround this Omnivore
   */
  public void takeAction(Cell[] surroundingCells) {
    if (hasAction) {
      // if all nearby cells contain something this Omnivore can't eat, it can't move
      boolean canMove = false;
      for (Cell c : surroundingCells) {
        if (!c.isOccupied() || c.getOccupant() instanceof edibleByOmnivore) {
          canMove = true;
          break;
        }
      }

      // pick a valid cell to move to if it can move
      Cell targetCell = null;
      if (canMove) {
        boolean done = false;
        while (!done) {
          int random = RandomGenerator.nextNumber(surroundingCells.length);
          if (!surroundingCells[random].isOccupied()
            || surroundingCells[random].getOccupant() instanceof edibleByOmnivore) {
              targetCell = surroundingCells[random];
              done = true;
            }
        }
      }

      // move to cell if a valid one was found, eat if something is in it
      if (targetCell != null) {
        if (targetCell.isOccupied() ) {
          eat(targetCell);
        }
        move(targetCell);
      }

      // if this Omnivore can reproduce, do it
      if (canReproduce(surroundingCells)) {
        reproduce(findEmptyCells(surroundingCells));
      }

      // increment turnsSinceEaten, dead if 5 turns passed without eating
      turnsSinceEaten++;
      if (turnsSinceEaten >= 5) {
        ownCell.removeOccupant();
        ownCell = null;
      }

      hasAction = false;
    }
  }

  /**
   *  canReproduce: Checks the nearby Cells to see if it satisfies the Omnivore's reproducing
   *  criteria:
   *    at least 1 nearby Omnivore
   *    at least 3 empty nearby Cells
   *    at least 1 nearby Cells with food
   *  @param surroundingCells is an array containing the Cells that surround this animal,
   *  @return true if conditions are met, otherwise false.
   */
  protected boolean canReproduce(Cell[] surroundingCells) {
    int nearbyOmnivores = 0;
    int emptyCells = 0;
    int nearbyFood = 0;
    for (Cell c : surroundingCells) {
      if (!c.isOccupied()) emptyCells++;
      else if (c.getOccupant() instanceof Omnivore) nearbyOmnivores++;
      else if (c.getOccupant() instanceof edibleByOmnivore) nearbyFood++;
    }
    return (nearbyOmnivores >= 1 && emptyCells >= 3 && nearbyFood >= 1);
  }

  /**
   *  reproduce: This Omnivore will reproduce by creating another Omnivore at a valid empty
   *  Cell.
   *  @param emptyCells is an array containing empty cells that the Omnivore will choose to
   *  reproduce to.
   */
  protected void reproduce(Cell[] emptyCells) {
    Cell target = emptyCells[RandomGenerator.nextNumber(emptyCells.length)];
    target.setOccupant(new Omnivore(target));
    target.getOccupant().hasAction = false; // new Omnivore cannot take an action until next turn
  }

  /**
   *  isEdible: Inherited from Animal. Check to see if a Lifeform can be eaten by this Omnivore.
   *  @param target is the Lifeform to check.
   *  @return true if edible by this Omnivore, else false.
   */
  protected boolean isEdible(Lifeform target) {
    return (target instanceof edibleByOmnivore);
  }

  /**
   *  findEmptyCells: Finds the empty cells that neighbour this animal.
   *  @param surroundingCells is an array holding the Cells that surround this animal.
   *  @return an array containing empty Cells.
   */
  private Cell[] findEmptyCells(Cell[] surroundingCells) {
    ArrayList<Cell> tmp = new ArrayList<>();
    for (Cell c : surroundingCells) {
      if (!c.isOccupied()) tmp.add(c);
    }
    Cell[] cells = new Cell[tmp.size()];
    return tmp.toArray(cells);
  }
}
