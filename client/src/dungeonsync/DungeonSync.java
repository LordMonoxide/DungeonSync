package dungeonsync;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.swing.JFrame;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class DungeonSync {
  private static final String baseURL = "http://dungeonsync.monoxidedesign.com/api/auth/";

  JFrame _frame;
  
  
  public DungeonSync() {
    _frame = new JFrame("Dungeon Sync");
    _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    _frame.setVisible(true);
  }
  
  public void register(String email, String password) {
    request("register", "PUT", param("email", email), param("password", password), param("password_confirmation", password));
  }
  
  public void login(String email, String password) {
    request("login", "PUT", param("email", email), param("password", password));
  }
  
  public void request(String url, String method, Parameter... param) {
    request(url, method, "UTF-8", param);
  }
  
  public void request(String url, String method, String encoding, Parameter... param) {
    HttpURLConnection con = null;
    try {
      con = (HttpURLConnection)new URL(baseURL + url).openConnection();
      con.setRequestMethod("PUT");
      con.setRequestProperty("Accept-Charset", encoding);
      con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + encoding);
      con.setUseCaches(false);
      con.setDoInput(true);
      con.setDoOutput(true);
      
      String params = "";
      for(Parameter p : param) {
        if(params.length() != 0) { params += "&"; }
        params += p.key + "=" + URLEncoder.encode(p.val, encoding);
      }
      
      OutputStream out = con.getOutputStream();
      out.write(params.getBytes("UTF-8"));
      out.close();
      
      JSONObject json = JSONFromInputStream(con.getInputStream());
      System.out.println(json);
    } catch(MalformedURLException e) {
      e.printStackTrace();
    } catch(IOException e) {
      e.printStackTrace();
      
      if(con != null) {
        JSONObject json = JSONFromInputStream(con.getErrorStream());
        for(Object err : json.keySet()) {
          String error = (String)err;
          System.out.println(error + ":");
          
          for(int i = 0; i < json.getJSONArray(error).length(); i++) {
            System.out.println(json.getJSONArray(error).get(i));
          }
        }
      }
    }
  }
  
  public Parameter param(String key, String val) {
    return new Parameter(key, val);
  }
  
  private JSONObject JSONFromInputStream(InputStream is) {
    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
    String line;
    StringBuffer resp = new StringBuffer();
    
    try {
      while((line = rd.readLine()) != null) {
        if(line.length() > 1) {
          line = line.substring(1, line.length() - 1);
        }
        
        line = line.replaceAll("\\\\\"", "\"");
        resp.append(line);
        resp.append('\r');
      }
    } catch(IOException e1) {
      e1.printStackTrace();
    }
    
    try {
      rd.close();
    } catch(IOException e1) { }
    
    try {
      return new JSONObject(new JSONTokener(resp.toString()));
    } catch(JSONException e) {
      System.out.println(resp.toString());
      return null;
    }
  }
  
  public static class Parameter {
    public final String key, val;
    public Parameter(String key, String val) {
      this.key = key;
      this.val = val;
    }
  }
}