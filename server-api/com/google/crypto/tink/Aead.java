package com.google.crypto.tink;

import java.security.GeneralSecurityException;

public interface Aead {
  byte[] encrypt(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws GeneralSecurityException;
  
  byte[] decrypt(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws GeneralSecurityException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\Aead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */