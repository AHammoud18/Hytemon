package com.hypixel.hytale.codec;

public interface WrappedCodec<T> {
  Codec<T> getChildCodec();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\WrappedCodec.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */