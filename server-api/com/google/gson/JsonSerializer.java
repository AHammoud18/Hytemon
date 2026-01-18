package com.google.gson;

import java.lang.reflect.Type;

public interface JsonSerializer<T> {
  JsonElement serialize(T paramT, Type paramType, JsonSerializationContext paramJsonSerializationContext);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\JsonSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */