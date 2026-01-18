package org.bson;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public interface ByteBuf {
  int capacity();
  
  ByteBuf put(int paramInt, byte paramByte);
  
  int remaining();
  
  ByteBuf put(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
  
  boolean hasRemaining();
  
  ByteBuf put(byte paramByte);
  
  ByteBuf flip();
  
  byte[] array();
  
  int limit();
  
  ByteBuf position(int paramInt);
  
  ByteBuf clear();
  
  ByteBuf order(ByteOrder paramByteOrder);
  
  byte get();
  
  byte get(int paramInt);
  
  ByteBuf get(byte[] paramArrayOfbyte);
  
  ByteBuf get(int paramInt, byte[] paramArrayOfbyte);
  
  ByteBuf get(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
  
  ByteBuf get(int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3);
  
  long getLong();
  
  long getLong(int paramInt);
  
  double getDouble();
  
  double getDouble(int paramInt);
  
  int getInt();
  
  int getInt(int paramInt);
  
  int position();
  
  ByteBuf limit(int paramInt);
  
  ByteBuf asReadOnly();
  
  ByteBuf duplicate();
  
  ByteBuffer asNIO();
  
  int getReferenceCount();
  
  ByteBuf retain();
  
  void release();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\ByteBuf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */