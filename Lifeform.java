// package life;

import java.awt.Color;

/**
 *  Lifeform abstract class, used as a template for plants and animals.
 *  
 *  @author Calvin Lee
 *  @version 1.0
 */
public abstract class Lifeform {
  
  // colour (determined by child classes) to display in the world
  Color colour;

  // indicate if a lifeform has an action to take this turn or not
  boolean hasAction;

  // takeAction: to be overwritten by child classes, causes the lifeform to take an action
  abstract void takeAction(Cell[] surroundingCells);
  
}
