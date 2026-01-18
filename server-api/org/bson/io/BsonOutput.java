package org.bson.io;

import java.io.Closeable;
import org.bson.types.ObjectId;

public interface BsonOutput extends Closeable {
  int getPosition();
  
  int getSize();
  
  void truncateToPosition(int paramInt);
  
  void writeBytes(byte[] paramArrayOfbyte);
  
  void writeBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
  
  void writeByte(int paramInt);
  
  void writeCString(String paramString);
  
  void writeString(String paramString);
  
  void writeDouble(double paramDouble);
  
  void writeInt32(int paramInt);
  
  void writeInt32(int paramInt1, int paramInt2);
  
  void writeInt64(long paramLong);
  
  void writeObjectId(ObjectId paramObjectId);
  
  void close();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\io\BsonOutput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */