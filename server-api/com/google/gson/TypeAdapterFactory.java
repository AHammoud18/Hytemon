package com.google.gson;

import com.google.gson.reflect.TypeToken;

public interface TypeAdapterFactory {
  <T> TypeAdapter<T> create(Gson paramGson, TypeToken<T> paramTypeToken);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\TypeAdapterFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */