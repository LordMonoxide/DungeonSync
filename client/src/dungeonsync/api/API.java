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
    JSONObject json = request("lang/" + type)._json;
    HashMap<String, String> lang = new HashMap<>();
    
    for(Object k : json.keySet()) {
      String key = (String)k;
      lang.put(key, json.getString(key));
    }
    
    return lang;
  }
  
  public static Response register(String email, String password, String confirmation) {
    return request("auth/register", "PUT", param("email", email), param("password", password), param("password_confirmation", confirmation));
  }
  
  public static Response login(String email, String password) {
    return request("auth/login", "PUT", param("email", email), param("password", password));
  }
  
  public static Response request(String url, Parameter... param) {
    return request(url, "GET", param);
  }
  
  public static Response request(String url, String method, Parameter... param) {
    return request(url, method, "UTF-8", param);
  }
  
  public static Response request(String url, String method, String encoding, Parameter... param) {
    Response con = new Response();
    
    try {
      con._con = (HttpURLConnection)new URL(baseURL + url).openConnection();
      con._con.setRequestMethod(method);
      con._con.setRequestProperty("Accept-Charset", encoding);
      con._con.setUseCaches(false);
      
      if(!"GET".equalsIgnoreCase(method)) {
        con._con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + encoding);
        con._con.setDoInput(true);
        con._con.setDoOutput(true);
        
        String params = "";
        for(Parameter p : param) {
          if(params.length() != 0) { params += "&"; }
          params += p.key + "=" + URLEncoder.encode(p.val, encoding);
        }
        
        OutputStream out = con._con.getOutputStream();
        out.write(params.getBytes("UTF-8"));
        out.close();
      }
      
      con._json = JSONFromInputStream(con._con.getInputStream());
      return con;
    } catch(MalformedURLException e) {
      e.printStackTrace();
    } catch(IOException e) {
      e.printStackTrace();
      
      if(con != null) {
        con._json = JSONFromInputStream(con._con.getErrorStream());
        return con;
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
    
    System.out.println(resp.toString());
    
    try {
      return new JSONObject(new JSONTokener(resp.toString()));
    } catch(JSONException e) {
      e.printStackTrace();
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
  
  public static class Response {
    private HttpURLConnection _con;
    private JSONObject _json;
    public HttpURLConnection con() { return _con; }
    public JSONObject json() { return _json; }
    
    public boolean success() {
      try {
        return _con.getResponseCode() >= 200 && _con.getResponseCode() < 300;
      } catch(IOException e) {
        e.printStackTrace();
      }
      
      return false;
    }
  }
}