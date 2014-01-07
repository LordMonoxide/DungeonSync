package main;

import javax.swing.SwingUtilities;

import dungeonsync.DungeonSync;

public class Main {
  public static void main(String[] args) {
    System.out.println("Starting...");
    
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new DungeonSync();
      }
    });
  }
}