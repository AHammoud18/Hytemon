package com.google.crypto.tink.keyderivation.internal;

import com.google.crypto.tink.Key;
import com.google.errorprone.annotations.Immutable;
import java.security.GeneralSecurityException;

@Immutable
public interface KeyDeriver {
  Key deriveKey(byte[] paramArrayOfbyte) throws GeneralSecurityException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\keyderivation\internal\KeyDeriver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */