// package life;

import java.awt.Color;

/**
 *  Lifeform interface, used as a template for plants and animals.
 *  
 *  @author Calvin Lee
 *  @version 1.0
 */
public abstract class Lifeform {
  
  // colour (determined by child classes) to display in the world
  Color colour;

  // the number of actions this lifeform can take in during one time unit
  // all lifeforms take 1 action per time unit unless a new specification is introduced later
  public int actionsRemaining;
  
  /**
   *  die: Called when the lifeform has died.
   */
  public abstract void die();
}
