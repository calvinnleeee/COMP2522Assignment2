// package life;

import java.awt.Color;
import java.util.ArrayList;

/**
 *  Herbivore class, representing an Herbivore in the Game of Life simulation.
 * 
 *  @author Calvin Lee
 */
public class Herbivore extends Animal implements edibleByCarnivore, edibleByOmnivore {
  
  /**
   *  Herbivore constructor. Set its colour, action flag, and let it know where it is.
   */
  public Herbivore(Cell cell) {
    colour = Color.YELLOW;
    hasAction = true;
    ownCell = cell;
  }

  /**
   *  eat: Inherited from Animal. The Herbivore shares a cell with a Plant and will eat it.
   */
  protected void eat(Cell targetCell) {
    targetCell.removeOccupant();
    turnsSinceEaten = -1;
  }

  /**
   * move: Inherited from Animal. This Herbivore moves to the given cell.
   * @param targetCell is the cell for this Herbivore to move to.
   */
  protected void move(Cell targetCell) {
    targetCell.setOccupant(this);
    ownCell.removeOccupant();
    ownCell = targetCell;
  }

  /**
   *  takeAction: Inherited from Animal. This Herbivore will take its turn, and attempt to move and
   *  reproduce.
   *  @param surroundingCells is an array storing the Cells that surround this Herbivore
   */
  public void takeAction(Cell[] surroundingCells) {
    if (hasAction) {
      // if all nearby cells contain something this Herbivore can't eat, it can't move
      boolean canMove = false;
      for (Cell c : surroundingCells) {
        if (!c.isOccupied() || c.getOccupant() instanceof edibleByHerbivore) {
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
            || surroundingCells[random].getOccupant() instanceof edibleByHerbivore) {
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

      // if this Herbivore can reproduce, do it
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
   *  canReproduce: Checks the nearby Cells to see if it satisfies the Herbivore's reproducing
   *  criteria:
   *    at least 1 nearby Herbivore
   *    at least 2 empty nearby Cells
   *    at least 2 nearby Cells with food
   *  @param surroundingCells is an array containing the Cells that surround this animal,
   *  @return true if conditions are met, otherwise false.
   */
  protected boolean canReproduce(Cell[] surroundingCells) {
    int nearbyHerbivores = 0;
    int emptyCells = 0;
    int nearbyFood = 0;
    for (Cell c : surroundingCells) {
      if (!c.isOccupied()) emptyCells++;
      else if (c.getOccupant() instanceof Herbivore) nearbyHerbivores++;
      else if (c.getOccupant() instanceof edibleByHerbivore) nearbyFood++;
    }
    return (nearbyHerbivores >= 1 && emptyCells >= 2 && nearbyFood >= 2);
  }

  /**
   *  reproduce: This Herbivore will reproduce by creating another Herbivore at a valid empty
   *  Cell.
   *  @param emptyCells is an array containing empty cells that the Herbivore will choose to
   *  reproduce to.
   */
  protected void reproduce(Cell[] emptyCells) {
    Cell target = emptyCells[RandomGenerator.nextNumber(emptyCells.length)];
    target.setOccupant(new Herbivore(target));
    target.getOccupant().hasAction = false; // new Herbivore cannot take an action until next turn
  }

  /**
   *  isEdible: Inherited from Animal. Check to see if a Lifeform can be eaten by this Herbivore.
   *  @param target is the Lifeform to check.
   *  @return true if edible by this Herbivore, else false.
   */
  protected boolean isEdible(Lifeform target) {
    return (target instanceof edibleByHerbivore);
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
