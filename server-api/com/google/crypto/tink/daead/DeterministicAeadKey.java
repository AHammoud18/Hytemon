package com.google.crypto.tink.daead;

import com.google.crypto.tink.Key;
import com.google.crypto.tink.Parameters;
import com.google.crypto.tink.util.Bytes;

public abstract class DeterministicAeadKey extends Key {
  public abstract Bytes getOutputPrefix();
  
  public abstract DeterministicAeadParameters getParameters();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\daead\DeterministicAeadKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */