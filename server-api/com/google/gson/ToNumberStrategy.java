package com.google.gson;

import com.google.gson.stream.JsonReader;
import java.io.IOException;

public interface ToNumberStrategy {
  Number readNumber(JsonReader paramJsonReader) throws IOException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\ToNumberStrategy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */