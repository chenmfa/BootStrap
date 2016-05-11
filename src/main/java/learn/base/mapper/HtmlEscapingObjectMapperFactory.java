package learn.base.mapper;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.FactoryBean;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *@use 自定义，非项目相关
 *@description 这个类原来用来测试annotation 里面过滤xss的，
 *但是fasterxml的jacksonMapper不能转换成codehaus的jscksonMapper下次研究
 */
public class HtmlEscapingObjectMapperFactory implements FactoryBean<Object>{
  
  private final ObjectMapper objectMapper;
  
  public HtmlEscapingObjectMapperFactory(){
    objectMapper = new ObjectMapper();
    objectMapper.getFactory().setCharacterEscapes(new HtmlCharacterEscapes());
  }
  @Override
  public Object getObject() throws Exception {
    return objectMapper;
  }

  @Override
  public Class<?> getObjectType() {
    return ObjectMapper.class;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }
  
  public static class HtmlCharacterEscapes extends CharacterEscapes{

    private static final long serialVersionUID = 1L;
    
    private final int[] asciiEscapes;
    
    public HtmlCharacterEscapes(){
      asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON(); 
      asciiEscapes['<'] = asciiEscapes['>'] 
          = asciiEscapes['&'] = asciiEscapes['"'] 
          = asciiEscapes['\''] = CharacterEscapes.ESCAPE_CUSTOM;
    }
    @Override
    public int[] getEscapeCodesForAscii() {      
      return asciiEscapes;
    }

    @Override
    public SerializableString getEscapeSequence(int ch) {
      return new SerializedString(StringEscapeUtils.escapeHtml(Character.toString((char)ch)));
    }
    
  }

}
