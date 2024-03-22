// package life;

import javax.swing.*;
// import javax.swing.event.MouseInputListener;

import java.awt.event.*;
import java.awt.*;

/**
 *  Game class, emulates the Game of Life. Contains the world in which lifeforms
 *  live in.
 *  
 *  @author Calvin Lee
 */
public class Game extends JFrame {
  
  // constants/variables
  // private int turnCount;        // keeps track of turns
  private int cellSize;    // x by x size of cell in pixels
  
  private World world;
  
  /**
   *  Game constructor. Creates and initializes the world.
   *  
   *  @param rows is the number of cells to hold in the y-axis.
   *  @param cols is the number of cells to hold in the x-axis.
   *  @param sideLength is the number of pixels for a side length of the cell.
   */
  public Game(int rows, int cols, int sideLength) {
    
    // Setting attributes for the frame
    super("Game of Life");
    cellSize = sideLength;
    setSize(cols * cellSize, rows * cellSize);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    // Create new world object with appropriate size
    createWorld(rows, cols, cellSize);
    
    // Add mouse listener to listen for clicks to progress the game
    addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent me) {
        updateGame();
      }
    });
    
    // Final things to set up
    // turnCount = 0;
  }
  
  /**
   *  runGame: Starts the game by displaying it to the user.
   */
  public void runGame() {
    setVisible(true);
  }
  
  /**
   *  updateGame: Called when user clicks the window. The game progresses
   *  by one time unit.
   */
  private void updateGame() {
    // turnCount++;
    world.updateWorld();
  }
  
  /**
   *  generateWorld: Supporter method for the constructor, creates and sets
   *  the initial state of the world for the Game of Life.
   * 
   *  Note: https://docs.oracle.com/javase/8/docs/api/java/awt/GridLayout.html
   *        ^ API for GridLayout to show how components are added.
   * 
   *  @param rows is the number of cells in the y-axis.
   *  @param cols is the number of cells in the x-axis.
   *  @param sideLength is the number of pixels for one side of the cell.
   */
  private void createWorld(int rows, int cols, int sideLength) {
    world = new World(rows, cols, cellSize);
    world.setSize(cols * cellSize, rows * cellSize);
    world.setLayout(new GridLayout(rows, cols));
    world.setBackground(Color.WHITE);
    this.add(world);
    world.setVisible(true);
  }
}
