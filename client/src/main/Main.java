package main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.swing.SwingUtilities;

import org.json.HTTP;
import org.json.HTTPTokener;
import org.json.JSONObject;
import org.json.JSONTokener;

import dungeonsync.DungeonSync;

public class Main {
  public static void main(String[] args) throws UnsupportedEncodingException {
    System.out.println("Starting...");
    
    /*SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new DungeonSync();
      }
    });*/
    
    String params = "email="                 + URLEncoder.encode("corey@narwhunderful.com", "UTF-8") +
                   "&password="              + URLEncoder.encode("monoxide", "UTF-8") +
                   "&password_confirmation=" + URLEncoder.encode("monoxide", "UTF-8");
    
    HttpURLConnection con = null;
    
    URL url;
    
    try {
      url = new URL("http://dungeonsync.monoxidedesign.com/api/register");
    } catch(MalformedURLException e) {
      e.printStackTrace();
      return;
    }
    
    try {
      con = (HttpURLConnection)url.openConnection();
      con.setRequestMethod("PUT");
      con.setRequestProperty("Accept-Charset", "UTF-8");
      con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
      con.setUseCaches(false);
      con.setDoInput(true);
      con.setDoOutput(true);
      
      OutputStream out = con.getOutputStream();
      out.write(params.getBytes("UTF-8"));
      out.close();
      
      InputStream is = con.getInputStream();
      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
      String line;
      StringBuffer resp = new StringBuffer();
      
      while((line = rd.readLine()) != null) {
        line = line.substring(1, line.length() - 1);
        line = line.replaceAll("\\\\\"", "\"");
        resp.append(line);
        resp.append('\r');
      }
      
      rd.close();
      
      JSONObject json = new JSONObject(new JSONTokener(con.getInputStream()));
      System.out.println(json);
    } catch(IOException e) {
      e.printStackTrace();
      
      if(con != null) {
        BufferedReader rd = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        String line;
        StringBuffer resp = new StringBuffer();
        
        try {
          while((line = rd.readLine()) != null) {
            line = line.substring(1, line.length() - 1);
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
        
        JSONObject json = new JSONObject(new JSONTokener(resp.toString()));
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
}