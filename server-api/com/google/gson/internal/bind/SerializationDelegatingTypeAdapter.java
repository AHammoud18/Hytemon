package com.google.gson.internal.bind;

import com.google.gson.TypeAdapter;

public abstract class SerializationDelegatingTypeAdapter<T> extends TypeAdapter<T> {
  public abstract TypeAdapter<T> getSerializationDelegate();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\internal\bind\SerializationDelegatingTypeAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */