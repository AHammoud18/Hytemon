package org.bson;

import java.io.IOException;
import java.io.InputStream;

public interface BSONDecoder {
  BSONObject readObject(byte[] paramArrayOfbyte);
  
  BSONObject readObject(InputStream paramInputStream) throws IOException;
  
  int decode(byte[] paramArrayOfbyte, BSONCallback paramBSONCallback);
  
  int decode(InputStream paramInputStream, BSONCallback paramBSONCallback) throws IOException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BSONDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */