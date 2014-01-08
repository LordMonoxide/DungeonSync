package main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.swing.SwingUtilities;

import dungeonsync.DungeonSync;

public class Main {
  public static void main(String[] args) throws MalformedURLException, UnsupportedEncodingException {
    System.out.println("Starting...");
    
    /*SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new DungeonSync();
      }
    });*/
    
    String params = "email="    + URLEncoder.encode("corey@narwhunderful.com", "UTF-8") +
                   "&password=" + URLEncoder.encode("monoxide", "UTF-8");
    
    HttpURLConnection con;
    
    URL url = new URL("http://dungeonsync.monoxidedesign.com");
    
    try {
      con = (HttpURLConnection)url.openConnection();
      con.setRequestMethod("POST");
      con.setRequestProperty("Content-Type", "x-www-form-urlencoded");
      con.setRequestProperty("Content-Length", Integer.toString(params.getBytes().length));
      con.setRequestProperty("Content-Language", "en-US");
      con.setUseCaches(false);
      con.setDoInput(true);
      con.setDoOutput(true);
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
}