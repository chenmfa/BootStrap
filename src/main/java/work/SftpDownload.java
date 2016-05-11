package work;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bean.ConnectInfo;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.SftpProgressMonitor;


/**
 *@description sshftp  网上还有ganymed-ssh2这个的解决方案 
 *1，完全覆盖模式OVERWRITE
 *2，恢复模式，（类似于断点上传）因外部原因导致文件上传中断，下次上传时会接着上次的继续上传RESUME
 *3，追加模式，若发现文件已经存在，则在目标文化后追加。APPEND
 *ChannelSftp类是JSch实现sftp核心类，实现了很多方法。
 *put()： 文件上传
 *get()： 文件下载
 *cd()：    进入指定目录
 *ls()：    得到指定目录下的文件列表
 *rename(): 重命名指定文件或目录
 *rm()：     删除指定文件
 *mkdir()： 创建目录
 *rmdir()： 删除目录
 */
public class SftpDownload {
  
  private Session session;
  
  public String exec(String command) throws JSchException{
    StringBuffer sb= new StringBuffer();
    String result="";    
    ChannelExec openChannel =null;
    openChannel =(ChannelExec)openChannel("exec");
    //也可以用 session.openchannel("shell");  然后用openChannel.getOutputStream()的输出流输出
    openChannel.setCommand(command);
    openChannel.connect();
    BufferedWriter bw=null;
    BufferedReader br=null;
    try{
      InputStream in= openChannel.getInputStream();
      br= new BufferedReader(new InputStreamReader(in));
      int exitStatus= openChannel.getExitStatus();
      System.out.println(exitStatus);
      //BufferedWriter bw= new BufferedWriter(new FileWriter(new File("test.txt"),true));
      if(exitStatus !=-1){
        System.out.println("执行命令出错");
      }else{
        File f=new File("src"+File.separator+"source"+File.separator+"log"+File.separator+"normal"+File.separator+"receive.log");
        mkDirs(f,false);
        bw= new BufferedWriter(new FileWriter(f,true));
        String buffer=null;
        int count=0;
        while(null != (buffer =br.readLine())){
          count++;
          sb.append(new String(buffer.getBytes("gbk"),"UTF-8"));
          sb.append("\r\n");        
        }
        String fileTransferred =count+" files has been packed.";
        sb.insert(0,fileTransferred);
        result=sb.toString();
        bw.write(result);
        System.out.println(fileTransferred);
      }
    }catch(IOException e){
      System.out.println(e.getMessage());
    }finally{
      try {
        closeStream(null, null, br, bw);
      } catch (IOException e) {        
        System.out.println("关闭连接失败."+e.getMessage());
      }
    }
    return result;
  }
  
