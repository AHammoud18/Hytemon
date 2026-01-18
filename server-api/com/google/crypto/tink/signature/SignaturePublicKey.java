package com.google.crypto.tink.signature;

import com.google.crypto.tink.Key;
import com.google.crypto.tink.Parameters;
import com.google.crypto.tink.util.Bytes;
import com.google.errorprone.annotations.Immutable;

@Immutable
public abstract class SignaturePublicKey extends Key {
  public abstract Bytes getOutputPrefix();
  
  public abstract SignatureParameters getParameters();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\SignaturePublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */