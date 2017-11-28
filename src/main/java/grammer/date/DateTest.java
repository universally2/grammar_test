package grammer.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by linfengchen on 11/28/17.
 */
public class DateTest {

  public static void main(String[] args) {
    DateTest test = new DateTest();
    /*
    int serviceday = 60;
    String startat = "20170928124522";
    String endat = "20171127124522";
    */
    int serviceday = 35;
    String startat = "20171023023033";
    String endat = "20171127023033";
    String ans = test.evaluate(serviceday,startat,endat);
    System.out.println(ans);
  }

  public String evaluate(int serviceday, String startat, String endat) {
    if (serviceday <= 30) {
      StringBuilder ext = new StringBuilder();
      ext.append("service_day=").append(serviceday);
      ext.append(";start_at=").append(startat);
      ext.append(";end_at=").append(endat);
      return ext.toString();
    }
    StringBuilder servicedays = new StringBuilder();
    servicedays.append("service_day=").append("30");
    servicedays.append(";start_at=").append(startat);
    SimpleDateFormat formater = new SimpleDateFormat( "yyyyMMddHHmmss");
    Date dateStart;
    try {
      dateStart = formater.parse(startat);
    } catch (ParseException e) {
      return "";
    }
    int endFromStart = 30;
    String newEndAt = formater.format(getNewDate(dateStart, endFromStart));
    servicedays.append(";end_at=").append(newEndAt);

    int days = serviceday - 30;
    while (days > 0) {
      if (days >= 30) {
        endFromStart += 30;
        servicedays.append(",");
        servicedays.append("service_day=").append("30");
        servicedays.append(";start_at=").append(newEndAt);
        newEndAt = formater.format(getNewDate(dateStart, endFromStart));
        servicedays.append(";end_at=").append(newEndAt);
        days -= 30;
      } else {
        endFromStart += days;
        servicedays.append(",");
        servicedays.append("service_day=").append(String.valueOf(days));
        servicedays.append(";start_at=").append(newEndAt);
        newEndAt = formater.format(getNewDate(dateStart, endFromStart));
        servicedays.append(";end_at=").append(newEndAt);
        break;
      }
    }
    return servicedays.toString();
  }

  public static Date getNewDate(Date d, int day) {
    Calendar now = Calendar.getInstance();
    now.setTime(d);
    now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
    return now.getTime();
  }
}
