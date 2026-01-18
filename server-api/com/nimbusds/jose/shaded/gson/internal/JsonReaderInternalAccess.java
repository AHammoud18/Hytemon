package com.nimbusds.jose.shaded.gson.internal;

import com.nimbusds.jose.shaded.gson.stream.JsonReader;
import java.io.IOException;

public abstract class JsonReaderInternalAccess {
  public static volatile JsonReaderInternalAccess INSTANCE;
  
  public abstract void promoteNameToValue(JsonReader paramJsonReader) throws IOException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\shaded\gson\internal\JsonReaderInternalAccess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */