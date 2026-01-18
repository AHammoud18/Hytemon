package com.google.crypto.tink.subtle;

import java.security.GeneralSecurityException;

public interface IndCpaCipher {
  byte[] encrypt(byte[] paramArrayOfbyte) throws GeneralSecurityException;
  
  byte[] decrypt(byte[] paramArrayOfbyte) throws GeneralSecurityException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\IndCpaCipher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */