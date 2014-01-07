package dungeonsync;

import javax.swing.JFrame;

public class DungeonSync {
  JFrame _frame;
  
  public DungeonSync() {
    _frame = new JFrame("Dungeon Sync");
    _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    _frame.setVisible(true);
  }
}