package com.google.protobuf;

public interface ValueOrBuilder extends MessageOrBuilder {
  boolean hasNullValue();
  
  int getNullValueValue();
  
  NullValue getNullValue();
  
  boolean hasNumberValue();
  
  double getNumberValue();
  
  boolean hasStringValue();
  
  String getStringValue();
  
  ByteString getStringValueBytes();
  
  boolean hasBoolValue();
  
  boolean getBoolValue();
  
  boolean hasStructValue();
  
  Struct getStructValue();
  
  StructOrBuilder getStructValueOrBuilder();
  
  boolean hasListValue();
  
  ListValue getListValue();
  
  ListValueOrBuilder getListValueOrBuilder();
  
  Value.KindCase getKindCase();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\ValueOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */