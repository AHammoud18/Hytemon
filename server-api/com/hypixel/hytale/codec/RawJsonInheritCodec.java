package com.hypixel.hytale.codec;

import com.hypixel.hytale.codec.util.RawJsonReader;
import java.io.IOException;
import javax.annotation.Nullable;

public interface RawJsonInheritCodec<T> extends RawJsonCodec<T> {
  @Nullable
  T decodeAndInheritJson(RawJsonReader paramRawJsonReader, T paramT, ExtraInfo paramExtraInfo) throws IOException;
  
  void decodeAndInheritJson(RawJsonReader paramRawJsonReader, T paramT1, T paramT2, ExtraInfo paramExtraInfo) throws IOException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\RawJsonInheritCodec.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */