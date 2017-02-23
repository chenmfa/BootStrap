package learn.excel;

public class Test {
  public static void main(String[] args) {
    int i=54;
    boolean gotta = false;
    while(true){
      if(i%9==0){
        if(i%8==1){
          if(i%7==5){
            if(i%6==3){
              if(i%5==4){
                if(i%3==0){
                  if(i%2==1){
                    System.out.println(i);
                    gotta= true;
                    break;
                  }
                }
              }
            }
          }
        }
      }
      if(!gotta){
        i++;
      }
    }

  }
}
