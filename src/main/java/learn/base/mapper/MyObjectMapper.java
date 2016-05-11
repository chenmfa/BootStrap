package learn.base.mapper;

import java.io.IOException;

import org.springframework.web.util.HtmlUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class MyObjectMapper extends ObjectMapper{

  private static final long serialVersionUID = 1L;

  public MyObjectMapper(){
    SimpleModule module = new SimpleModule("HTML XSS Serializer",
        new Version(1, 0, 0, "FINAL","com.yihaomen","ep-jsonmodule"));
    module.addSerializer(new JsonHtmlXssSerializer(String.class));
    this.registerModule(module);
  }
  
  class JsonHtmlXssSerializer extends JsonSerializer<String>{
    public JsonHtmlXssSerializer(Class<String> string) {
      super();
    }
    public Class<String> handledType() {
        return String.class;
    }
    public void serialize(String value, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException,
            JsonProcessingException {
        if (value != null) {
            String encodedValue = HtmlUtils.htmlEscape(value.toString());
            jsonGenerator.writeString(encodedValue);
        }
    }
  }
}
