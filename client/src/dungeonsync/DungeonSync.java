package dungeonsync;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import dungeonsync.api.API;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.Font;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

public class DungeonSync {
  private JFrame frame;
  private JPanel panel;
  private JTextField txtEmail;
  private JPasswordField txtPassword;
  private JPasswordField txtConfirm;
  private JButton btnLogIn;
  private JCheckBox chkNewAccount;
  
  private HashMap<String, String> langApp;
  
  public DungeonSync() {
    EventQueue.invokeLater(new Runnable() {
      @Override public void run() {
        initialize();
        frame.setVisible(true);
      }
    });
  }
  
  /**
   * @wbp.parser.entryPoint
   */
  private void initialize() {
    langApp = API.lang("app");
    
    frame = new JFrame();
    frame.setBounds(100, 100, 427, 232);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new BorderLayout(0, 0));
    
    panel = new JPanel();
    frame.getContentPane().add(panel, BorderLayout.CENTER);
    panel.setLayout(new FormLayout(new ColumnSpec[] {
        FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
        FormFactory.MIN_COLSPEC,
        FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
        FormFactory.GLUE_COLSPEC,
        FormFactory.DEFAULT_COLSPEC,
        FormFactory.DEFAULT_COLSPEC,
        FormFactory.LABEL_COMPONENT_GAP_COLSPEC,},
      new RowSpec[] {
        FormFactory.LINE_GAP_ROWSPEC,
        FormFactory.MIN_ROWSPEC,
        FormFactory.LINE_GAP_ROWSPEC,
        FormFactory.MIN_ROWSPEC,
        FormFactory.MIN_ROWSPEC,
        FormFactory.MIN_ROWSPEC,
        FormFactory.LINE_GAP_ROWSPEC,
        FormFactory.DEFAULT_ROWSPEC,
        FormFactory.LINE_GAP_ROWSPEC,}));
    
    JLabel lblTitle = new JLabel(langApp.get("title"));
    lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
    panel.add(lblTitle, "2, 2, 5, 1");
    
    JLabel lblEmail = new JLabel(langApp.get("email") + ":");
    panel.add(lblEmail, "2, 4, left, top");
    
    txtEmail = new JTextField();
    panel.add(txtEmail, "4, 4, 3, 1, fill, top");
    txtEmail.setColumns(10);
    
    JLabel lblPassword = new JLabel(langApp.get("password") + ":");
    panel.add(lblPassword, "2, 5, left, top");
    
    txtPassword = new JPasswordField();
    panel.add(txtPassword, "4, 5, 3, 1, fill, fill");
    
    final JLabel lblConfirm = new JLabel(langApp.get("password_confirmation") + ":");
    lblConfirm.setVisible(false);
    panel.add(lblConfirm, "2, 6, left, top");
    
    txtConfirm = new JPasswordField();
    txtConfirm.setVisible(false);
    panel.add(txtConfirm, "4, 6, 3, 1, fill, fill");
    
    chkNewAccount = new JCheckBox(langApp.get("newaccount"));
    chkNewAccount.addChangeListener(new ChangeListener() {
      @Override public void stateChanged(ChangeEvent arg0) {
        lblConfirm.setVisible(chkNewAccount.isSelected());
        txtConfirm.setVisible(chkNewAccount.isSelected());
      }
    });
    
    panel.add(chkNewAccount, "5, 8");
    
    btnLogIn = new JButton(langApp.get("login"));
    btnLogIn.addMouseListener(new MouseListener() {
      @Override public void mouseReleased(MouseEvent ev) { }
      @Override public void mousePressed(MouseEvent ev) { }
      @Override public void mouseExited(MouseEvent ev) { }
      @Override public void mouseEntered(MouseEvent ev) { }
      @Override public void mouseClicked(MouseEvent ev) {
        if(ev.getButton() == MouseEvent.BUTTON1) {
          if(chkNewAccount.isSelected()) {
            register();
          } else {
            login();
          }
        }
      }
    });
    
    panel.add(btnLogIn, "6, 8");
  }
  
  @SuppressWarnings("deprecation")
  private void register() {
    setEnabled(false);
    
    // We have no choice but to use getText() because URLEncoder sucks
    API.register(txtEmail.getText(), txtPassword.getText(), txtConfirm.getText());
  }
  
  @SuppressWarnings("deprecation")
  private void login() {
    setEnabled(false);
    
    // See register()
    API.login(txtEmail.getText(), txtPassword.getText());
  }
  
  private void setEnabled(boolean enabled) {
    txtEmail.setEnabled(enabled);
    txtPassword.setEnabled(enabled);
    txtConfirm.setEnabled(enabled);
    chkNewAccount.setEnabled(enabled);
    btnLogIn.setEnabled(enabled);
  }
}