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

  // 
  Cell ownCell;
  
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
   *  @param targetCell is the target cell for this animal to move to.
   */
  abstract void move(Cell targetCell);

  /**
   * isEdible: The calling animal checks if the target Lifeform is edible by them.
   * @param target is the lifeform to check.
   * @return true if the target is edible by this Animal, else false.
   */
  abstract boolean isEdible(Lifeform target);
}
