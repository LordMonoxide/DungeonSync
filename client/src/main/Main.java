package main;

import javax.swing.UIManager;

import dungeonsync.DungeonSync;

public class Main {
  public static void main(String[] args) {
    System.out.println("Starting...");
    
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch(Exception e) {
      e.printStackTrace();
    }
    
    new DungeonSync();
  }
}