// package life;

/**
 *  Animal abstract class, to be extended by any animals in the Game of Life.
 *  
 *  @author Calvin Lee
 *  @version 1.0
 */
public abstract class Animal extends Lifeform {

  // counter that keeps track of how long the animal last ate
  int turnsSinceEaten;
  
  /**
   *  Animal interface constructor, initially sets turnsSinceEaten.
   */
  public Animal() {
    turnsSinceEaten = 0;
  }
  
  /**
   *  eat: The calling animal is standing over some edible and is going
   *  to eat it.
   *  @param targetCell is the cell containing what is going to be eaten
   */
  abstract void eat(Cell targetCell);
  
  /**
   *  move: The calling animal is attempting to move to an adjacent cell.
   *  @param currentCell is the cell the animal currently inhabits
   *  @param nearbyCells is an array containing the cells adjacent/diagonal to the Animal
   *  @param index is a random number to choose which cell to move into
   */
  abstract void move(Cell currentCell, Cell[] nearbyCells, int index);
}
