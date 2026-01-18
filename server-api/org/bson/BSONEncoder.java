package org.bson;

import org.bson.io.OutputBuffer;

public interface BSONEncoder {
  byte[] encode(BSONObject paramBSONObject);
  
  int putObject(BSONObject paramBSONObject);
  
  void done();
  
  void set(OutputBuffer paramOutputBuffer);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BSONEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */