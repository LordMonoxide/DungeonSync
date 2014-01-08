package main;

import dungeonsync.DungeonSync;

public class Main {
  public static void main(String[] args) {
    System.out.println("Starting...");
    
    DungeonSync ds = new DungeonSync();
    ds.request("api/register/", "PUT", ds.param("email", "corey2@narwhunderful.com"), ds.param("password", "monoxide"), ds.param("password_confirmation", "monoxide"));
  }
}