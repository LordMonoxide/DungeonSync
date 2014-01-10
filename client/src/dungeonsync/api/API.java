package dungeonsync.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class API {
  private static final String baseURL = "http://dungeonsync.monoxidedesign.com/api/";

  public static HashMap<String, String> lang(String type) {
    JSONObject json = request("lang/" + type);
    HashMap<String, String> lang = new HashMap<>();
    
    for(Object k : json.keySet()) {
      String key = (String)k;
      lang.put(key, json.getString(key));
    }
    
    return lang;
  }
  
  public static JSONObject register(String email, String password, String confirmation) {
    return request("auth/register", "PUT", param("email", email), param("password", password), param("password_confirmation", confirmation));
  }
  
  public static JSONObject login(String email, String password) {
    return request("auth/login", "PUT", param("email", email), param("password", password));
  }
  
  public static JSONObject request(String url, Parameter... param) {
    return request(url, "GET", param);
  }
  
  public static JSONObject request(String url, String method, Parameter... param) {
    return request(url, method, "UTF-8", param);
  }
  
  public static JSONObject request(String url, String method, String encoding, Parameter... param) {
    HttpURLConnection con = null;
    
    try {
      con = (HttpURLConnection)new URL(baseURL + url).openConnection();
      con.setRequestMethod(method);
      con.setRequestProperty("Accept-Charset", encoding);
      con.setUseCaches(false);
      
      if(!"GET".equalsIgnoreCase(method)) {
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + encoding);
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
      }
      
      return JSONFromInputStream(con.getInputStream());
    } catch(MalformedURLException e) {
      e.printStackTrace();
    } catch(IOException e) {
      e.printStackTrace();
      
      if(con != null) {
        return JSONFromInputStream(con.getErrorStream());
      }
    }
    
    return null;
  }
  
  public static Parameter param(String key, String val) {
    return new Parameter(key, val);
  }
  
  private static JSONObject JSONFromInputStream(InputStream is) {
    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
    String line;
    StringBuffer resp = new StringBuffer();
    
    try {
      while((line = rd.readLine()) != null) {
        resp.append(line);
        resp.append('\r');
      }
    } catch(IOException e) {
      e.printStackTrace();
    }
    
    try {
      rd.close();
    } catch(IOException e) { }
    
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