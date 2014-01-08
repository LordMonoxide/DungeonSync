package main;

import dungeonsync.DungeonSync;

public class Main {
  public static void main(String[] args) {
    System.out.println("Starting...");
    
    DungeonSync ds = new DungeonSync();
    ds.register("corey@narwhunderful.com", "monoxide");
  }
}