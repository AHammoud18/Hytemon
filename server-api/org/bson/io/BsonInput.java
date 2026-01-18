package org.bson.io;

import java.io.Closeable;
import org.bson.types.ObjectId;

public interface BsonInput extends Closeable {
  int getPosition();
  
  byte readByte();
  
  void readBytes(byte[] paramArrayOfbyte);
  
  void readBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
  
  long readInt64();
  
  double readDouble();
  
  int readInt32();
  
  String readString();
  
  ObjectId readObjectId();
  
  String readCString();
  
  void skipCString();
  
  void skip(int paramInt);
  
  BsonInputMark getMark(int paramInt);
  
  boolean hasRemaining();
  
  void close();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\io\BsonInput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */