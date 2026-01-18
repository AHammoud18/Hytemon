package com.google.crypto.tink.keyderivation;

import com.google.crypto.tink.KeysetHandle;
import com.google.errorprone.annotations.Immutable;
import java.security.GeneralSecurityException;

@Immutable
public interface KeysetDeriver {
  KeysetHandle deriveKeyset(byte[] paramArrayOfbyte) throws GeneralSecurityException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\keyderivation\KeysetDeriver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */