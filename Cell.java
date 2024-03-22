// package life;

import javax.swing.*;
import javax.swing.border.Border;
// import java.awt.event.*;
import java.awt.*;

/**
 *  Cell class, may or may not contain a lifeform in the Game of Life.
 *  
 *  @author Calvin Lee
 *  @version 1.0
 */
public class Cell extends JComponent {
  
  // constants/variables
  private Border border = BorderFactory.createLineBorder(Color.BLACK);
  private int sideLength;        // width/length in pixels
  private Lifeform occupant;

  /**
   *  Cell constructor. Sets some initial variables for display in the world.
   *  
   *  @param sideLength of one side of the cell (square) in pixels
   */
  public Cell (int sideLength) {
    setPreferredSize(new Dimension(sideLength, sideLength));
    this.sideLength = sideLength;
    occupant = null;
    setBorder(border);
    setOpaque(true);
  }

  /**
   *  isOccupied: Checks if the Cell is occupied by a lifeform.
   *  
   *  @return true if occupied, false otherwise.
   */
  public boolean isOccupied() {
    return occupant == null ? false : true;
  }
  
  /**
   *  setOccupant: Sets the cell's new occupant.
   * 
   *  @param lifeform that will now occupy this cell.
   */
  public void setOccupant(Lifeform lifeform) {
    occupant = lifeform;
  }

  /**
   *  getOccupant: Get the cell's current occupant.
   * 
   *  @return the occupant of this cell.
   */
  public Lifeform getOccupant() {
    return occupant;
  }

  /**
   *  removeOccupant: Destroys the cell's current occupant.
   */
  public void removeOccupant() {
    occupant = null;
  }
  
  /**
   *  paintComponent: Overrides method to tell the cell what colour to paint itself.
   *  
   *  @param g is the graphics object of this cell.
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    // set the colour depending on the occupant's colour, white if the cell is empty
    if (isOccupied()) {
      g.setColor(this.occupant.colour);
    }
    else {
      g.setColor(Color.WHITE);
    }
    
    g.fillRect(0,  0, sideLength, sideLength);
  }
}
