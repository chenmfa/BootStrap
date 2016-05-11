package bean;

public final class ConnectInfo {
  private String host;
  private String username;
  private String password;
  private int port;
  
  public ConnectInfo(){
    this.port=22;
  }
  public ConnectInfo(String host, String username, String password, int port){
    this.host=  host;
    this.username=  username;
    this.password=  password;
    this.port=  port;
  }
  public String getHost() {
    return host;
  }
  public void setHost(String host) {
    this.host = host;
  }
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public int getPort() {
    return port;
  }
  public void setPort(int port) {
    this.port = port;
  }
  public String toString(){
    return "host: "+host+":\tuser: "+username+"\tport"+port;
  }
}
