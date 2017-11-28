package grammer.json;

import grammer.file.FileReaderTest;
import org.json.JSONObject;

/**
 * Created by linfengchen on 11/23/17.
 */
public class JsonTest {

  public static void main(String[] args) {
    //下面使用情况不同,都能用,但是需要各自设置path变量
    //String content = FileReaderTest.ReadFile("/home/linfengchen/workspace/exe/grammer_test/grammar_test-1.0-SNAPSHOT/input/member.json");
    String content = FileReaderTest.ReadFile("./input/member.json");
    //String content = FileReaderTest.ReadFileAsStream("./input/member.json");
    System.out.println(content);
    try {
      JSONObject jsonObject = new JSONObject(content);
      System.out.println(jsonObject.toString());
      System.out.println(jsonObject.keys().toString());
      System.out.println(jsonObject.length());
      System.out.println(jsonObject.getJSONArray("groups"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