  public boolean getSftpFile(String localPath, String remotePath) throws JSchException, SftpException{
    //session.connect();
    ChannelSftp channel= null;
    OutputStream out=null;
    try {
      channel =(ChannelSftp)openChannel("sftp");
      channel.connect();
      getRemoteFile(channel, out, localPath, remotePath);
    }catch (FileNotFoundException e){
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }finally{
      try {
        closeStream(null, out,null,null);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    
    return true;
  }
  
  public Session openConnection(ConnectInfo hostInfo) throws JSchException{
    JSch jsch = new JSch();
    session = jsch.getSession(hostInfo.getUsername(), hostInfo.getHost(), hostInfo.getPort());
    session.setPassword(hostInfo.getPassword());
    Properties config =new Properties();
    config.put("StrictHostKeyChecking", "no");
    session.setConfig(config);
    session.connect();//session.connect(5000);
    System.out.println("Connect Success");
    return session;
  }
  
  public Channel openChannel(String channelType) throws JSchException{
    Channel channel= session.openChannel(channelType);    
    return channel;
  }
  
  public boolean closeConnection(Channel channel){
    if(null !=channel && !channel.isClosed()){
      channel.disconnect();
    }
    if(null !=session && session.isConnected()){
      session.disconnect();
    }
    return true;
  }
  
  public boolean closeStream(InputStream input,OutputStream out,Reader reader,Writer writer)
      throws IOException{
    if(null !=out){
      out.flush();
      out.close();
    }
    if(null != input){
      input.close();
    }
    if(null !=reader){
      reader.close();
    }
    if(null != writer){
      writer.flush();
      writer.close();
    }
    return true;
  }
  
  public boolean mkDirs(File f,boolean isdir) throws IOException{
    if(!f.exists()){
      if(!isdir){        
        f.getParentFile().mkdirs();
        f.createNewFile();
      }else{
        f.mkdirs();
      }
    }
    return true;
  }
  
  public boolean getRemoteFile(ChannelSftp channel,OutputStream out, String localPath,String remotePath) throws SftpException, IOException{
    //LsEntry next = (LsEntry)channel.;
    SftpATTRS lstat = channel.lstat(remotePath);     
    //int index =remotePath.lastIndexOf('/');
    boolean isdir= lstat.isDir(); 
    Vector<?> files =channel.ls(remotePath);
    Matcher mathcer= Pattern.compile("(\\/{1}[^\\/]+)+\\/?").matcher(remotePath);
    //boolean matches = mathcer.matches();
    if(!mathcer.find()){
      return false;
    }
    String fileName=mathcer.group(1).replace("/", "");
    String localFilePath=localPath.endsWith("/")?localPath+fileName:localPath+File.separator+fileName;
    File f= new File(localFilePath);
    
    mkDirs(f,isdir);
    if(!isdir){
      out = new BufferedOutputStream(new FileOutputStream(f));
      channel.get(remotePath, out, new FileProgressMonitor()
        /*new SftpProgressMonitor() {
      private int total;
      public void init(int arg0, String srcPath, String dest, long fileSize) {
        System.out.println(arg0+"\r\n"+srcPath+"\r\n"+dest+"\r\n"+fileSize+"\r\n");
      }       
      public void end() {
        System.out.println("Transfer Finished,"+total+"bytes received.");
      }
      public boolean count(long count) {
        total +=count;
        //System.out.println("接收数据块"+count);
        return true;
      }
    }*/);
    }else{      
      if(files.isEmpty()){
        return true;
      }
      Iterator<?> it =files.iterator();
      while(it.hasNext()){
        LsEntry next = (LsEntry)it.next();
        //SftpATTRS attrs = next.getAttrs();        
        String entryFile=next.getFilename();
        if(!".".equals(entryFile) && !"..".equals(entryFile)){
          System.out.println(next.getFilename()+"************"+next.getLongname()+"|\r\n"+localFilePath+File.separator+entryFile+"|\r\n"+remotePath+File.separator+entryFile);        
          getRemoteFile(channel, out, localFilePath, remotePath+"/"+entryFile);
        }
      }
      /*Enumeration<?> elements = files.elements();
      while(elements.hasMoreElements()){
        String nextElement = elements.nextElement().toString();
        getRemoteFile(channel, out, remotePath+"\\"+nextElement, localPath);
      }*/
    }
    return true;
  }
  
  public static void main(String[] args){
    SftpDownload ssd =new SftpDownload();
    ConnectInfo hostInfo= new ConnectInfo("10.253.22.252","root","ComplianceTest123",22);
    String projectPath=System.getProperty("user.dir");
    String downLoadPath=projectPath+File.separator+"src\\source\\download";
    String command ="cd /home/pub/workspace; tar -zcvf /home/pub/workspace/log/logs.tar.gz --exclude=*tar.gz -C /home/pub/workspace/ log runlog.log*";
    try {
      ssd.openConnection(hostInfo);
      ssd.exec(command);
      ssd.getSftpFile(downLoadPath,"/home/pub/workspace/log/logs.tar.gz");//"/home/pub/workspace/log"
      Runtime.getRuntime().exec(new String[]{"cmd","/c","start"," ",downLoadPath});
    } catch (JSchException e) {
      e.printStackTrace();
    } catch (SftpException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.exit(0);
  }
  
  private class FileProgressMonitor extends TimerTask implements SftpProgressMonitor{
    
    private long total; //文件总大小
    
    private long transferred; //已传输文件大小
    
    private long defaultInterval = 1000; //默认的计时器时间
    
    private boolean isFinished= false;
    
    private String fileName ="File";
    
    private Timer timer; //计时器对象
    
    private boolean isStarted= false;
    @Override
    public boolean count(long count) {
      transferred +=count;
      return true;
    }

    @Override
    public void end() {
      isFinished= true;
      System.out.println(fileName+" Transfer Finished,"+String.format("%.2f",transferred/1024.0)+"kb received.");
      stop();
    }

    @Override
    public void init(int arg0, String srcPath, String dest, long fileSize) {
      this.total=fileSize;
      int index= srcPath.lastIndexOf("/");
      this.fileName = (index != -1)?srcPath.substring(index+1):srcPath;
      System.out.println("文件数目："+arg0+"\t文件路径："+srcPath+"\t目标文件路径："+dest+"\t文件大小："+fileSize+"\r\n");
      start();
    }
    
    /**
     * @source timertask 
     */
    @Override
    public void run() {      
      if(!isFinished){
        System.out.print("File Transferring......\t");
        if(transferred <= total){
          if(transferred !=0){
            System.out.println(fileName + " has transferred "+String.format("%.2f", transferred/1024.0)+"kb-------"+String.format("%d%%", (transferred*100/total)));
          }else{
            System.out.println("Getting File....");
          }
        }
      }
    }
    
    
    public void  start(){
      if(!isStarted){
        timer =new Timer();
        timer.schedule(this, 1000, defaultInterval);
        isStarted = true;
        System.out.println("Timer started");
      }      
    }
    public void stop(){
      if(null !=timer){
        timer.cancel();
        timer.purge();
        timer= null;
      }
    }
  } 
}
