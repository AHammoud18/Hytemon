package com.google.crypto.tink.prf;

import com.google.crypto.tink.Key;
import com.google.crypto.tink.Parameters;

public abstract class PrfKey extends Key {
  public abstract PrfParameters getParameters();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\prf\PrfKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */