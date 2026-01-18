package com.google.protobuf;

import java.util.Collection;
import java.util.List;

public interface LazyStringList extends ProtocolStringList {
  ByteString getByteString(int paramInt);
  
  Object getRaw(int paramInt);
  
  byte[] getByteArray(int paramInt);
  
  void add(ByteString paramByteString);
  
  void add(byte[] paramArrayOfbyte);
  
  void set(int paramInt, ByteString paramByteString);
  
  void set(int paramInt, byte[] paramArrayOfbyte);
  
  boolean addAllByteString(Collection<? extends ByteString> paramCollection);
  
  boolean addAllByteArray(Collection<byte[]> paramCollection);
  
  List<?> getUnderlyingElements();
  
  void mergeFrom(LazyStringList paramLazyStringList);
  
  List<byte[]> asByteArrayList();
  
  LazyStringList getUnmodifiableView();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\LazyStringList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */