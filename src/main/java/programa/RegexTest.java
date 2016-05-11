package programa;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
  public static void main(String[] args) {
    Matcher ma= Pattern.compile("\\d").matcher("123dfsf34");
    
    while(ma.find()){
      System.out.print(ma.group()+"\t");
    }
  }
}
