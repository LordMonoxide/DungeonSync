package dungeonsync.api;

import org.json.JSONObject;

public class Character {
  public final int ID;
  public final int userID;
  public final String filename;
  public final String original;
  
  public Character(int ID, int userID, String filename, String original) {
    this.ID = ID;
    this.userID = userID;
    this.filename = filename;
    this.original = original;
  }
  
  public Character(JSONObject json) {
    this(json.getInt("id"), json.getInt("user_id"), json.getString("filename"), json.getString("original"));
  }
}