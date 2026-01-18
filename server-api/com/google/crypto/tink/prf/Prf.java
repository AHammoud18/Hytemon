package com.google.crypto.tink.prf;

import com.google.errorprone.annotations.Immutable;
import java.security.GeneralSecurityException;

@Immutable
public interface Prf {
  byte[] compute(byte[] paramArrayOfbyte, int paramInt) throws GeneralSecurityException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\prf\Prf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */