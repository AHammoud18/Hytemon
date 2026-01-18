package com.google.crypto.tink.mac;

import com.google.crypto.tink.Key;
import com.google.crypto.tink.Parameters;
import com.google.crypto.tink.util.Bytes;

public abstract class MacKey extends Key {
  public abstract Bytes getOutputPrefix();
  
  public abstract MacParameters getParameters();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\mac\MacKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */