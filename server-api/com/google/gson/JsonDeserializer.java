package com.google.gson;

import java.lang.reflect.Type;

public interface JsonDeserializer<T> {
  T deserialize(JsonElement paramJsonElement, Type paramType, JsonDeserializationContext paramJsonDeserializationContext) throws JsonParseException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\JsonDeserializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */