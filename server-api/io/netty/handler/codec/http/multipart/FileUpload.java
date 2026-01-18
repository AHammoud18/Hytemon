package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.util.ReferenceCounted;

public interface FileUpload extends HttpData {
  String getFilename();
  
  void setFilename(String paramString);
  
  void setContentType(String paramString);
  
  String getContentType();
  
  void setContentTransferEncoding(String paramString);
  
  String getContentTransferEncoding();
  
  FileUpload copy();
  
  FileUpload duplicate();
  
  FileUpload retainedDuplicate();
  
  FileUpload replace(ByteBuf paramByteBuf);
  
  FileUpload retain();
  
  FileUpload retain(int paramInt);
  
  FileUpload touch();
  
  FileUpload touch(Object paramObject);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\multipart\FileUpload.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */