package dungeonsync.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class API {
  private static API _instance = new API();
  public static API instance() { return _instance; }
  
  private final String baseURL = "http://dungeonsync.monoxidedesign.com/api/";
  
  private API() {
    CookieHandler.setDefault(new CookieManager());
  }
  
  public HashMap<String, String> lang(String type) {
    JSONObject json = request("lang/" + type)._json;
    HashMap<String, String> lang = new HashMap<>();
    
    for(Object k : json.keySet()) {
      String key = (String)k;
      lang.put(key, json.getString(key));
    }
    
    return lang;
  }
  
  public Response register(String email, String password, String confirmation) {
    return request("auth/register", "PUT", param("email", email), param("password", password), param("password_confirmation", confirmation));
  }
  
  public Response login(String email, String password) {
    return request("auth/login", "PUT", param("email", email), param("password", password));
  }
  
  public Response chars() {
    return request("characters");
  }
  
  public Response request(String url, Parameter... param) {
    return request(url, "GET", param);
  }
  
  public Response request(String url, String method, Parameter... param) {
    return request(url, method, "UTF-8", param);
  }
  
  public Response request(String url, String method, String encoding, Parameter... param) {
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
  
  public Parameter param(String key, String val) {
    return new Parameter(key, val);
  }
  
  private JSONObject JSONFromInputStream(InputStream is) {
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