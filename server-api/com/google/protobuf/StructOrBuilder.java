package com.google.protobuf;

import java.util.Map;

public interface StructOrBuilder extends MessageOrBuilder {
  int getFieldsCount();
  
  boolean containsFields(String paramString);
  
  @Deprecated
  Map<String, Value> getFields();
  
  Map<String, Value> getFieldsMap();
  
  Value getFieldsOrDefault(String paramString, Value paramValue);
  
  Value getFieldsOrThrow(String paramString);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\StructOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */