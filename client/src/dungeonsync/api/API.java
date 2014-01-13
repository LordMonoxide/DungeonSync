package dungeonsync.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class API {
  private static API _instance = new API();
  public static API instance() { return _instance; }
  
  private final String baseURL = "http://dungeonsync.monoxidedesign.com/api/";
  
  private API() {
    CookieHandler.setDefault(new CookieManager());
  }
  
  public HashMap<String, String> lang(String type) throws IOException {
    JSONObject json = request("lang/" + type).parseObject();
    HashMap<String, String> lang = new HashMap<>();
    
    for(Object k : json.keySet()) {
      String key = (String)k;
      lang.put(key, json.getString(key));
    }
    
    return lang;
  }
  
  public Response register(String email, String password, String confirmation) throws IOException {
    return request("auth/register", "PUT", param("email", email), param("password", password), param("password_confirmation", confirmation));
  }
  
  public Response login(String email, String password) throws IOException {
    return request("auth/login", "PUT", param("email", email), param("password", password));
  }
  
  public Response chars() throws IOException {
    return request("characters");
  }
  
  public Response request(String url, Parameter... param) throws IOException {
    return request(url, "GET", param);
  }
  
  public Response request(String url, String method, Parameter... param) throws IOException {
    return request(url, method, "UTF-8", param);
  }
  
  public Response request(String url, String method, String encoding, Parameter... param) throws IOException {
    HttpURLConnection con;
    
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
    
    return new Response(con);
  }
  
  public Parameter param(String key, String val) {
    return new Parameter(key, val);
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
    private String _resp;
    
    private Response(HttpURLConnection con) throws IOException {
      _con = con;
      
      try {
        _resp = read(_con.getInputStream());
      } catch(IOException e) {
        e.printStackTrace();
        _resp = read(_con.getErrorStream());
      }
    }
    
    public int     status () throws IOException { return _con.getResponseCode(); }
    public boolean success() throws IOException { return status() >= 200 && status() < 300; }
    public JSONObject parseObject() { return new JSONObject(_resp); }
    public JSONArray  parseArray () { return new JSONArray (_resp); }
    
    
    private String read(InputStream is) throws IOException {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
      StringBuffer resp = new StringBuffer();
      String line;
      
      while((line = rd.readLine()) != null) {
        resp.append(line);
        resp.append('\r');
      }
      
      rd.close();
      
      System.out.println(resp.toString());
      
      return resp.toString();
    }
  }
}