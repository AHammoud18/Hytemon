package com.google.gson;

import java.lang.reflect.Type;

public interface JsonSerializationContext {
  JsonElement serialize(Object paramObject);
  
  JsonElement serialize(Object paramObject, Type paramType);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\JsonSerializationContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */