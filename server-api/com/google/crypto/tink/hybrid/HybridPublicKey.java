package com.google.crypto.tink.hybrid;

import com.google.crypto.tink.Key;
import com.google.crypto.tink.Parameters;
import com.google.crypto.tink.util.Bytes;
import com.google.errorprone.annotations.Immutable;

@Immutable
public abstract class HybridPublicKey extends Key {
  public abstract Bytes getOutputPrefix();
  
  public abstract HybridParameters getParameters();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\HybridPublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */