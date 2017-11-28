package grammer.url;

/**
 * Created by linfengchen on 10/26/17.
 */

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

class DruidQueryClient {

  private String brokerHostAndPort;

  public DruidQueryClient(String brokerHostAndPort) {
    this.brokerHostAndPort = brokerHostAndPort;
  }

  public String query(String query) {
    String brokerUrl = "http://" + brokerHostAndPort + "/druid/v2/?pretty";
    String sr = HttpRequest.sendPost(brokerUrl, query);
    return sr;
  }

}

class HttpRequest {
  /**
   * 向指定URL发送GET方法的请求
   *
   * @param url
   *            发送请求的URL
   * @param param
   *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
   * @return URL 所代表远程资源的响应结果
   */
  public static String sendGet(String url, String param) {
    String result = "";
    BufferedReader in = null;
    try {
      String urlNameString = url + "?" + param;
      URL realUrl = new URL(urlNameString);
      // 打开和URL之间的连接
      URLConnection connection = realUrl.openConnection();
      // 设置通用的请求属性
      connection.setRequestProperty("accept", "*/*");
      connection.setRequestProperty("connection", "Keep-Alive");
      connection.setRequestProperty("user-agent",
          "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
      connection.setConnectTimeout(5000);
      connection.setReadTimeout(5000);
      // 建立实际的连接
      connection.connect();
      // 获取所有响应头字段
      Map<String, List<String>> map = connection.getHeaderFields();
      // 遍历所有的响应头字段
      for (String key : map.keySet()) {
        System.out.println(key + "--->" + map.get(key));
      }
      // 定义 BufferedReader输入流来读取URL的响应
      in = new BufferedReader(new InputStreamReader(
          connection.getInputStream()));
      String line;
      while ((line = in.readLine()) != null) {
        result += line;
      }
    } catch (Exception e) {
      System.out.println("发送GET请求出现异常！" + e);
      e.printStackTrace();
    }
    // 使用finally块来关闭输入流
    finally {
      try {
        if (in != null) {
          in.close();
        }
      } catch (Exception e2) {
        e2.printStackTrace();
      }
    }
    return result;
  }

  /**
   * 向指定 URL 发送POST方法的请求
   *
   * @param url
   *            发送请求的 URL
   * @param param
   *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
   * @return 所代表远程资源的响应结果
   */
  public static String sendPost(String url, String param) {
    PrintWriter out = null;
    BufferedReader in = null;
    String result = "";
    try {
      URL realUrl = new URL(url);
      // 打开和URL之间的连接
      URLConnection conn = realUrl.openConnection();
      // 设置通用的请求属性
      conn.setRequestProperty("accept", "*/*");
      conn.setRequestProperty("connection", "Keep-Alive");
      conn.setRequestProperty("user-agent",
          "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
      conn.setRequestProperty("Content-Type", "application/json");
      // 发送POST请求必须设置如下两行
      conn.setDoOutput(true);
      conn.setDoInput(true);
      // 获取URLConnection对象对应的输出流
      out = new PrintWriter(conn.getOutputStream());
      // 发送请求参数
      out.print(param);
      // flush输出流的缓冲
      out.flush();
      // 定义BufferedReader输入流来读取URL的响应
      in = new BufferedReader(
          new InputStreamReader(conn.getInputStream()));
      String line;
      while ((line = in.readLine()) != null) {
        result += line;
      }
    } catch (Exception e) {
      System.out.println("发送 POST 请求出现异常！"+e);
      e.printStackTrace();
    }
    //使用finally块来关闭输出流、输入流
    finally{
      try{
        if(out!=null){
          out.close();
        }
        if(in!=null){
          in.close();
        }
      }
      catch(IOException ex){
        ex.printStackTrace();
      }
    }
    return result;
  }
}

public class HttpRequestDruid {

  public static void main(String[] args) {

    //发送 GET 请求
    //String s = HttpRequest.sendGet("http://localhost:9000/systeminfo", "");
    //System.out.println(s);

    //String brokerUrl = "http://10.10.65.63:8082/druid/v2/?pretty";
    String params = "{\n" + "   \"queryType\" : \"timeseries\",\n" + "   \"dataSource\" : \"kafka_topic\",\n"
        + "   \"granularity\" : \"hour\",\n" + "   \"descending\" : \"false\",\n" + "   \"aggregations\" : [\n"
        + "      { \"type\" : \"longSum\", \"name\" : \"messagesInPerSec\", \"fieldName\" : \"messagesInPerSec\" },\n"
        + "      { \"type\" : \"longSum\", \"name\" : \"bytesInPerSec\", \"fieldName\" : \"bytesInPerSec\" },\n"
        + "      { \"type\" : \"longSum\", \"name\" : \"bytesOutPerSec\", \"fieldName\" : \"bytesOutPerSec\" }\n"
        + "   ],\n" + "   \"intervals\" : [ \"2017-10-25T00:00:00.000+08:00/2017-10-27T00:00:00.000+08:00\" ]\n" + "}\n";
    System.out.println(params);
    //发送 POST 请求
    //String sr = HttpRequest.sendPost(brokerUrl, param);
    //System.out.println(sr);

    /*
    DruidQueryClient client = new DruidQueryClient("10.10.65.63:8082");
    String ans = client.query(param);
    System.out.println(ans);
    try {
      JSONObject jsonObject = new JSONObject("{ans:" + ans + "}");
      //JSONObject jsonObject = new JSONObject(ans.substring(1, ans.length() - 1));
      System.out.println(jsonObject.toString());
    } catch (JSONException e) {
      e.printStackTrace();
    }
    */
    String desc = "linfengtest vrs exception".replaceAll(" ", "%20");
    String param = "appId=1001&exceptionAppId=1011&status=404&desc=" + desc + "&isAlert=1&mails=linfengchen207486;jingsun204401&weixin=13516122832;18611642877";
    System.out.println(desc);
    String str = HttpRequest.sendGet("http://ms.sohuno.com/interface/exception/report.do", param);
    System.out.println(str);

  }
}
