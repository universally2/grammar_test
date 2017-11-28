package grammer.file;

import java.io.*;

/**
 * Created by linfengchen on 11/23/17.
 */
public class FileReaderTest {

  private static FileReaderTest theFactory = null;

  public static synchronized FileReaderTest getFactory() {
    if (theFactory == null) {
      theFactory = new FileReaderTest();
    }
    return theFactory;
  }

  //这个一直没成功,不知道为啥
  public static String ReadFile(String path) {
    System.out.println(path);
    File file = new File(path);
    BufferedReader reader = null;
    String laststr = "";
    try {
      FileReader fr = new FileReader(file);
      reader = new BufferedReader(fr);
      String tempString = null;
      while ((tempString = reader.readLine()) != null) {
        laststr += tempString;
      }
      reader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return laststr;
  }

  public static String ReadFileAsStream(String path) {
    System.out.println(path);
    InputStream in = null;
    in = FileReaderTest.class.getResourceAsStream(path);
    String laststr = "";
    StringBuffer sb = new StringBuffer();
    try {
      Reader r = new InputStreamReader(in, "utf-8");
      int length = 0;
      for(char c[] = new char[1024];(length = r.read(c)) != -1;) {
        sb.append(c, 0 ,length);
      }
      r.close();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return sb.toString();
  }
}
