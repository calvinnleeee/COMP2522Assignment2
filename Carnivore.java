// package life;

import java.awt.Color;
import java.util.ArrayList;

/**
 *  Carnivore class, representing an Carnivore in the Game of Life simulation.
 * 
 *  @author Calvin Lee
 */
public class Carnivore extends Animal implements edibleByOmnivore {
  
  /**
   *  Carnivore constructor. Set its colour, action flag, and let it know where it is.
   */
  public Carnivore(Cell cell) {
    colour = Color.RED;
    hasAction = true;
    ownCell = cell;
  }

  /**
   *  eat: Inherited from Animal. The Carnivore has found something edible and will eat it.
   */
  protected void eat(Cell targetCell) {
    targetCell.removeOccupant();
    turnsSinceEaten = -1;
  }

  /**
   * move: Inherited from Animal. This Carnivore moves to the given cell.
   * @param targetCell is the cell for this Carnivore to move to.
   */
  protected void move(Cell targetCell) {
    targetCell.setOccupant(this);
    ownCell.removeOccupant();
    ownCell = targetCell;
  }

  /**
   *  takeAction: Inherited from Animal. This Carnivore will take its turn, and attempt to move and
   *  reproduce.
   *  @param surroundingCells is an array storing the Cells that surround this Carnivore
   */
  public void takeAction(Cell[] surroundingCells) {
    if (hasAction) {
      // if all nearby cells contain something this Carnivore can't eat, it can't move
      boolean canMove = false;
      for (Cell c : surroundingCells) {
        if (!c.isOccupied() || c.getOccupant() instanceof edibleByCarnivore) {
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
            || surroundingCells[random].getOccupant() instanceof edibleByCarnivore) {
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

      // if this Carnivore can reproduce, do it
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
   *  canReproduce: Checks the nearby Cells to see if it satisfies the Carnivore's reproducing
   *  criteria:
   *    at least 1 nearby Carnivore
   *    at least 3 empty nearby Cells
   *    at least 2 nearby Cells with food
   *  @param surroundingCells is an array containing the Cells that surround this animal,
   *  @return true if conditions are met, otherwise false.
   */
  protected boolean canReproduce(Cell[] surroundingCells) {
    int nearbyCarnivores = 0;
    int emptyCells = 0;
    int nearbyFood = 0;
    for (Cell c : surroundingCells) {
      if (!c.isOccupied()) emptyCells++;
      else if (c.getOccupant() instanceof Carnivore) nearbyCarnivores++;
      else if (c.getOccupant() instanceof edibleByCarnivore) nearbyFood++;
    }
    return (nearbyCarnivores >= 1 && emptyCells >= 3 && nearbyFood >= 2);
  }

  /**
   *  reproduce: This Carnivore will reproduce by creating another Carnivore at a valid empty
   *  Cell.
   *  @param emptyCells is an array containing empty cells that the Carnivore will choose to
   *  reproduce to.
   */
  protected void reproduce(Cell[] emptyCells) {
    Cell target = emptyCells[RandomGenerator.nextNumber(emptyCells.length)];
    target.setOccupant(new Carnivore(target));
    target.getOccupant().hasAction = false; // new Carnivore cannot take an action until next turn
  }

  /**
   *  isEdible: Inherited from Animal. Check to see if a Lifeform can be eaten by this Carnivore.
   *  @param target is the Lifeform to check.
   *  @return true if edible by this Carnivore, else false.
   */
  protected boolean isEdible(Lifeform target) {
    return (target instanceof edibleByCarnivore);
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
