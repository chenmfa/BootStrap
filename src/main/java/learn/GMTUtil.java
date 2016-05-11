package learn;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class GMTUtil {
  
  public static void main(String[] args) {
    GMTUtil.getGMTTime(new Date().getTime());
  }
  
  public static String getGMTTime(long time){
    System.out.println(time);
    SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'",Locale.US);
    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    String date = sdf.format(time);
    System.out.println(date);
    return date;
  }
}
