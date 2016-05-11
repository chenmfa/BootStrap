package toolmanage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ListExtraction {
    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception{
        System.out.println("1");
        BufferedReader br =
                new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\chenmf13021\\Desktop\\userExtract.txt")));
        BufferedWriter bw =
                new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Users\\chenmf13021\\Desktop\\userExtractFinal.txt")));
        byte[] by =new byte[1024];
        int flag=0;
        String str="";
        String str2="";
        while((str2=br.readLine()) != null){
            str =str+str2+"\t";
         
            if((flag+1)%4 ==0){
                str +="\r\n";
                bw.write(str);
                bw.flush();
                str="";
            }
            flag++;
        }
//连接两个日期之间的换行符        
//        while((str2=br.readLine()) != null){
//            str =str+str2;
//            if((flag+3)%4 ==0 && flag>1){
//                str =str + " " + br.readLine();
//            }
//            str +="\r\n";
//            bw.write(str);
//            bw.flush();
//            str="";  
//            flag++;
//        }
        br.close();
        bw.write(str);
        bw.flush();
        bw.close();
    }
}
