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
import dungeonsync.api.Character;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.Font;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JList;

import org.json.JSONArray;
import org.json.JSONObject;

public class DungeonSync {
  private API api = API.instance();
  
  private JFrame frame;
  private JPanel pnlLogin;
  private JTextField txtEmail;
  private JPasswordField txtPassword;
  private JPasswordField txtConfirm;
  private JButton btnLogIn;
  private JCheckBox chkNewAccount;
  
  private HashMap<String, String> langApp;
  private JPanel pnlChars;
  private JList<String> lstChars;
  private Vector<Character> _char = new Vector<>();
  private Vector<String> _charName = new Vector<>();
  private JLabel lblErrors;
  private JLabel lblError;
  
  public DungeonSync() {
    EventQueue.invokeLater(new Runnable() {
      @Override public void run() {
        try {
          initialize();
        } catch(IOException e) {
          e.printStackTrace();
        }
        
        frame.setVisible(true);
      }
    });
  }
  
  /**
   * @throws IOException 
   * @wbp.parser.entryPoint
   */
  private void initialize() throws IOException {
    langApp = api.lang("app");
    
    frame = new JFrame();
    frame.setBounds(100, 100, 427, 356); // 232
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new BorderLayout(0, 0));
    
    pnlChars = new JPanel();
    frame.getContentPane().add(pnlChars, BorderLayout.NORTH);
    pnlChars.setLayout(new FormLayout(new ColumnSpec[] {
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
    
    JLabel lblTitleChars = new JLabel(langApp.get("title"));
    lblTitleChars.setFont(new Font("Tahoma", Font.PLAIN, 16));
    pnlChars.add(lblTitleChars, "2, 2, 5, 1");
    
    lstChars = new JList<>();
    pnlChars.add(lstChars, "4, 4, fill, fill");
    pnlChars.setVisible(false);
    
    pnlLogin = new JPanel();
    frame.getContentPane().add(pnlLogin, BorderLayout.CENTER);
    pnlLogin.setLayout(new FormLayout(new ColumnSpec[] {
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
        FormFactory.LINE_GAP_ROWSPEC,
        FormFactory.MIN_ROWSPEC,
        FormFactory.LINE_GAP_ROWSPEC,}));
    
    JLabel lblTitle = new JLabel(langApp.get("title"));
    lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
    pnlLogin.add(lblTitle, "2, 2, 5, 1");
    
    JLabel lblEmail = new JLabel(langApp.get("email") + ":");
    pnlLogin.add(lblEmail, "2, 4");
    
    txtEmail = new JTextField();
    pnlLogin.add(txtEmail, "4, 4, 3, 1, fill, top");
    txtEmail.setColumns(10);
    
    JLabel lblPassword = new JLabel(langApp.get("password") + ":");
    pnlLogin.add(lblPassword, "2, 5");
    
    txtPassword = new JPasswordField();
    pnlLogin.add(txtPassword, "4, 5, 3, 1, fill, fill");
    
    final JLabel lblConfirm = new JLabel(langApp.get("password_confirmation") + ":");
    lblConfirm.setVisible(false);
    pnlLogin.add(lblConfirm, "2, 6");
    
    txtConfirm = new JPasswordField();
    txtConfirm.setVisible(false);
    pnlLogin.add(txtConfirm, "4, 6, 3, 1, fill, fill");
    
    chkNewAccount = new JCheckBox(langApp.get("newaccount"));
    chkNewAccount.addChangeListener(new ChangeListener() {
      @Override public void stateChanged(ChangeEvent arg0) {
        lblConfirm.setVisible(chkNewAccount.isSelected());
        txtConfirm.setVisible(chkNewAccount.isSelected());
      }
    });
    
    lblErrors = new JLabel("Errors:");
    pnlLogin.add(lblErrors, "2, 8");
    pnlLogin.add(chkNewAccount, "5, 8");
    
    btnLogIn = new JButton(langApp.get("login"));
    btnLogIn.addMouseListener(new MouseListener() {
      @Override public void mouseReleased(MouseEvent ev) { }
      @Override public void mousePressed(MouseEvent ev) { }
      @Override public void mouseExited(MouseEvent ev) { }
      @Override public void mouseEntered(MouseEvent ev) { }
      @Override public void mouseClicked(MouseEvent ev) {
        if(ev.getButton() == MouseEvent.BUTTON1) {
          setEnabled(false);
          
          try {
            API.Response resp;
            
            if(chkNewAccount.isSelected()) {
              resp = api.register(txtEmail.getText(), txtPassword.getText(), txtConfirm.getText());
            } else {
              resp = api.login(txtEmail.getText(), txtPassword.getText());
            }
            
            if(resp.success()) {
              chars();
              
              pnlLogin.setVisible(false);
              pnlChars.setVisible(true);
            } else {
              JSONObject errors = resp.parseObject();
              StringBuilder s = new StringBuilder("<html>");
              for(Object k : errors.keySet()) {
                String key = (String)k;
                s.append(key);
                s.append(":<br>");
                
                JSONArray error = errors.getJSONArray(key);
                for(int i = 0; i < error.length(); i++) {
                  s.append(error.getString(i));
                  s.append("<br>");
                }
                
                s.append("<br>");
              }
              
              lblError.setText(s.toString());
            }
          } catch(IOException e) {
            e.printStackTrace();
          }
        }
      }
    });
    
    pnlLogin.add(btnLogIn, "6, 8");
    
    lblError = new JLabel("");
    pnlLogin.add(lblError, "2, 10, 5, 1");
  }
  
  private void chars() throws IOException {
    API.Response response = api.chars();
    
    if(response.success()) {
      _char.clear();
      _charName.clear();
      
      JSONArray chars = response.parseArray();
      for(int i = 0; i < chars.length(); i++) {
        _char.addElement(new Character(chars.getJSONObject(i)));
        _charName.add(_char.get(i).original);
      }
      
      lstChars.setListData(_charName);
    }
  }
  
  private void setEnabled(boolean enabled) {
    txtEmail.setEnabled(enabled);
    txtPassword.setEnabled(enabled);
    txtConfirm.setEnabled(enabled);
    chkNewAccount.setEnabled(enabled);
    btnLogIn.setEnabled(enabled);
  }
}