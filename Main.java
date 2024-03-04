// package life;

import javax.swing.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.BorderLayout;
import java.awt.*;

/**
 *  Main driver program for the Game of Life, COMP2522 Assignment 2.
 *  
 *  @author Calvin Lee
 *  @version 1.0
 */
public class Main {

  // number rows and cols to display to the player
  private static int rows = 20;
  private static int cols = 20;
  private static int sideLength = 30;   // length of a side in pixels
  
  /**
   * Main program. Runs the Game of Life.
   * @param args unused.
   */
  public static void main(String[] args) {

    Game g = new Game(rows, cols, sideLength);
    g.runGame();
  }
}
