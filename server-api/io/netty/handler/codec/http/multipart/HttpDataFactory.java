package io.netty.handler.codec.http.multipart;

import io.netty.handler.codec.http.HttpRequest;
import java.nio.charset.Charset;

public interface HttpDataFactory {
  void setMaxLimit(long paramLong);
  
  Attribute createAttribute(HttpRequest paramHttpRequest, String paramString);
  
  Attribute createAttribute(HttpRequest paramHttpRequest, String paramString, long paramLong);
  
  Attribute createAttribute(HttpRequest paramHttpRequest, String paramString1, String paramString2);
  
  FileUpload createFileUpload(HttpRequest paramHttpRequest, String paramString1, String paramString2, String paramString3, String paramString4, Charset paramCharset, long paramLong);
  
  void removeHttpDataFromClean(HttpRequest paramHttpRequest, InterfaceHttpData paramInterfaceHttpData);
  
  void cleanRequestHttpData(HttpRequest paramHttpRequest);
  
  void cleanAllHttpData();
  
  @Deprecated
  void cleanRequestHttpDatas(HttpRequest paramHttpRequest);
  
  @Deprecated
  void cleanAllHttpDatas();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\multipart\HttpDataFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */