package learn.service;

public interface Base64_2{
  
  /**
   * 根据传进来的字符字节码，查询base64码表的索引，并返回查找到的索引
   * @param  b
   * @return byte
   */
  public abstract byte baseIndex(byte b);
  
  /**
   * 解码，入参是编码后的base64字符的字节码
   * 解析时以4个一组进行解析
   * @param  b 编码后的字符字节码数组
   * @return String
   * @throws
   */
  public abstract String backEncode(byte[] b);
  
  /**
   * 解码， 将四个字节码中第一个的后6位（00XXXXXX）
   * 和第二个字节的前4位的后两位(00yy0000)
   * 还原成原来的字节码xxxxxxyy
   * @param first(四个字节码中的第一个)
   * @param second(四个字节码中的第二个)
   * @return byte
   * @throws
   */
  public abstract byte backFirst(byte first, byte second);
  
  /**
   * 解码 将4个字节的第2个(00yyaaaa)和第3个
   * 字节的前六位的后四位(00bbbb00)还原为字节码aaaabbbb
   * @param second(第2/4字节码)
   * @param third(第3/4字节码)
   * @return byte
   * @throws
   */
  public abstract byte backSecond(byte second, byte third);
  
  /**
   * 解码 将4个字节中的第3个的后两位(000000cc)和
   * 第4个字节的后6位(00dddddd)还原成原来的字节码
   * ccdddddd
   * @param third(第3/4字节码)
   * @param fourth(第4个字节码)
   * @return byte
   * @throws
   */
  public abstract byte backThird(byte third, byte fourth);
  
  /**
   * 解码 将编码后的字符串数组的最后连个字节码还原为原来的字节码
   * 假如数组末尾剩下两个字节码：将倒数第二个字节的前后6位(00xxxxxx)
   * 和倒数第二个字节的后两位(000000yy)还原为原来的编码(xxxxyy)
   * 加入数组末尾就剩3个字节，将倒数第二个字节的前后四位(0000xxxx)
   * 和倒数第一个字节的后四位(0000yyyy)还原为原来的编码xxxxyyyy
   * @param last_b(倒数第二个字节)
   * @param next_b(倒数第一个字节)
   * @param move_1(倒数第二个的位移)
   * @param move_2(倒数第一个的位移)
   * @return byte
   * @throws
   */
  public byte backLastOne(byte last_b, byte next_b, int move_1, int move_2);
  
  /**
   * 编码,将传进来的字符编码为base64格式,返回一个base64字符串
   * 编码时3个字节一组进行编码，
   * @param  b 要进行编码的额字符串字节数组
   * @return String
   * @throws
   */
  public abstract String encode(byte[] b);
  
  /**
   * 如果字符长度%3!=0使用此方法编码莫尾字符
   * 若: b=xxxxyyyy, 加入末尾字节个数等于1
   * 分别将这个字节的前后6位都当做一个字节00xxxxyy和00xxyyyyy
   * @param b(莫尾字符的字节码) move 位移参数
   * @return byte
   * @throws
   */
  public abstract byte lastOneByte(byte b, int move);
  
  /**
   * 编码 b=xxxxyyyy 将第一个字节的前6位编码为base64, 
   * 将三个字节中的第一个字节码转为00xxxxyy
   * @param b 三个中的第一个字节
   * @return byte
   * @throws
   */
  public abstract byte firstByte(byte b);
  
  /**
   * 编码 last_b=xxxxyyyy, next_b=kkkkffff
   * 将第一个字节码的后两位和第二个字节码的前4位，编码为00yykkkk
   * @param  last_b 第一个字节
   * @param  next_b 第二个字节
   * @return byte
   * @throws
   */
  public abstract byte secondByte(byte last_b, byte next_b);
  
  
  /**
   * 编码 last_b=xxxxyyyy, next_b=kkkkffff,
   * 将第二个的后四位yyyy和第三个的
   * @param
   * @return byte
   * @throws
   */
  public abstract byte thirdByte(byte last_b, byte next_b);
  
  /**
   * 加入b=xxxxyyyy,将3个字节中的00XXyyyy转码为00xxyyyy
   * @param
   * @return byte
   * @throws
   */
  public abstract byte fourthByte(byte b);
}
