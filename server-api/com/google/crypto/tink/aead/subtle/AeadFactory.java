package com.google.crypto.tink.aead.subtle;

import com.google.crypto.tink.Aead;
import com.google.errorprone.annotations.Immutable;
import java.security.GeneralSecurityException;

@Immutable
public interface AeadFactory {
  int getKeySizeInBytes();
  
  Aead createAead(byte[] paramArrayOfbyte) throws GeneralSecurityException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\subtle\AeadFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */